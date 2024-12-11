package com.example.learncompose.data

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.learncompose.domain.LinesRepository
import com.example.learncompose.domain.MetroLine
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class LinesRepositoryWebImpl(
    private val client: OkHttpClient
) : LinesRepository {
    override suspend fun loadData(): List<MetroLine> {
        val request = Request.Builder()
            .url("https://metro-server-rc8a.onrender.com/get_lines")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException(
                        "Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}"
                    )
                }

                val respBody = response.body!!.string()

                Log.d("OkHttp", "Response was successful")
                Log.d("OkHttp", "Response body: $respBody}")

                val deserializedLines = Json.decodeFromString<MetroResponse>(respBody)

                return deserializedLines.lines.map { it.toMetroLine() }
            }
        } catch (e: IOException) {
            Log.d("OkHttp", "Some error occured ${e.message}")
            return emptyList()
        }
    }
}

@Serializable
data class MetroLineResponse(
    val num: String,
    val name: String,
    val color: Long,
    val stations: List<String>
)

@Serializable
data class MetroResponse(
    val lines: List<MetroLineResponse>
)

fun MetroLineResponse.toMetroLine(): MetroLine {
    return MetroLine(
        num = num.toInt(),
        name = name,
        color = Color(color),
        stations = stations
    )
}
