package com.app.replace.ui.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import com.app.replace.R
import com.app.replace.databinding.ActivityMainBinding
import com.app.replace.ui.main.alarm.AlarmFragment
import com.app.replace.ui.main.bookmark.BookmarkFragment
import com.app.replace.ui.main.diary.DiaryFragment
import com.app.replace.ui.main.home.HomeFragment
import com.app.replace.ui.main.mypage.MypageFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationListener {

    private val fragments = mapOf(
        FRAGMENT_HOME to HomeFragment(),
        FRAGMENT_DIARY to DiaryFragment(),
        FRAGMENT_BOOKMARK to BookmarkFragment(),
        FRAGMENT_ALARM to AlarmFragment(),
        FRAGMENT_MYPAGE to MypageFragment(),
    )

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        permissions.entries.forEach { entry ->
            val isGranted = entry.value
            if (!isGranted) {
                ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    LOCATION_PERMISSION_REQUEST_CODE,
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        permissionRequestLauncher.launch(PERMISSIONS)
        initFragmentContainerView()
        setBottomNavigation()
    }

    private fun initFragmentContainerView() {
        supportFragmentManager.commit {
            add(R.id.fcv_main, HomeFragment(), FRAGMENT_HOME)
            setReorderingAllowed(true)
        }
    }

    private fun setBottomNavigation() {
        binding.bnvMain.selectedItemId = R.id.bottom_menu_home
        binding.bnvMain.setOnItemSelectedListener {
            changeFragment(getTag(it.itemId))
            true
        }
    }

    private fun changeFragment(fragmentTag: String) {
        hideFragment()

        val findFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        supportFragmentManager.commit {
            if (findFragment != null) {
                show(findFragment)
            } else {
                val fragment = fragments[fragmentTag] ?: throw IllegalArgumentException()
                add(R.id.fcv_main, fragment, fragmentTag)
            }
        }
    }

    private fun hideFragment() {
        val currentFragmentTag = getTag(binding.bnvMain.selectedItemId)
        supportFragmentManager.commit {
            supportFragmentManager.findFragmentByTag(currentFragmentTag)?.let { hide(it) }
        }
    }

    private fun getTag(menuId: Int): String {
        return when (menuId) {
            R.id.bottom_menu_home -> FRAGMENT_HOME
            R.id.bottom_menu_diary -> FRAGMENT_DIARY
            R.id.bottom_menu_bookmark -> FRAGMENT_BOOKMARK
            R.id.bottom_menu_alarm -> FRAGMENT_ALARM
            R.id.bottom_menu_mypage -> FRAGMENT_MYPAGE
            else -> throw IllegalArgumentException()
        }
    }

    override fun hideBottomNavigation() {
        binding.bnvMain.visibility = View.GONE
    }

    override fun showBottomNavigation() {
        binding.bnvMain.visibility = View.VISIBLE
    }

    companion object {
        private const val FRAGMENT_HOME = "home"
        private const val FRAGMENT_DIARY = "diary"
        private const val FRAGMENT_BOOKMARK = "bookmark"
        private const val FRAGMENT_ALARM = "alarm"

        private const val FRAGMENT_MYPAGE = "mypage"

        const val LOCATION_PERMISSION_REQUEST_CODE = 5000
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }
}
