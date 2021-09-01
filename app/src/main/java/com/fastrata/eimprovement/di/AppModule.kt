package com.fastrata.eimprovement.di

import com.fastrata.eimprovement.BuildConfig
import com.fastrata.eimprovement.api.AppService
import com.fastrata.eimprovement.api.AuthInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    /* --------------------------------- Provide Remote Data Source ------------------------------*/
    @Singleton
    @Provides
    fun provideSurveyService(@AppAPI okhttpClient: OkHttpClient,
                             converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, AppService::class.java)

    /*@Singleton
    @Provides
    fun provideGoogleService(okhttpClient: OkHttpClient, converterFactory: GsonConverterFactory) = provideGoogleService(okhttpClient, converterFactory, GoogleService::class.java)*/

    /*@Singleton
    @Provides
    fun provideRemoteDataSource(surveyService: AppService) = MasterSurveyRemoteDataSource(surveyService)*/
    //fun provideRemoteDataSource(surveyService: AppService, googleService: GoogleService) = MasterSurveyRemoteDataSource(surveyService, googleService)

    @AppAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN))
            .build()
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /*private fun createGoogleRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GoogleService.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }*/

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    /*private fun <T> provideGoogleService(okhttpClient: OkHttpClient,
                                         converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createGoogleRetrofit(okhttpClient, converterFactory).create(clazz)
    }*/

    /* --------------------------------- Provide Local Database Data Source ----------------------*/
    /*@Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideMasterSurvey(db: AppDatabase) = db.masterSurveyDao()

    @Singleton
    @Provides
    fun provideMasterKecamatan(db: AppDatabase) = db.masterKecamatanDao()

    @Singleton
    @Provides
    fun provideSurveyHeader(db: AppDatabase) = db.surveyHeaderDao()

    @Singleton
    @Provides
    fun provideMasterCustomers(db: AppDatabase) = db.masterCustomerDao()

    @Singleton
    @Provides
    fun provideInsertSurveyDetail(db: AppDatabase) = db.surveyDetailDao()

    @Singleton
    @Provides
    fun provideInsertCapturedPhoto(db: AppDatabase) = db.surveyPhotoDao()*/

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

}
