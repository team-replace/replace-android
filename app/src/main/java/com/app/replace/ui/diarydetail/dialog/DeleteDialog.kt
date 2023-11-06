package com.app.replace.ui.diarydetail.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.replace.databinding.DialogConfirmDeleteDiaryBinding

class DeleteDialog(
    private val onPositiveButtonClick: () -> Unit,
) : DialogFragment() {
    private lateinit var binding: DialogConfirmDeleteDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogConfirmDeleteDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnDialogNo.setOnClickListener { dismiss() }
        binding.btnDialogYes.setOnClickListener { onPositiveButtonClick() }
    }
}
