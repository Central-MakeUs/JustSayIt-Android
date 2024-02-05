package com.sowhat.report_domain.use_case

import com.sowhat.report_domain.model.TodayMood
import com.sowhat.report_domain.repository.ReportRepository
import com.sowhat.common.model.Resource
import com.sowhat.datastore.AuthDataRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTodayMoodDataUseCase @Inject constructor(
    private val authDataRepository: AuthDataRepository,
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(): Resource<TodayMood> = try {
        val accessToken = authDataRepository.authData.first().accessToken
        accessToken?.let {
            reportRepository.getTodayMoodData(accessToken)
        } ?: Resource.Error(
            message = "서비스를 이용하기 위해 로그인이 필요합니다."
        )
    } catch (e: Exception) {
        Resource.Error(message = "알 수 없는 에러가 발생하였습니다.")
    }
}