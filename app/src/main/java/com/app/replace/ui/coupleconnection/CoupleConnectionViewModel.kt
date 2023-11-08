package com.app.replace.ui.coupleconnection

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
class CoupleConnectionViewModel @Inject constructor(
    private val connectionRepository: ConnectionRepository,
) : ViewModel() {

    private val _code = MutableLiveData<String?>()
    val code: LiveData<String?> get() = _code

    private val _event: SingleLiveEvent<CoupleConnectionEvent> = SingleLiveEvent()
    val event: LiveData<CoupleConnectionEvent> get() = _event

    fun getConnectionCode() {
        viewModelScope.launch {
            when (val response = connectionRepository.getConnectionCode()) {
                is CustomResult.Success -> {
                    _code.value = response.data
                }

                is CustomResult.ApiError -> {
                    _event.value =
                        CoupleConnectionEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = CoupleConnectionEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value =
                        CoupleConnectionEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = CoupleConnectionEvent.ShowUnexpectedError
                }
            }
        }
    }

    sealed class CoupleConnectionEvent {
        class ShowApiError(val throwable: CustomThrowable) : CoupleConnectionEvent()
        class ShowNetworkError(val fetchState: FetchState) : CoupleConnectionEvent()
        object ShowUnexpectedError : CoupleConnectionEvent()
    }
}
