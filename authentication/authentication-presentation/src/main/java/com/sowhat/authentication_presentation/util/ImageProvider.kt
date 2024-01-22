package com.sowhat.authentication_presentation.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.sowhat.authentication_presentation.R
import java.io.File

class ImageProvider : FileProvider(
    R.xml.file_paths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            // We get the path to the directory where we want the file to be stored.
            // this has to match one of the paths defined in our filepaths.xml file we described earlier.
            val directory = File(context.cacheDir, "images") // file_paths.xml 파일에 images라는 폴더로 정의되어 있으므로...
            // We create a temporary file in this directory.
            directory.mkdirs()

            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )

            //We get the authority for our content provider (which has to match the one we defined in the manifest).
            // in manifest : android:authorities="com.practice.android_file_practice.fileprovider"
            val authority = context.packageName + ".fileprovider"

            return getUriForFile(
                context, authority, file
            )
        }
    }
}