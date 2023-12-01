package com.app.replace.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.replace.R
import com.app.replace.databinding.FragmentHomeBinding
import com.app.replace.ui.common.makeSnackbar
import com.app.replace.ui.common.showNetworkErrorMessage
import com.app.replace.ui.common.showUnexpectedErrorMessage
import com.app.replace.ui.main.MainActivity.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
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

    private lateinit var naverMap: NaverMap

    private val currentMarker by lazy {
        createMarker()
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
        setMap()
        setLocationSource()
        setObserver()
    }

    private fun setMap() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fcv_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fcv_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun setLocationSource() {
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun setObserver() {
        viewModel.placeInfo.observe(viewLifecycleOwner) {
            currentMarker.position = currentLatLng
            currentMarker.map = naverMap
        }

        viewModel.event.observe(viewLifecycleOwner) {
            handleEvent(it)
        }
    }

    private fun handleEvent(event: HomeViewModel.HomeEvent) {
        when (event) {
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

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.setMapSetting()
        naverMap.onMapClick()
    }

    private fun NaverMap.setMapSetting() {
        val uiSettings = this.uiSettings
        uiSettings.isLocationButtonEnabled = true
    }

    private fun NaverMap.onMapClick() {
        this.setOnMapClickListener { _, latLng ->
            currentLatLng = latLng
            viewModel.getPlaceInfo(latLng.longitude.toString(), latLng.latitude.toString())
        }
    }

    private fun createMarker(): Marker {
        val marker = Marker()
        marker.map = null
        marker.icon = OverlayImage.fromResource(R.drawable.ic_place_marker)
        return marker
    }
}
