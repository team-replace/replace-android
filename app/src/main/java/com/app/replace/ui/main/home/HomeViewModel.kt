package com.app.replace.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.replace.ui.common.SingleLiveEvent
import com.app.replace.ui.common.mapper.toUi
import com.app.replace.ui.model.PlaceInfoUiModel
import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import com.replace.data.remote.CustomResult
import com.replace.data.repository.map.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mapRepository: MapRepository,
) : ViewModel() {

    private val _placeInfo = MutableLiveData<PlaceInfoUiModel>()
    val placeInfo: LiveData<PlaceInfoUiModel> get() = _placeInfo

    private val _event: SingleLiveEvent<HomeEvent> = SingleLiveEvent()
    val event: LiveData<HomeEvent>
        get() = _event

    fun getPlaceInfo(longitude: String, latitude: String) {
        viewModelScope.launch {
            when (val response = mapRepository.getPlaceInfo(longitude, latitude)) {
                is CustomResult.Success -> {
                    _placeInfo.value = response.data.toUi()
                }

                is CustomResult.ApiError -> {
                    if (!(response.customThrowable.code == 8000 || response.customThrowable.code == 8001)) {
                        _event.value = HomeEvent.ShowApiError(response.customThrowable)
                    } else {
                        _event.value = HomeEvent.UnKnownPlace
                    }
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = HomeEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = HomeEvent.ShowNetworkError(response.fetchState)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = HomeEvent.ShowUnexpectedError
                }
            }
        }
    }

    sealed class HomeEvent {
        object UnKnownPlace : HomeEvent()
        class ShowApiError(val throwable: CustomThrowable) : HomeEvent()
        class ShowNetworkError(val fetchState: FetchState) : HomeEvent()
        object ShowUnexpectedError : HomeEvent()
    }
}
