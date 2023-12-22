package com.app.replace.ui.main.home

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.replace.R
import com.app.replace.databinding.FragmentHomeBinding
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import com.app.replace.ui.diarydetail.DiaryDetailActivity
import com.app.replace.ui.diaryeditor.DiaryEditorActivity
import com.app.replace.ui.main.BottomNavigationListener
import com.app.replace.ui.main.MainActivity.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.app.replace.ui.main.home.adapter.PlaceDiaryAdapter
import com.app.replace.ui.model.CoordinateUiModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMapReadyCallback {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var locationSource: FusedLocationSource

    private lateinit var currentLatLng: LatLng

    private var naverMap: NaverMap? = null

    private var bottomNavigationListener: BottomNavigationListener? = null

    private var isUpdate: Boolean = false
    private var isMarkerClick: Boolean = false

    private val currentMarker by lazy {
        createMarker()
    }

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.viewMainBottomSheet.clBottomSheet)
    }

    private val bottomSheetBinding by lazy {
        binding.viewMainBottomSheet
    }

    private val ourDiaryAdapter by lazy {
        PlaceDiaryAdapter { diaryId ->
            navigateToDetail(diaryId)
        }
    }

    private val allDiaryAdapter by lazy {
        PlaceDiaryAdapter { diaryId ->
            navigateToDetail(diaryId)
        }
    }

    private val requestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            isUpdate = true
            viewModel.getPlaceInfo(
                currentLatLng.longitude.toString(),
                currentLatLng.latitude.toString(),
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationListener) {
            bottomNavigationListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.writeDiary = { navigateToEditor() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMap()
        initBottomSheet()
        setLocationSource()
        setObserver()
        getDiaryCoordinates()
        setBottomSheet()
        setListener()
        setAdapter()
        setDiaryTitle()
    }

    private fun setMap() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fcv_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fcv_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun initBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setLocationSource() {
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun setObserver() {
        viewModel.placeInfo.observe(viewLifecycleOwner) {
            binding.placeInfo = it
            ourDiaryAdapter.submitList(it.coupleDiaries)
            allDiaryAdapter.submitList(it.allDiaries)
            setBottomSheetBehaviorState()
            if (!isMarkerClick) {
                setCurrentMarker()
            }
        }

        viewModel.event.observe(viewLifecycleOwner) {
            handleEvent(it)
        }
    }

    private fun setBottomSheetBehaviorState() {
        if (isUpdate) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setCurrentMarker() {
        currentMarker.position = currentLatLng
        currentMarker.map = naverMap
    }

    private fun handleEvent(event: HomeViewModel.HomeEvent) {
        when (event) {
            is HomeViewModel.HomeEvent.UnKnownPlace -> {
                currentMarker.map = null
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                bottomNavigationListener?.showBottomNavigation()
            }

            is HomeViewModel.HomeEvent.ShowApiError -> {
                binding.root.makeSnackbar(event.throwable.message)
            }

            is HomeViewModel.HomeEvent.ShowNetworkError -> {
                binding.root.showNetworkErrorMessage(event.fetchState)
            }

            is HomeViewModel.HomeEvent.ShowUnexpectedError -> {
                binding.root.showUnexpectedErrorMessage()
            }
        }
    }

    private fun getDiaryCoordinates() {
        viewModel.getDiaryCoordinates()
    }

    private fun setBottomSheet() {
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomNavigationListener?.hideBottomNavigation()
                        bottomSheetBinding.clBottomSheet.setBackgroundResource(R.drawable.bg_ffffff_radius_15dp)
                        bottomSheetBinding.tbMain.visibility = View.GONE
                        bottomSheetBinding.tvSpotName.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        bottomNavigationListener?.showBottomNavigation()
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomSheetBinding.clBottomSheet.setBackgroundResource(R.color.white_FFFFFF)
                        bottomSheetBinding.tbMain.visibility = View.VISIBLE
                        bottomSheetBinding.tvSpotName.visibility = View.INVISIBLE
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        bottomSheetBinding.clBottomSheet.setBackgroundResource(R.drawable.bg_ffffff_radius_15dp)
                        bottomSheetBinding.tbMain.visibility = View.GONE
                        bottomSheetBinding.tvSpotName.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun setListener() {
        binding.viewMainBottomSheet.ivDown.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setAdapter() {
        bottomSheetBinding.viewCoupleDiaries.rvDiary.adapter = ourDiaryAdapter
        bottomSheetBinding.viewAllDiaries.rvDiary.adapter = allDiaryAdapter
    }

    private fun setDiaryTitle() {
        bottomSheetBinding.viewCoupleDiaries.tvDiaryTitle.setText(DiaryTitle.Our.title)
        bottomSheetBinding.viewAllDiaries.tvDiaryTitle.setText(DiaryTitle.All.title)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.setMapSetting()
        naverMap.onMapClick()

        viewModel.diaryCoordinates.value?.let { coordinates ->
            setDiariesMarker(coordinates)
        }
    }

    private fun NaverMap.setMapSetting() {
        val uiSettings = this.uiSettings
        uiSettings.isLocationButtonEnabled = true
    }

    private fun NaverMap.onMapClick() {
        this.setOnMapClickListener { _, latLng ->
            currentLatLng = latLng
            isMarkerClick = false
            viewModel.getPlaceInfo(latLng.longitude.toString(), latLng.latitude.toString())
            isUpdate = false
            setCameraPosition(latLng)
        }
    }

    private fun NaverMap.setCameraPosition(latLng: LatLng) {
        val cameraUpdate = CameraUpdate.scrollTo(latLng)
        this.moveCamera(cameraUpdate)
    }

    private fun setDiariesMarker(diaryCoordinates: List<CoordinateUiModel>) {
        val markers = arrayOfNulls<Marker>(diaryCoordinates.size)
        diaryCoordinates.forEachIndexed { index, coordinate ->
            markers[index] = Marker()
            configureMarker(markers[index], coordinate)
        }
    }

    private fun configureMarker(marker: Marker?, coordinate: CoordinateUiModel) {
        marker?.icon = OverlayImage.fromResource(R.drawable.ic_diary_marker)
        marker?.position = LatLng(coordinate.latitude.toDouble(), coordinate.longitude.toDouble())
        marker?.map = naverMap
        marker?.isIconPerspectiveEnabled = true
        marker?.onClickListener = Overlay.OnClickListener {
            isMarkerClick = true
            currentMarker.map = null
            viewModel.getPlaceInfo(coordinate.longitude, coordinate.latitude)
            true
        }
    }

    private fun createMarker(): Marker {
        val marker = Marker()
        marker.map = null
        marker.icon = OverlayImage.fromResource(R.drawable.ic_place_marker)
        marker.isIconPerspectiveEnabled = true
        return marker
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationListener = null
    }

    private fun navigateToDetail(diaryId: Long) {
        startActivity(DiaryDetailActivity.newIntent(requireContext(), diaryId))
    }

    private fun navigateToEditor() {
        requestLauncher.launch(
            DiaryEditorActivity.newIntent(
                requireActivity(),
                CoordinateUiModel(
                    currentLatLng.latitude.toString(),
                    currentLatLng.longitude.toString(),
                ),
                DiaryEditorActivity.SAVE_CODE,
            ),
        )
    }
}
