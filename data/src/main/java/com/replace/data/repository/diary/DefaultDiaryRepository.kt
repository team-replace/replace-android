package com.replace.data.repository.diary

import com.replace.data.datasource.diary.DiaryDataSource
import com.replace.data.model.CoordinateDataModel
import com.replace.data.model.request.DiaryEditorRequest
import com.replace.data.model.request.DiaryEditorSaveRequest
import com.replace.data.model.response.DiariesResponse
import com.replace.data.model.response.DiaryDetailResponse
import com.replace.data.model.response.DiaryEditorImageResponse
import com.replace.data.remote.CustomResult
import java.io.File
import javax.inject.Inject

class DefaultDiaryRepository @Inject constructor(
    private val diaryDataSource: DiaryDataSource,
) : DiaryRepository {
    override suspend fun saveDiary(
        images: List<String>,
        title: String,
        content: String,
        shareScope: String,
        coordinateDataModel: CoordinateDataModel,
    ): CustomResult<Unit> {
        return diaryDataSource.saveDiary(DiaryEditorSaveRequest(images, title, content, shareScope, coordinateDataModel))
    }

    override suspend fun saveDiaryImages(images: List<File>): CustomResult<DiaryEditorImageResponse> {
        return diaryDataSource.saveDiaryImages(images)
    }

    override suspend fun updateDiary(
        diaryId: Long,
        images: List<String>,
        title: String,
        content: String,
        shareScope: String,
    ): CustomResult<Unit> {
        return diaryDataSource.updateDiary(
            diaryId,
            DiaryEditorRequest(images, title, content, shareScope),
        )
    }

    override suspend fun deleteDiary(diaryId: Long): CustomResult<Unit> {
        return diaryDataSource.deleteDiary(diaryId)
    }

    override suspend fun getDiaryDetail(diaryId: Long): CustomResult<DiaryDetailResponse> {
        return diaryDataSource.getDiaryDetail(diaryId)
    }

    override suspend fun getDiariesWithDate(
        year: Int,
        month: Int,
        day: Int,
    ): CustomResult<DiariesResponse> {
        return diaryDataSource.getDiariesWithDate(year, month, day)
    }
}
