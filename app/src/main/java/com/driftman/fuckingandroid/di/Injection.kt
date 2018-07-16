package com.driftman.fuckingandroid.di

import android.content.Context
import com.driftman.fuckingandroid.data.db.dao.UserDAO
import com.driftman.fuckingandroid.data.db.ApplicationDatabase
import com.driftman.fuckingandroid.data.db.dao.PostDAO
import com.driftman.fuckingandroid.data.db.dao.TodoDAO
import com.driftman.fuckingandroid.data.network.api.PostAPI
import com.driftman.fuckingandroid.data.network.api.TodoAPI
import com.driftman.fuckingandroid.data.network.api.UserAPI
import com.driftman.fuckingandroid.data.network.service.PostService
import com.driftman.fuckingandroid.data.network.service.TodoService
import com.driftman.fuckingandroid.data.network.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    fun provideUserDAO(context: Context): UserDAO {
        val userDatabase: ApplicationDatabase = ApplicationDatabase.getInstance(context)
        return userDatabase.userDAO()
    }

    fun provideTodoDAO(context: Context): TodoDAO {
        val userDatabase: ApplicationDatabase = ApplicationDatabase.getInstance(context)
        return userDatabase.todoDAO()
    }

    fun providePostDAO(context: Context): PostDAO {
        val userDatabase: ApplicationDatabase = ApplicationDatabase.getInstance(context)
        return userDatabase.postDAO()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
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