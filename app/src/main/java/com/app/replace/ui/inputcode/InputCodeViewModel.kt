package com.app.replace.ui.inputcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.replace.ui.common.SingleLiveEvent
import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import com.replace.data.remote.CustomResult
import com.replace.data.repository.connection.ConnectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputCodeViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository,
) : ViewModel() {

    private val _code = MutableLiveData("")

    private val _event: MutableLiveData<InputCodeEvent> = SingleLiveEvent()
    val event: LiveData<InputCodeEvent> get() = _event
    fun postConnectionCode() {
        viewModelScope.launch {
            when (val response = connectionRepository.postConnectionCode(_code.value ?: "")) {
                is CustomResult.Success -> {
                    _event.value = InputCodeEvent.Success
                }

                is CustomResult.ApiError -> {
                    _event.value = InputCodeEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = InputCodeEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = InputCodeEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = InputCodeEvent.ShowUnexpectedError
                }
            }
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        _code.value = s.toString()
        checkConnectionCode()
    }

    private fun checkConnectionCode() {
        if (_code.value?.count() == CODE_LENGTH) {
            _event.value = InputCodeEvent.IsCodePostAble
        }
    }

    sealed class InputCodeEvent {
        object Success : InputCodeEvent()
        class ShowApiError(val throwable: CustomThrowable) : InputCodeEvent()
        class ShowNetworkError(val fetchState: FetchState) : InputCodeEvent()
        object ShowUnexpectedError : InputCodeEvent()

        object IsCodePostAble : InputCodeEvent()
    }

    companion object {
        private const val CODE_LENGTH = 10
    }
}
