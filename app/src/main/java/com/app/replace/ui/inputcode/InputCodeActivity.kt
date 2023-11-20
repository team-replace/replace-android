package com.app.replace.ui.inputcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.replace.R
import com.app.replace.databinding.ActivityInputCodeBinding
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.setOnSingleClickListener
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputCodeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityInputCodeBinding.inflate(layoutInflater)
    }

    private val viewModel: InputCodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initBinding()
        setObserver()
        setListener()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbInputCode)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_close)
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setObserver() {
        viewModel.event.observe(this) { event ->
            handleEvent(event)
        }
    }

    private fun setListener() {
        binding.tvInputOk.setOnSingleClickListener {
            viewModel.postConnectionCode()
        }
    }

    private fun handleEvent(event: InputCodeViewModel.InputCodeEvent) {
        when (event) {
            is InputCodeViewModel.InputCodeEvent.Success -> {
                finish()
            }

            is InputCodeViewModel.InputCodeEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
                binding.etInputCode.setText("")
            }

            is InputCodeViewModel.InputCodeEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is InputCodeViewModel.InputCodeEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }

            is InputCodeViewModel.InputCodeEvent.IsCodePostAble -> {
                binding.tvInputOk.isEnabled = true
            }
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, InputCodeActivity::class.java)
        }
    }
}
