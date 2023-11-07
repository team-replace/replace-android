package com.app.replace.ui.coupleconnection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.replace.databinding.ActivityCoupleConnectionBinding

class CoupleConnectionActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoupleConnectionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
