package com.app.replace.ui.diarydetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.app.replace.R
import com.app.replace.databinding.ActivityDiaryDetailBinding
import com.app.replace.ui.common.dialog.LoadingDialog
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import com.app.replace.ui.diarydetail.adapter.ImageSliderAdapter
import com.app.replace.ui.diarydetail.dialog.DeleteDialog
import com.app.replace.ui.diaryeditor.DiaryEditorActivity
import com.app.replace.ui.model.DiaryUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiaryDetailActivity : AppCompatActivity() {

    private val binding: ActivityDiaryDetailBinding by lazy {
        ActivityDiaryDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DiaryDetailViewModel by viewModels()

    private val diaryId: Long by lazy {
        intent.getLongExtra(KEY_DIARY_ID, 0L)
    }

    private val deleteDialog: DeleteDialog by lazy {
        DeleteDialog {
            viewModel.deleteDiary(diaryId)
        }
    }

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(getString(R.string.loading_message))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBinding()
        initToolbar()
        initLoading()
        getDiaryDetail()
        setObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.diary_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_update -> {
                val diary = viewModel.diary.value ?: return false
                navigateToEditor(diary)
                true
            }

            R.id.action_delete -> {
                deleteDialog.show(supportFragmentManager, DELETE_DIALOG_TAG)
                true
            }

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
        setSupportActionBar(binding.tbDiaryDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_close)
    }

    private fun initLoading() {
        loadingDialog.show(supportFragmentManager, LOADING_DIALOG_TAG)
    }

    private fun getDiaryDetail() {
        viewModel.getDiaryDetail(diaryId)
    }

    private fun setObserver() {
        viewModel.diary.observe(this) { diary ->
            loadingDialog.dismiss()
            if (diary.images.isNotEmpty()) {
                setImageSlider(diary.images)
                setImageIndicators()
            } else {
                binding.vpImageSlider.isVisible = false
                binding.llIndicators.isVisible = false
            }
        }
        viewModel.event.observe(this) { event ->
            handleEvent(event)
        }
    }

    private fun setImageSlider(images: List<String>) {
        binding.vpImageSlider.offscreenPageLimit = 1
        binding.vpImageSlider.adapter = ImageSliderAdapter(images)
        binding.vpImageSlider.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    updateCurrentIndicator(position)
                }
            },
        )
    }

    private fun setImageIndicators() {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        ).apply { setMargins(8, 0, 8, 0) }

        addIndicatorViews(params)
        updateCurrentIndicator(0)
    }

    private fun addIndicatorViews(params: LinearLayout.LayoutParams) {
        List(binding.vpImageSlider.adapter?.itemCount ?: 0) {
            ImageView(this).apply {
                setImageResource(R.drawable.ic_bcd4c7_indicator_focus_off)
                layoutParams = params
            }.also { indicatorView ->
                binding.llIndicators.addView(indicatorView)
            }
        }
    }

    private fun updateCurrentIndicator(position: Int) {
        for (i in 0 until binding.llIndicators.childCount) {
            val indicatorView = binding.llIndicators.getChildAt(i) as ImageView
            if (i == position) {
                indicatorView.setImageResource(R.drawable.ic_9eceb4_indicator_focus_on)
            } else {
                indicatorView.setImageResource(R.drawable.ic_bcd4c7_indicator_focus_off)
            }
        }
    }

    private fun navigateToEditor(diary: DiaryUiModel) {
        startActivity(DiaryEditorActivity.newIntent(this, diary, DiaryEditorActivity.UPDATE_CODE))
    }

    private fun handleEvent(event: DiaryDetailViewModel.DiaryDetailEvent) {
        when (event) {
            is DiaryDetailViewModel.DiaryDetailEvent.DeleteDiarySuccess -> {
                deleteDialog.dismiss()
                finish()
            }

            is DiaryDetailViewModel.DiaryDetailEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
            }

            is DiaryDetailViewModel.DiaryDetailEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is DiaryDetailViewModel.DiaryDetailEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }
        }
    }

    companion object {

        private const val KEY_DIARY_ID = "key_diary_id"
        private const val DELETE_DIALOG_TAG = "DeleteDialog"
        private const val LOADING_DIALOG_TAG = "LoadingDialog"

        fun newIntent(context: Context, diaryId: Long): Intent {
            return Intent(context, DiaryDetailActivity::class.java).apply {
                putExtra(KEY_DIARY_ID, diaryId)
            }
        }
    }
}
