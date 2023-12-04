package com.replace.data.repository.map

import com.replace.data.datasource.map.MapDataSource
import com.replace.data.model.response.DiaryCoordinatesResponse
import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.remote.CustomResult
import javax.inject.Inject

class DefaultMapRepository @Inject constructor(
    private val mapDataSource: MapDataSource,
) : MapRepository {
    override suspend fun getPlaceInfo(
        longitude: String,
        latitude: String,
    ): CustomResult<PlaceInfoResponse> {
        return mapDataSource.getPlaceInfo(longitude, latitude)
    }

    override suspend fun getDiaryCoordinates(): CustomResult<DiaryCoordinatesResponse> {
        return mapDataSource.getDiaryCoordinates()
    }
}
