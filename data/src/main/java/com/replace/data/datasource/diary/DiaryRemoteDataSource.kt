package com.replace.data.datasource.diary

import com.replace.data.model.request.DiaryEditorRequest
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
    override suspend fun saveDiary(diaryEditorRequest: DiaryEditorRequest): CustomResult<Long> {
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

    private fun List<File>.generateMultiPartFromFile() =
        this.map { imgFile ->
            val requestFile = imgFile.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", imgFile.name, requestFile)
        }
}
