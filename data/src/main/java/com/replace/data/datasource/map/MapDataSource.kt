package com.replace.data.datasource.map

import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.remote.CustomResult

interface MapDataSource {
    suspend fun getPlaceInfo(
        longitude: String,
        latitude: String,
    ): CustomResult<PlaceInfoResponse>
}
