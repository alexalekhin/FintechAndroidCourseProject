package ru.lab.fintechcourseproject.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNetworkClient {
    private var retrofit: Retrofit? = null
    private val client = OkHttpClient.Builder().build()
    const val BASE_URL: String = "https://fintech.tinkoff.ru/"

    fun newInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }


    fun newNetworkService(): NetworkService {
        return retrofit?.create(NetworkService::class.java) ?: newInstance().create(NetworkService::class.java)
    }
}
