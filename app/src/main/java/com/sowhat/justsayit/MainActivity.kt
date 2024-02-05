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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.sowhat.designsystem.theme.JustSayItTheme
import com.sowhat.justsayit.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustSayItTheme {
                val context = LocalContext.current
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                RequestPermissions()

                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(JustSayItTheme.Colors.mainBackground),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->
                    val padding = paddingValues
                    AppNavHost(
                        navController = navController,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
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