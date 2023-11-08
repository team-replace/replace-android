package com.app.replace.ui.inputcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.replace.databinding.ActivityInputCodeBinding

class InputCodeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityInputCodeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
