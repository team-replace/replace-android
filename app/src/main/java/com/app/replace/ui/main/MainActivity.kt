package com.app.replace.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.app.replace.R
import com.app.replace.databinding.ActivityMainBinding
import com.app.replace.ui.main.alarm.AlarmFragment
import com.app.replace.ui.main.bookmark.BookmarkFragment
import com.app.replace.ui.main.diary.DiaryFragment
import com.app.replace.ui.main.home.HomeFragment
import com.app.replace.ui.main.mypage.MypageFragment

class MainActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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

    companion object {
        private const val FRAGMENT_HOME = "home"
        private const val FRAGMENT_DIARY = "diary"
        private const val FRAGMENT_BOOKMARK = "bookmark"
        private const val FRAGMENT_ALARM = "alarm"
        private const val FRAGMENT_MYPAGE = "mypage"
    }
}
