package com.app.replace.ui.diaryeditor

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.replace.ui.common.SingleLiveEvent
import com.app.replace.ui.common.processAndAdjustImage
import com.app.replace.ui.model.DiaryUiModel
import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import com.replace.data.remote.CustomResult
import com.replace.data.repository.diary.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DiaryEditorViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _images = mutableListOf<String>()
    val images: List<String> get() = _images.toList()
    private val _galleryImages = MutableLiveData<List<String>>()
    val galleryImages: LiveData<List<String>> get() = _galleryImages

    private val _imageUrls = MutableLiveData<List<String>>()

    private val _diary = MutableLiveData<DiaryUiModel>()
    val diary: LiveData<DiaryUiModel> get() = _diary

    private val _event: SingleLiveEvent<DiaryEditorEvent> = SingleLiveEvent()
    val event: LiveData<DiaryEditorEvent>
        get() = _event

    fun initViewModelOnUpdate(diary: DiaryUiModel) {
        _diary.value = diary
        _images.addAll(diary.images)
        _galleryImages.value = images.toList()
    }

    fun addSelectedImages(image: String) {
        _images.add(image)
        _galleryImages.value = images.toList()
    }

    fun deleteImages(image: String) {
        _images.remove(image)
        _galleryImages.value = images.toList()
    }

    fun saveDiary(title: String, content: String, shareScope: String) {
        viewModelScope.launch {
            when (
                val response = diaryRepository.saveDiary(
                    _imageUrls.value ?: emptyList(),
                    title,
                    content,
                    shareScope,
                )
            ) {
                is CustomResult.Success -> {
                    _event.value = DiaryEditorEvent.SaveDiaryResult(
                        response.headers["Location"]?.substringAfterLast("/")?.toLong() ?: 0,
                    )
                }

                is CustomResult.ApiError -> {
                    _event.value = DiaryEditorEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = DiaryEditorEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = DiaryEditorEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = DiaryEditorEvent.ShowUnexpectedError
                }
            }
        }
    }

    fun saveImages(context: Context) {
        val uris = _images.map { Uri.parse(it) }
        viewModelScope.launch {
            when (
                val response =
                    diaryRepository.saveDiaryImages(getFileFromContent(context, uris))
            ) {
                is CustomResult.Success -> {
                    _imageUrls.value = response.data.imageUrls
                }

                is CustomResult.ApiError -> {
                    _event.value = DiaryEditorEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = DiaryEditorEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = DiaryEditorEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = DiaryEditorEvent.ShowUnexpectedError
                }
            }
        }
    }

    fun updateDiary(
        diaryId: Long,
        title: String,
        content: String,
        shareScope: String,
    ) {
        viewModelScope.launch {
            when (
                val response =
                    diaryRepository.updateDiary(
                        diaryId,
                        _imageUrls.value ?: emptyList(),
                        title,
                        content,
                        shareScope,
                    )
            ) {
                is CustomResult.Success -> {
                    _event.value = DiaryEditorEvent.UpdateDiaryResult
                }

                is CustomResult.ApiError -> {
                    _event.value = DiaryEditorEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = DiaryEditorEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = DiaryEditorEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = DiaryEditorEvent.ShowUnexpectedError
                }
            }
        }
    }

    fun checkUploadAble(title: String) {
        if (title.isNotBlank()) {
            _event.value = DiaryEditorEvent.UploadAble
        }
    }

    private fun getFileFromContent(context: Context, uris: List<Uri>): List<File> {
        return uris.map { uri -> processAndAdjustImage(context, uri) }
    }

    fun checkImagesCount(): Boolean {
        return _images.size < 10
    }

    sealed class DiaryEditorEvent {
        class SaveDiaryResult(val diaryId: Long) : DiaryEditorEvent()
        object UpdateDiaryResult : DiaryEditorEvent()
        class ShowApiError(val throwable: CustomThrowable) : DiaryEditorEvent()
        class ShowNetworkError(val fetchState: FetchState) : DiaryEditorEvent()
        object ShowUnexpectedError : DiaryEditorEvent()

        object UploadAble : DiaryEditorEvent()
    }
}
