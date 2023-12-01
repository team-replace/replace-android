package com.replace.data.service

import com.replace.data.model.response.PlaceInfoResponse
import com.replace.data.remote.CustomResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {

    @GET("/map")
    fun getPlaceInfo(
        @Query("longitude") longitude: String,
        @Query("latitude") latitude: String,
    ): CustomResult<PlaceInfoResponse>
}
