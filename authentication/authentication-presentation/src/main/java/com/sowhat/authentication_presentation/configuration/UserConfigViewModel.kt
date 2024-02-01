package com.sowhat.authentication_presentation.configuration

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.authentication_domain.model.NewMember
import com.sowhat.authentication_domain.use_case.PostNewMemberUseCase
import com.sowhat.authentication_domain.use_case.ValidateDayUseCase
import com.sowhat.authentication_domain.use_case.ValidateGenderUseCase
import com.sowhat.authentication_domain.use_case.ValidateMonthUseCase
import com.sowhat.authentication_domain.use_case.ValidateNicknameUseCase
import com.sowhat.authentication_domain.use_case.ValidateYearUseCase
import com.sowhat.common.model.RegistrationFormEvent
import com.sowhat.common.model.Resource
import com.sowhat.common.model.SignUpEvent
import com.sowhat.common.model.UiState
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.network.util.getRequestBody
import com.sowhat.network.util.toBearerToken
import com.sowhat.common.model.RegistrationFormState
import com.sowhat.authentication_presentation.common.RegistrationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserConfigViewModel @Inject constructor(
    private val postNewMemberUseCase: PostNewMemberUseCase,
    private val authDataStore: AuthDataRepository,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase,
    private val validateYearUseCase: ValidateYearUseCase,
    private val validateMonthUseCase: ValidateMonthUseCase,
    private val validateDayUseCase: ValidateDayUseCase
) : ViewModel() {
    // 각 항목에 대한 유효성 검사를 use case를 활용해 진행
    var formState by mutableStateOf(RegistrationFormState())
        private set

    private val _uiState: MutableStateFlow<UiState<NewMember>> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState<NewMember>>
        get() = _uiState

    var imageUri by mutableStateOf<Uri?>(null)

    val isFormValid by derivedStateOf {
        formState.isNicknameValid && formState.isGenderValid && formState.isYearValid
                && formState.isMonthValid && formState.isDayValid
    }

    val tokens = viewModelScope.async { authDataStore.authData.first() }

    private val signUpEventChannel = Channel<SignUpEvent>()
    val signUpEvent = signUpEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.ProfileChanged -> {
                formState = formState.copy(
                    profileImage = event.image,
                )
            }
            is RegistrationFormEvent.NicknameChanged -> {
                formState = formState.copy(
                    nickname = event.nickname,
                    isNicknameValid = validateNicknameUseCase(event.nickname).isValid
                )
            }
            is RegistrationFormEvent.GenderChanged -> {
                formState = formState.copy(
                    gender = event.gender,
                    isGenderValid = validateGenderUseCase(event.gender).isValid
                )
            }
            is RegistrationFormEvent.YearChanged -> {
                formState = formState.copy(
                    year = event.year,
                    isYearValid = validateYearUseCase(event.year).isValid
                )
            }
            is RegistrationFormEvent.MonthChanged -> {
                formState = formState.copy(
                    month = event.month,
                    isMonthValid = validateMonthUseCase(event.month).isValid
                )
            }
            is RegistrationFormEvent.DayChanged -> {
                formState = formState.copy(
                    day = event.day,
                    isDayValid = validateDayUseCase(event.day).isValid
                )
            }
            is RegistrationFormEvent.Submit -> {
                // complete button
                signUp()
            }
        }
    }

    private fun isValid(): Boolean {
        val nicknameResult = validateNicknameUseCase(formState.nickname)
        val genderResult = validateGenderUseCase(formState.gender)
        val yearResult = validateYearUseCase(formState.year)
        val monthResult = validateYearUseCase(formState.month)
        val dayResult = validateYearUseCase(formState.day)

        val results = listOf(nicknameResult, genderResult, yearResult, monthResult, dayResult)

        val isNotValid = results.any { !it.isValid }

        return !isNotValid
    }

    fun signUp() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            if (!isFormValid) {
                _uiState.value = _uiState.value.copy(isLoading = false, data = null)
                signUpEventChannel.send(SignUpEvent.Error("조건을 만족하지 못하였습니다."))
                return@launch
            }

            val availableTokens = tokens.await()

            val platformToken = availableTokens.platformToken
            val platform = availableTokens.platformStatus

            if (platformToken == null || platform == null) {
                _uiState.value = _uiState.value.copy(isLoading = false, data = null)
                signUpEventChannel.send(SignUpEvent.Error("로그인 플랫폼이 존재하지 않습니다."))
                return@launch
            }

            val birth = "${formState.year}-${formState.month}-${formState.day}"
            val gender = if (formState.gender == "남") "MALE" else "FEMALE"
            val loginType = platform
            val nickname = formState.nickname
            val token = platformToken

//            val jsonObject = JSONObject(
//            "{\"token\":\"${token}\", " +
//                    "\"nickname\":\"${nickname}\"," +
//                    "\"loginType\":\"${loginType}\"," +
//                    "\"gender\":\"${gender}\"," +
//                    "\"birth\":\"${birth}\"}"
//            ).toString()

            val requestBody = RegistrationRequest(
                token = token,
                nickname = nickname,
                loginType = loginType,
                gender = gender,
                birth = birth
            )

            val requestPart = getRequestBody(requestBody)

//            val request = Gson().toJson(requestBody).toRequestBody("application/json".toMediaTypeOrNull())
            Log.i("UserConfigScreen", requestBody.toString())

            val result = postNewMemberUseCase(
                loginInfo = requestPart,
                profileImage = formState.profileImage
            )

            when (result) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, data = result.data)
                    val accessToken = result.data?.accessToken
                    val memberId = result.data?.memberId
                    if (accessToken != null || memberId != null) {
                        authDataStore.updateAccessToken(accessToken!!)
                        authDataStore.updateMemberId(memberId!!)
                        signUpEventChannel.send(SignUpEvent.NavigateToMain)
                    }
                    signUpEventChannel.send(SignUpEvent.Error("토큰이 없습니다."))
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                    signUpEventChannel.send(SignUpEvent.Error(result.message ?: "예상치 못한 오류가 발생했습니다."))
                }
            }
        }
    }
}