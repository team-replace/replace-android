package com.app.replace.ui.main.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.replace.R
import com.app.replace.databinding.FragmentDiaryBinding
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import com.app.replace.ui.diarydetail.DiaryDetailActivity
import com.app.replace.ui.main.diary.adapter.DiaryAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.Calendar

@AndroidEntryPoint
class DiaryFragment : Fragment() {

    private val binding: FragmentDiaryBinding by lazy {
        FragmentDiaryBinding.inflate(layoutInflater)
    }

    private val diaryAdapter: DiaryAdapter by lazy {
        DiaryAdapter { id ->
            navigateToDetail(id)
        }
    }

    private val viewModel: DiaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setTodayDiaries()
        setListener()
        setObserver()
    }

    private fun setAdapter() {
        binding.rvDiaryWithProfile.adapter = diaryAdapter
    }

    private fun setTodayDiaries() {
        val today = LocalDate.now()
        viewModel.getDiariesWithDate(today.year, today.monthValue, today.dayOfMonth)
    }

    private fun setObserver() {
        viewModel.diaries.observe(viewLifecycleOwner) {
            diaryAdapter.submitList(it)
        }
        viewModel.event.observe(viewLifecycleOwner) {
            handleEvent(it)
        }
    }

    private fun setListener() {
        binding.cvCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            handleDateSelection(year, month, dayOfMonth)
        }
    }

    private fun handleEvent(event: DiaryViewModel.DiaryEvent) {
        when (event) {
            is DiaryViewModel.DiaryEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
            }

            is DiaryViewModel.DiaryEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is DiaryViewModel.DiaryEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }
        }
    }

    private fun handleDateSelection(year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = getSelectedDateInMillis(year, month, dayOfMonth)
        val currentDate = System.currentTimeMillis()

        if (selectedDate > currentDate) {
            showErrorAndSetCurrentDate()
            setTodayDiaries()
        } else {
            viewModel.getDiariesWithDate(year, month + 1, dayOfMonth)
        }
    }

    private fun showErrorAndSetCurrentDate() {
        binding.root.makeSnackbar(getString(R.string.date_error_message))
        binding.cvCalendar.date = System.currentTimeMillis()
    }

    private fun getSelectedDateInMillis(year: Int, month: Int, dayOfMonth: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.timeInMillis
    }

    private fun navigateToDetail(diaryId: Long) {
        startActivity(DiaryDetailActivity.newIntent(requireContext(), diaryId))
    }
}
