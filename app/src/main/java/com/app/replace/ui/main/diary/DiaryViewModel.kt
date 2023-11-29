package com.app.replace.ui.main.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.replace.ui.common.SingleLiveEvent
import com.app.replace.ui.common.mapper.toUi
import com.app.replace.ui.model.DiaryUiModel
import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import com.replace.data.remote.CustomResult
import com.replace.data.repository.diary.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : ViewModel() {

    private val _diaries = MutableLiveData<List<DiaryUiModel>>()
    val diaries: LiveData<List<DiaryUiModel>> get() = _diaries

    private val _event: MutableLiveData<DiaryEvent> = SingleLiveEvent()
    val event: LiveData<DiaryEvent> get() = _event
    fun getDiariesWithDate(year: Int, month: Int, dayOfMonth: Int) {
        viewModelScope.launch {
            when (val response = diaryRepository.getDiariesWithDate(year, month, dayOfMonth)) {
                is CustomResult.Success -> {
                    _diaries.value = response.data.diaries.map { it.toUi() }
                }

                is CustomResult.ApiError -> {
                    _event.value = DiaryEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.NetworkError -> {
                    _event.value = DiaryEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = DiaryEvent.ShowUnexpectedError
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = DiaryEvent.ShowUnexpectedError
                }
            }
        }
    }

    sealed class DiaryEvent {
        class ShowApiError(val throwable: CustomThrowable) : DiaryEvent()
        class ShowNetworkError(val fetchState: FetchState) : DiaryEvent()
        object ShowUnexpectedError : DiaryEvent()
    }
}
