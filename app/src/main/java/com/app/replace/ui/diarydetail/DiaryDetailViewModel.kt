package com.app.replace.ui.diarydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.replace.ui.common.SingleLiveEvent
import com.app.replace.ui.common.mapper.toUi
import com.app.replace.ui.model.DiaryDetailUiModel
import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import com.replace.data.remote.CustomResult
import com.replace.data.repository.diary.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _diary = MutableLiveData<DiaryDetailUiModel>()
    val diary: LiveData<DiaryDetailUiModel> get() = _diary

    private val _event: SingleLiveEvent<DiaryDetailEvent> = SingleLiveEvent()
    val event: LiveData<DiaryDetailEvent>
        get() = _event

    fun getDiaryDetail(diaryId: Long) {
        viewModelScope.launch {
            when (val response = diaryRepository.getDiaryDetail(diaryId)) {
                is CustomResult.Success -> {
                    _diary.value = response.data.toUi()
                }

                is CustomResult.ApiError -> {
                    _event.value = DiaryDetailEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = DiaryDetailEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = DiaryDetailEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = DiaryDetailEvent.ShowUnexpectedError
                }
            }
        }
    }

    fun deleteDiary(diaryId: Long) {
        viewModelScope.launch {
            when (val response = diaryRepository.deleteDiary(diaryId)) {
                is CustomResult.Success -> {
                    _event.value = DiaryDetailEvent.DeleteDiarySuccess
                }

                is CustomResult.ApiError -> {
                    _event.value = DiaryDetailEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = DiaryDetailEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = DiaryDetailEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = DiaryDetailEvent.DeleteDiarySuccess
                }
            }
        }
    }

    sealed class DiaryDetailEvent {
        class ShowApiError(val throwable: CustomThrowable) : DiaryDetailEvent()
        class ShowNetworkError(val fetchState: FetchState) : DiaryDetailEvent()
        object ShowUnexpectedError : DiaryDetailEvent()

        object DeleteDiarySuccess : DiaryDetailEvent()
    }
}
