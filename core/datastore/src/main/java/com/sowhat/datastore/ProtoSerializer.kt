package com.sowhat.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.sowhat.datastore.model.AuthData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

const val DATASTORE_AUTH = "auth_prefs.pb"

object AuthDataSerializer : Serializer<AuthData> by ProtoSerializer(
    serializer = AuthData.serializer(),
    default = AuthData(
        accessToken = null,
        refreshToken = null,
        platformToken = null,
        platformStatus = null,
        fcmToken = null,
        deviceNumber = null,
        memberId = null
    )
)

class ProtoSerializer<T>(
    private val serializer: KSerializer<T>,
    private val default: T
) : Serializer<T> {
    override val defaultValue: T
        get() = default

    override suspend fun readFrom(input: InputStream): T {
        return try {
            // read -> parse input(parameter/ json) into the specified class
            Json.decodeFromString(serializer, input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: T, output: OutputStream) {
        output.write(Json.encodeToString(serializer, t).encodeToByteArray())
    }
}

val Context.authDataStore: DataStore<AuthData> by dataStore(
    fileName = DATASTORE_AUTH,
    serializer = AuthDataSerializer
)