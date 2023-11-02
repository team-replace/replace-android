package com.replace.data.service

import com.replace.data.model.request.DiaryEditorRequest
import com.replace.data.model.response.DiaryDetailResponse
import com.replace.data.model.response.DiaryEditorImageResponse
import com.replace.data.remote.CustomResult
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface DiaryService {

    @POST("/diary")
    suspend fun saveDiary(
        @Body diaryEditorRequest: DiaryEditorRequest,
    ): CustomResult<Unit>

    @POST("/diary/images")
    @Multipart
    suspend fun saveDiaryImages(
        @Part images: List<MultipartBody.Part>,
    ): CustomResult<DiaryEditorImageResponse>

    @PUT("/diary/{diaryId}")
    suspend fun updateDiary(
        @Path("diaryId") diaryId: Long,
        @Body diaryEditorRequest: DiaryEditorRequest,
    ): CustomResult<Unit>

    @DELETE("/diary/{diaryId}")
    suspend fun deleteDiary(
        @Path("diaryId") diaryId: Long,
    ): CustomResult<Unit>

    @GET("/diary/{diaryId}")
    suspend fun getDiaryDetail(
        @Path("diaryId") diaryId: Long,
    ): CustomResult<DiaryDetailResponse>
}
