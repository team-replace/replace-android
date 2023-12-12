package com.app.replace.ui.diaryeditor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.replace.R
import com.app.replace.databinding.ActivityDiaryEditorBinding
import com.app.replace.ui.common.dialog.LoadingDialog
import com.app.replace.ui.common.getParcelableExtraCompat
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.setOnSingleClickListener
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import com.app.replace.ui.diarydetail.DiaryDetailActivity
import com.app.replace.ui.diaryeditor.adapter.DiaryEditorImageAdapter
import com.app.replace.ui.model.CoordinateUiModel
import com.app.replace.ui.model.DiaryDetailUiModel
import com.app.replace.ui.model.ShareScope
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class DiaryEditorActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDiaryEditorBinding.inflate(layoutInflater)
    }

    private val includeBinding by lazy {
        binding.viewSelectImage
    }

    private val viewModel: DiaryEditorViewModel by viewModels()

    private val adapter: DiaryEditorImageAdapter by lazy {
        DiaryEditorImageAdapter { imageUrl ->
            viewModel.deleteImages(imageUrl)
        }
    }

    private val originActivityKey: Int by lazy {
        intent.getIntExtra(KEY_DIARY_EDITOR_CHECK, 0)
    }

    private val diary: DiaryDetailUiModel? by lazy {
        intent.getParcelableExtraCompat(KEY_DIARY_EDITOR_DIARY) as? DiaryDetailUiModel
    }

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(getString(R.string.loading_save_message))
    }

    private val coordinate: CoordinateUiModel? by lazy {
        intent.getParcelableExtraCompat(KEY_DIARY_EDITOR_COORDINATE) as? CoordinateUiModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBinding()
        initToolbar()
        setAdapter()
        setDiaryIfUpdate()
        setObserver()
        selectImages()
        setClickListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbDiaryEditor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_close)
    }

    private fun setAdapter() {
        binding.rvDiaryImage.adapter = adapter
    }

    private fun setDiaryIfUpdate() {
        diary?.let {
            if (originActivityKey == UPDATE_CODE) {
                viewModel.initViewModelOnUpdate(it)
            }
        }
    }

    private fun setObserver() {
        viewModel.galleryImages.observe(this) {
            adapter.submitList(it)
        }

        viewModel.event.observe(this) {
            handleEvent(it)
        }
    }

    private fun handleEvent(event: DiaryEditorViewModel.DiaryEditorEvent) {
        when (event) {
            is DiaryEditorViewModel.DiaryEditorEvent.SaveDiaryResult -> {
                loadingDialog.dismiss()
                navigateToDetail(event.diaryId)
                finish()
            }

            is DiaryEditorViewModel.DiaryEditorEvent.UpdateDiaryResult -> {
                diary?.let {
                    navigateToDetail(it.id)
                    finish()
                }
            }

            is DiaryEditorViewModel.DiaryEditorEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
            }

            is DiaryEditorViewModel.DiaryEditorEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is DiaryEditorViewModel.DiaryEditorEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }

            is DiaryEditorViewModel.DiaryEditorEvent.UploadAble -> {
                saveDiary()
            }
        }
    }

    private fun selectImages() {
        includeBinding.clSelectImage.setOnSingleClickListener {
            if (viewModel.checkImagesCount()) {
                TedImagePicker.with(this)
                    .max(MAX_IMAGE_NUMBER - viewModel.images.size, "사진은 최대 $MAX_IMAGE_NUMBER 장")
                    .startMultiImage(::showImages)
            } else {
                binding.root.makeSnackbar("이미지는 최대 10장까지 가능합니다")
            }
        }
    }

    private fun setClickListener() {
        binding.tvSave.setOnSingleClickListener {
            viewModel.checkUploadAble(binding.etDiaryTitle.text.toString())
        }
    }

    private fun showImages(uriList: List<Uri>) {
        uriList.forEach {
            viewModel.addSelectedImages(it.toString())
        }
        viewModel.saveImages(this)
    }

    private fun navigateToDetail(diaryId: Long) {
        startActivity(DiaryDetailActivity.newIntent(this, diaryId))
    }

    private fun saveDiary() {
        val diaryTitle = binding.etDiaryTitle.text.toString()
        val diaryContent = binding.etDiaryContent.text.toString().ifBlank { "" }
        val diaryScope = getShareScope()
        loadingDialog.show(supportFragmentManager, LOADING_DIALOG_TAG)
        when (originActivityKey) {
            SAVE_CODE -> viewModel.saveDiary(diaryTitle, diaryContent, diaryScope, coordinate ?: CoordinateUiModel("", ""))
            UPDATE_CODE -> diary?.let { diary ->
                viewModel.updateDiary(diary.id, diaryTitle, diaryContent, diaryScope)
            }
        }
    }

    private fun getShareScope(): String {
        return when (binding.rgShareScope.checkedRadioButtonId) {
            R.id.rb_share_all -> ShareScope.ALL.name
            R.id.rb_share_us -> ShareScope.US.name
            else -> return ""
        }
    }

    companion object {
        private const val MAX_IMAGE_NUMBER = 10
        const val SAVE_CODE = 2000
        const val UPDATE_CODE = 3000
        private const val KEY_DIARY_EDITOR_CHECK = "key_diary_editor_check"
        private const val KEY_DIARY_EDITOR_DIARY = "key_diary_editor_diary"
        private const val KEY_DIARY_EDITOR_COORDINATE = "key_diary_editor_coordinate"
        private const val LOADING_DIALOG_TAG = "loadingDialog"

        fun newIntent(context: Context, coordinate: CoordinateUiModel, code: Int): Intent {
            return Intent(context, DiaryEditorActivity::class.java).apply {
                putExtra(KEY_DIARY_EDITOR_COORDINATE, coordinate)
                putExtra(KEY_DIARY_EDITOR_CHECK, code)
            }
        }

        fun newIntent(context: Context, diary: DiaryDetailUiModel, code: Int): Intent {
            return Intent(context, DiaryEditorActivity::class.java).apply {
                putExtra(KEY_DIARY_EDITOR_CHECK, code)
                putExtra(KEY_DIARY_EDITOR_DIARY, diary)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
}
