package com.replace.data.repository.diary

import com.replace.data.model.response.DiaryEditorImageResponse
import com.replace.data.remote.CustomResult
import java.io.File

interface DiaryRepository {

    suspend fun saveDiary(
        images: List<String>,
        title: String,
        content: String,
        shareScope: String,
    ): CustomResult<Long>

    suspend fun saveDiaryImages(
        images: List<File>,
    ): CustomResult<DiaryEditorImageResponse>

    suspend fun updateDiary(
        diaryId: Long,
        images: List<String>,
        title: String,
        content: String,
        shareScope: String,
    ): CustomResult<Unit>

    suspend fun deleteDiary(
        diaryId: Long,
    ): CustomResult<Unit>
}
