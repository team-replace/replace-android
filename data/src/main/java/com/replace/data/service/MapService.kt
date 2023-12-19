package com.replace.data.service

import com.replace.data.model.response.DiaryCoordinatesResponse
import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.remote.CustomResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {

    @GET("/map")
    suspend fun getPlaceInfo(
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
    ): CustomResult<PlaceInfoResponse>

    @GET("/map/coordinate")
    suspend fun getDiaryCoordinates(): CustomResult<DiaryCoordinatesResponse>
}
