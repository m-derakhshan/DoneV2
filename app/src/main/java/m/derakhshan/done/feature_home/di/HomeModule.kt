package m.derakhshan.done.feature_home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.derakhshan.done.core.data.data_source.DoneDatabase
import m.derakhshan.done.core.utils.AppConstants
import m.derakhshan.done.feature_home.data.data_source.HomeApi
import m.derakhshan.done.feature_home.data.repository.HomeRepositoryImpl
import m.derakhshan.done.feature_home.domain.repository.HomeRepository
import m.derakhshan.done.feature_home.domain.use_case.GetInspirationQuoteUseCase
import m.derakhshan.done.feature_home.domain.use_case.HomeUseCases
import m.derakhshan.done.feature_home.domain.use_case.UpdateInspirationQuotesUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeApi(): HomeApi {
        return Retrofit.Builder()
            .baseUrl(AppConstants.QUOTE_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }


    @Provides
    @Singleton
    fun provideHomeRepository(
        database: DoneDatabase,
        homeApi: HomeApi
    ): HomeRepository {
        return HomeRepositoryImpl(
            inspirationQuoteDao = database.inspirationQuoteDao,
            homeApi = homeApi
        )
    }


    @Provides
    @Singleton
    fun provideHomeUseCases(repository: HomeRepository): HomeUseCases {
        return HomeUseCases(
            getInsertInspirationQuoteUseCase = GetInspirationQuoteUseCase(repository = repository),
            updateInspirationQuotesUseCase = UpdateInspirationQuotesUseCase(repository = repository)
        )
    }


}