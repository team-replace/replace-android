package com.app.replace.ui.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.replace.ui.common.SingleLiveEvent
import com.app.replace.ui.common.mapper.toUi
import com.app.replace.ui.model.CoupleProfileUiModel
import com.app.replace.ui.model.WriterUiModel
import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import com.replace.data.model.response.ProfileResponse
import com.replace.data.remote.CustomResult
import com.replace.data.repository.profile.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _soloProfile = MutableLiveData<WriterUiModel>()
    val soloProfile: LiveData<WriterUiModel> get() = _soloProfile

    private val _coupleProfile = MutableLiveData<CoupleProfileUiModel>()
    val coupleProfile: LiveData<CoupleProfileUiModel> get() = _coupleProfile

    private val _event: MutableLiveData<MypageEvent> = SingleLiveEvent()
    val event: LiveData<MypageEvent> get() = _event

    fun getProfile() {
        viewModelScope.launch {
            when (val response = profileRepository.getProfile()) {
                is CustomResult.Success -> {
                    setProfile(response.data)
                }

                is CustomResult.ApiError -> {
                    _event.value = MypageEvent.ShowApiError(response.customThrowable)
                }

                is CustomResult.NullCustomResult -> {
                    _event.value = MypageEvent.ShowUnexpectedError
                }

                is CustomResult.UnKnownApiError -> {
                    _event.value = MypageEvent.ShowUnexpectedError
                }

                is CustomResult.NetworkError -> {
                    _event.value = MypageEvent.ShowNetworkError(response.fetchState)
                }
            }
        }
    }

    private fun setProfile(profileResponse: ProfileResponse) {
        if (profileResponse.partner == null) {
            _soloProfile.value = profileResponse.user.toUi()
        } else {
            _coupleProfile.value = profileResponse.toUi()
        }
    }

    sealed class MypageEvent {
        class ShowApiError(val throwable: CustomThrowable) : MypageEvent()
        class ShowNetworkError(val fetchState: FetchState) : MypageEvent()
        object ShowUnexpectedError : MypageEvent()
    }
}
