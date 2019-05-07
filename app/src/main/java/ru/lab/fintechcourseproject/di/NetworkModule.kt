package ru.lab.fintechcourseproject.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.lab.fintechcourseproject.network.NetworkService
import ru.lab.fintechcourseproject.network.RetrofitNetworkClient

@Module
class NetworkModule(private val retrofitNetworkClient: RetrofitNetworkClient) {
    @Provides
    fun provideRetrofit(): Retrofit {
        return retrofitNetworkClient.newInstance()
    }

    @Provides
    fun provideFintechService(): NetworkService {
        return retrofitNetworkClient.newNetworkService()
    }
}
