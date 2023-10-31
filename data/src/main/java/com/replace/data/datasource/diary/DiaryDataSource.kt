package com.replace.data.datasource.diary

import com.replace.data.model.request.DiaryEditorRequest
import com.replace.data.model.response.DiaryEditorImageResponse
import com.replace.data.remote.CustomResult
import java.io.File

interface DiaryDataSource {
    suspend fun saveDiary(
        diaryEditorRequest: DiaryEditorRequest,
    ): CustomResult<Long>

    suspend fun saveDiaryImages(
        images: List<File>,
    ): CustomResult<DiaryEditorImageResponse>

    suspend fun updateDiary(
        diaryId: Long,
        diaryEditorRequest: DiaryEditorRequest,
    ): CustomResult<Unit>

    suspend fun deleteDiary(
        diaryId: Long,
    ): CustomResult<Unit>
}
