package com.brandy.cooperativeapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.brandy.cooperativeapp.data.local.Preference
import com.brandy.cooperativeapp.data.network.ApiService
import com.brandy.cooperativeapp.data.repository.AuthRepositoryImpl
import com.brandy.cooperativeapp.data.utils.Constant.BASE_URL
import com.brandy.cooperativeapp.data.utils.Constant.COP_PREFERENCE
import com.brandy.cooperativeapp.domain.repository.AuthRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Okhttp client
    @[Provides Singleton]
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    //ApiService
    @[Provides Singleton]
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatastorePreferences(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(COP_PREFERENCE)
            },
        )

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun providePreferences(
        dataStore: DataStore<Preferences>,
        gson: Gson
    ) = Preference(dataStore, gson)

    //Auth Repository
    @[Provides Singleton]
    fun provideAuthRepository(apiService: ApiService, preference: Preference): AuthRepository =
        AuthRepositoryImpl(apiService = apiService, preference = preference)


}