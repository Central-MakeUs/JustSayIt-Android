package com.sowhat.justsayitt

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sowhat.datastore.AuthDataRepository
import com.sowhat.datastore.model.AuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProtoDataViewModel @Inject constructor(
    private val authDataRepository: AuthDataRepository
): ViewModel() {

    companion object {
        const val TAG = "ProtoDataViewModel"
    }

    // 사용할 곳(UI)에서 collectAsState 적용
    val tokens = authDataRepository.authData

    fun updatePlatform(platform: String) {
        viewModelScope.launch {
            authDataRepository.updatePlatform(platform)
            Log.d(TAG, "${authDataRepository.authData}")
        }
    }

    fun updatePlatformToken(platformToken: String) {
        viewModelScope.launch {
            authDataRepository.updatePlatformToken(platformToken)
            Log.d(TAG, "${authDataRepository.authData}")
        }
    }

    fun updateAccessToken(accessToken: String) {
        viewModelScope.launch {
            authDataRepository.updateAccessToken(accessToken)
        }
    }

    fun updateRefreshToken(refreshToken: String) {
        viewModelScope.launch {
            authDataRepository.updateRefreshToken(refreshToken)
        }
    }

    fun updateFcmToken(fcmToken: String) {
        viewModelScope.launch {
            authDataRepository.updateFcmToken(fcmToken)
        }
    }

    fun updateDeviceNumber(deviceNumber: String) {
        viewModelScope.launch {
            authDataRepository.updateDeviceNumber(deviceNumber)
        }
    }

    fun resetToken() {
        viewModelScope.launch {
            authDataRepository.resetData()
        }
    }
}