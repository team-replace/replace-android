package com.replace.data.repository.map

import com.replace.data.model.response.DiaryCoordinatesResponse
import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.remote.CustomResult

interface MapRepository {
    suspend fun getPlaceInfo(
        longitude: String,
        latitude: String,
    ): CustomResult<PlaceInfoResponse>

    suspend fun getDiaryCoordinates(): CustomResult<DiaryCoordinatesResponse>
}
