package com.vectorinc.task.di


import android.app.Application
import androidx.room.Room
import com.vectorinc.task.data.local.Database
import com.vectorinc.task.data.remote.ApiService
import com.vectorinc.task.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val baseUrl = Constants.BASE_URL

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "characters.db",
        ).build()
    }


}