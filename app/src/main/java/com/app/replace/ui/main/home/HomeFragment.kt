package com.app.replace.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.replace.R
import com.app.replace.databinding.FragmentHomeBinding
import com.app.replace.ui.main.MainActivity.Companion.LOCATION_PERMISSION_REQUEST_CODE
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class HomeFragment : Fragment(), OnMapReadyCallback {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private lateinit var locationSource: FusedLocationSource

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

    override fun onMapReady(naverMap: NaverMap) {
        naverMap.locationSource = locationSource
        naverMap.setMapSetting()
        naverMap.onMapClick()
    }

    private fun NaverMap.setMapSetting() {
        val uiSettings = this.uiSettings
        uiSettings.isLocationButtonEnabled = true
    }

    private fun NaverMap.onMapClick() {
        val marker = createMarker()

        this.setOnMapClickListener { _, latLng ->
            // 이 위치에 주소와 건물명이 있는지 확인 후 있다면 마커 놓을 수 있게
            marker.position = latLng
            marker.map = this
        }
    }

    private fun createMarker(): Marker {
        val marker = Marker()
        marker.map = null
        marker.icon = OverlayImage.fromResource(R.drawable.ic_place_marker)
        return marker
    }
}
