package com.app.replace.ui.diaryeditor

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.replace.R
import com.app.replace.databinding.ActivityDiaryEditorBinding
import com.app.replace.ui.diaryeditor.adapter.DiaryEditorImageAdapter
import gun0912.tedimagepicker.builder.TedImagePicker

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBinding()
        initToolbar()
        setAdapter()
        setObserver()
        selectImages()
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

    private fun setObserver() {
        viewModel.galleryImages.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun selectImages() {
        includeBinding.clSelectImage.setOnClickListener {
            viewModel.galleryImages.value?.takeIf { it.size < MAX_IMAGE_NUMBER }?.let { galleryImages ->
                TedImagePicker.with(this)
                    .max(MAX_IMAGE_NUMBER - galleryImages.size, "사진은 최대 $MAX_IMAGE_NUMBER 장")
                    .startMultiImage(::showImages)
            }
        }
    }

    private fun showImages(uriList: List<Uri>) {
        uriList.forEach {
            viewModel.addSelectedImages(it.toString())
        }
    }

    companion object {
        private const val MAX_IMAGE_NUMBER = 10
    }
}
