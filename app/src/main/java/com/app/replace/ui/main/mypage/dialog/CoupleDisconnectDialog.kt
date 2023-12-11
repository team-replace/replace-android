package com.app.replace.ui.main.mypage.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.replace.databinding.DialogCoupleDisconnectBinding

class CoupleDisconnectDialog(
    private val onDisconnectClick: () -> Unit,
    private val onCancelClick: () -> Unit,
) : DialogFragment() {

    private val binding: DialogCoupleDisconnectBinding by lazy {
        DialogCoupleDisconnectBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
