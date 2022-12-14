package com.vectorinc.task.di


import android.app.Application
import androidx.room.Room
import com.vectorinc.task.data.local.Database
import com.vectorinc.task.data.remote.ApiService
import com.vectorinc.task.utils.Constants
import com.vectorinc.task.utils.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class AppModule {


    protected open fun baseUrl() = Constants.BASE_URL.toHttpUrl()


    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
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


    @Provides
    @Singleton
    fun provideDispatchers(): DispatchersProvider  = object  : DispatchersProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }


}