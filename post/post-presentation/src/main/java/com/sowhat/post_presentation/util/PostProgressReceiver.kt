package com.sowhat.post_presentation.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class PostProgressReceiver : BroadcastReceiver() {

        // triggered when Broadcast is fired!
        override fun onReceive(context: Context?, intent: Intent?) {
            // **Broadcast를 보냈을 때 정의하였던 action을 통해 intention을 정의하였을 것**
            // **받는 측에서는 그 intention을 받아 처리하고자 하므로 보낸 곳에서 정의하였던 action과 동일한 action이 왔을 때에만 처리해야 할 것**
            // **action 외에도, data가 왔다면 그 data 역시 사용 가능**
//            intent?.let {
//                val isSuccessful = it.getBooleanExtra(POST_SUCCESS, false)
//                Log.d(TAG, "isSuccessful : $isSuccessful")
//                if (isSuccessful) {
//
//                } else {
//
//                }
//            }
            intent?.action?.let {
                when (it) {
                    PostProgressService.Actions.START.toString() -> {
                        Toast.makeText(context, "게시글을 업로드를 시작합니다.", Toast.LENGTH_SHORT).show()
                    }
                    PostProgressService.Actions.SUCCESS.toString() -> {
                        Toast.makeText(context, "게시글 업로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    PostProgressService.Actions.ERROR.toString() -> {
                        Toast.makeText(context, "게시글을 업로드하지 못하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    companion object {
        const val TAG = "AirPlaneModeReceiver"

        const val POST_SUCCESS = "post_success"
    }
}