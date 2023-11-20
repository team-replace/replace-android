package com.app.replace.ui.coupleconnection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.replace.R
import com.app.replace.databinding.ActivityCoupleConnectionBinding
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.setOnSingleClickListener
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoupleConnectionActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoupleConnectionBinding.inflate(layoutInflater)
    }

    private val viewModel: CoupleConnectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initBinding()
        setConnectionCode()
        setObserver()
        setListener()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tbCoupleConnection)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.btn_close)
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setConnectionCode() {
        viewModel.getConnectionCode()
    }

    private fun setObserver() {
        viewModel.event.observe(this) {
            handleEvent(it)
        }
    }

    private fun handleEvent(event: CoupleConnectionViewModel.CoupleConnectionEvent) {
        when (event) {
            is CoupleConnectionViewModel.CoupleConnectionEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
            }

            is CoupleConnectionViewModel.CoupleConnectionEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is CoupleConnectionViewModel.CoupleConnectionEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }
        }
    }

    private fun setListener() {
        binding.tvInputConnectionCode.setOnSingleClickListener {
            navigateToInputConnectionCode()
        }
    }

    private fun navigateToInputConnectionCode() {
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CoupleConnectionActivity::class.java)
        }
    }
}
