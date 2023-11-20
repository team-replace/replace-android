package com.app.replace.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.replace.R
import com.app.replace.databinding.FragmentMypageBinding
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import com.app.replace.ui.coupleconnection.CoupleConnectionActivity
import com.app.replace.ui.main.mypage.dialog.CoupleDisconnectDialog
import com.app.replace.ui.main.mypage.listener.MypageListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : Fragment(), MypageListener {

    private val binding: FragmentMypageBinding by lazy {
        FragmentMypageBinding.inflate(layoutInflater)
    }

    private val viewModel: MypageViewModel by viewModels()

    private val disconnectDialog: CoupleDisconnectDialog by lazy {
        CoupleDisconnectDialog(
            onDisconnectClick = {},
            onCancelClick = { disconnectDialog.dismiss() },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setProfile()
        setObserver()
    }

    private fun setProfile() {
        viewModel.getProfile()
    }

    private fun setObserver() {
        viewModel.event.observe(viewLifecycleOwner) {
            handleEvent(it)
        }

        viewModel.soloProfile.observe(viewLifecycleOwner) {
            binding.isSolo = true
        }

        viewModel.coupleProfile.observe(viewLifecycleOwner) {
            binding.isSolo = false
        }
    }

    private fun handleEvent(event: MypageViewModel.MypageEvent) {
        when (event) {
            is MypageViewModel.MypageEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is MypageViewModel.MypageEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
            }

            is MypageViewModel.MypageEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }
        }
    }

    override fun profileUpdateClick() {
        showPreparingMessage()
    }

    override fun coupleConnectClick() {
        navigateToCoupleConnect()
    }

    override fun coupleDisConnectClick() {
        disconnectDialog.show(requireActivity().supportFragmentManager, DISCONNECT_DIALOG_TAG)
    }

    override fun pushAlarmClick() {
        showPreparingMessage()
    }

    override fun logoutClick() {
        showPreparingMessage()
    }

    override fun withdrawClick() {
        showPreparingMessage()
    }

    private fun showPreparingMessage() {
        binding.root.makeSnackbar(getString(R.string.mypage_preparing_feature))
    }

    private fun navigateToCoupleConnect() {
        startActivity(CoupleConnectionActivity.newIntent(requireContext()))
    }

    companion object {
        private const val DISCONNECT_DIALOG_TAG = "DisconnectDialog"
    }
}
