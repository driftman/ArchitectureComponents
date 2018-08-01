package com.driftman.fuckingandroid.di

import android.content.Context
import android.util.Log
import com.driftman.fuckingandroid.data.db.ApplicationDatabase
import com.driftman.fuckingandroid.data.network.api.PostAPI
import com.driftman.fuckingandroid.data.network.api.TodoAPI
import com.driftman.fuckingandroid.data.network.api.UserAPI
import com.driftman.fuckingandroid.data.network.service.PostService
import com.driftman.fuckingandroid.data.network.service.TodoService
import com.driftman.fuckingandroid.data.network.service.UserService
import com.driftman.fuckingandroid.log.db.LogDatabase
import com.driftman.fuckingandroid.log.network.LogAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class Injection {

    fun provideLogDatabase(context: Context) = LogDatabase.getInstance(context)

    fun provideLogDAO(context: Context) = provideLogDatabase(context).logDAO()

    fun provideApplicationDatabase(context: Context) = ApplicationDatabase.getInstance(context)

    fun provideUserDAO(context: Context) = provideApplicationDatabase(context).userDAO()

    fun provideTodoDAO(context: Context) = provideApplicationDatabase(context).todoDAO()

    fun providePostDAO(context: Context) = provideApplicationDatabase(context).postDAO()

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
    }

    fun provideESOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain?): Response {
                        val request: Request = chain!!.request()
                        val response: Response = chain!!.proceed(request);
                        try {

                            val copy = request.newBuilder().build()
                            val buffer = Buffer()
                            copy.body()!!.writeTo(buffer)
                            var bodyString =  buffer.readUtf8()
                            Log.d("shit", bodyString);
                        } catch (e: IOException) {

                        }

                        return response

                    }

                })
                .build()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun provideESRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9200")
                .client(provideESOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun provideLogAPI(): LogAPI {
        val retrofit: Retrofit = provideESRetrofit()
        return retrofit.create(LogAPI::class.java)
    }

    fun provideUserAPI(): UserAPI {
        val retrofit: Retrofit = provideRetrofit()
        return retrofit.create(UserAPI::class.java)
    }

    fun provideTodoAPI(): TodoAPI {
        val retrofit: Retrofit = provideRetrofit()
        return retrofit.create(TodoAPI::class.java)
    }

    fun providePostAPI(): PostAPI {
        val retrofit: Retrofit = provideRetrofit()
        return retrofit.create(PostAPI::class.java)
    }

    fun provideUserService(): UserService {
        val userAPI: UserAPI = provideUserAPI()
        return UserService(userAPI)
    }

    fun provideTodoService(): TodoService {
        val todoAPI: TodoAPI = provideTodoAPI()
        return TodoService(todoAPI)
    }

    fun providePostService(): PostService {
        val postAPI: PostAPI = providePostAPI()
        return PostService(postAPI)
    }
}