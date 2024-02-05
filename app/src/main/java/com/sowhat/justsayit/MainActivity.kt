package com.sowhat.justsayit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.sowhat.common.model.FCMData
import com.sowhat.common.util.getDate
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.justsayit.navigation.AppNavHost
import com.sowhat.notification.use_case.InsertNotificationDataUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var title: String
    private lateinit var body: String
    private lateinit var targetCategory: String
    private lateinit var targetData: String
    private lateinit var date: String

    @Inject
    lateinit var insertNotificationDataUseCase: InsertNotificationDataUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fcmData = intent.extras?.let { getFcmData(it) }

            LaunchedEffect(key1 = fcmData) {
                if (fcmData != null) insertNotificationDataUseCase(fcmData)
            }

            JustSayItTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                RequestPermissions()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(JustSayItTheme.Colors.mainBackground),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
                    val padding = paddingValues
                    AppNavHost(
                        navController = navController,
                        snackbarHostState = snackbarHostState,
                        fcmData = fcmData
                    )
                }
            }
        }
    }

    private fun getFcmData(it: Bundle): FCMData {
        title = it.getString("title", "")
        body = it.getString("body", "")
        targetCategory = it.getString("targetCategory", "")
        targetData = it.getString("targetData", "")
        date = it.getString("date", getDate(System.currentTimeMillis()))
        Log.d(
            "MainActivity", "title: $title, body: $body, targetCategory: $targetCategory, " +
                    "targetPK: $targetCategory, targetNotificationPK: $targetData, date: $date"
        )

        return FCMData(
            title = title,
            body = body,
            targetCategory = targetCategory,
            targetData = targetData,
            date = date
        )
    }

    @Composable
    private fun RequestPermissions() {
        val context = LocalContext.current

        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyArray()
        }

        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                Log.d("권한", "권한이 동의되었습니다.")
            } else {
                Log.d("권한", "권한이 거부되었습니다.")
            }
        }

        // 권한이 이미 있는 경우
        if (permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }) {
            Log.d("권한", "권한이 이미 존재합니다.")
        } else {
            Log.d("권한", "권한이 존재하지 않습니다.")
            SideEffect {
                launcherMultiplePermissions.launch(permissions)
            }
        }
    }
}