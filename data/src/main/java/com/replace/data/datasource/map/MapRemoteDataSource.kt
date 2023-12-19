package com.replace.data.datasource.map

import com.replace.data.model.response.DiaryCoordinatesResponse
import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.remote.CustomResult
import com.replace.data.service.MapService
import javax.inject.Inject

class MapRemoteDataSource @Inject constructor(
    private val mapService: MapService,
) : MapDataSource {
    override suspend fun getPlaceInfo(
        longitude: String,
        latitude: String,
    ): CustomResult<PlaceInfoResponse> {
        return mapService.getPlaceInfo(longitude, latitude)
    }

    override suspend fun getDiaryCoordinates(): CustomResult<DiaryCoordinatesResponse> {
        return mapService.getDiaryCoordinates()
    }
}
