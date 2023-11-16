package com.replace.data.datasource.diary

import com.replace.data.model.request.DiaryEditorRequest
import com.replace.data.model.response.DiariesResponse
import com.replace.data.model.response.DiaryDetailResponse
import com.replace.data.model.response.DiaryEditorImageResponse
import com.replace.data.remote.CustomResult
import com.replace.data.service.DiaryService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class DiaryRemoteDataSource @Inject constructor(
    private val diaryService: DiaryService,
) : DiaryDataSource {
    override suspend fun saveDiary(diaryEditorRequest: DiaryEditorRequest): CustomResult<Unit> {
        return diaryService.saveDiary(diaryEditorRequest)
    }

    override suspend fun saveDiaryImages(images: List<File>): CustomResult<DiaryEditorImageResponse> {
        return diaryService.saveDiaryImages(images.generateMultiPartFromFile())
    }

    override suspend fun updateDiary(
        diaryId: Long,
        diaryEditorRequest: DiaryEditorRequest,
    ): CustomResult<Unit> {
        return diaryService.updateDiary(diaryId, diaryEditorRequest)
    }

    override suspend fun deleteDiary(diaryId: Long): CustomResult<Unit> {
        return diaryService.deleteDiary(diaryId)
    }

    override suspend fun getDiaryDetail(diaryId: Long): CustomResult<DiaryDetailResponse> {
        return diaryService.getDiaryDetail(diaryId)
    }

    override suspend fun getDiariesWithDate(
        year: Int,
        month: Int,
        day: Int,
    ): CustomResult<DiariesResponse> {
        return diaryService.getDiariesWithDate(year, month, day)
    }

    private fun List<File>.generateMultiPartFromFile() =
        this.map { imgFile ->
            val requestFile = imgFile.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", imgFile.name, requestFile)
        }
}
