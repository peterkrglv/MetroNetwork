//package com.example.learncompose.data
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.util.Log
//import com.example.learncompose.domain.Post
//import com.example.learncompose.domain.PostRepository
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.json.Json
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody.Companion.toRequestBody
//import java.io.ByteArrayOutputStream
//
//class PostRepositoryWebImpl(private val client: OkHttpClient) : PostRepository {
//    override suspend fun getPosts(station: String): List<Post> {
//        val request = Request.Builder()
//            .url("https://metro-server-rc8a.onrender.com/get_posts?station=$station")
//            .build()
//        try {
//            client.newCall(request).execute().use { response ->
//                if (!response.isSuccessful) {
//                    throw Exception("Request to server was not successful: ${response.code} ${response.message}")
//                }
//                val respBody = response.body!!.string()
//                Log.d("OkHttp", "Response was successful response body: $respBody")
//                val deserializedPosts = Json.decodeFromString<PostResponce>(respBody)
//                var posts = mutableListOf<Post>()
//                for (post in deserializedPosts.posts) {
//                    posts.add(post.toPost())
//                }
//                Log.d("OkHttp", "Posts: $posts")
//                return posts
//            }
//        } catch (e: Exception) {
//            return emptyList()
//        }
//    }
//
//    override suspend fun uploadPost(
//        username: String,
//        station: String,
//        text: String,
//        photo: Uri
//    ): Boolean {
//        val inputStream = context.contentResolver.openInputStream(photo)
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        val requestBody = MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart(
//                "image",
//                "image.jpg",
//                byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
//            )
//            .build()
//
//        val request = Request.Builder()
//            .url("https://metro-server-rc8a.onrender.com/upload_post?username=$username&station=$station&text=$text")
//            .post(requestBody)
//            .build()
//        try {
//            client.newCall(request).execute().use { response ->
//                if (!response.isSuccessful) {
//                    throw Exception("Request to server was not successful: ${response.code} ${response.message}")
//                }
//                val respBody = response.body!!.string()
//                Log.d("OkHttp", "Response was successful response body: $respBody")
//                return true
//            }
//        } catch (e: Exception) {
//            return false
//        }
//    }
//}
//
//@Serializable
//data class PostResponce(
//    val posts: List<PostData>
//)
//
//@Serializable
//data class PostData(
//    val username: String,
//    val text: String,
//    val date: String,
//    val image: String
//)
//
//fun PostData.toPost(): Post {
//    return Post(
//        username = username,
//        text = text,
//        date = date,
//        image = image
//    )
//}
//
