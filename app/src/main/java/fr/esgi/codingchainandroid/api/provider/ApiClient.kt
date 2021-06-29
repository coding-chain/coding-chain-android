package fr.esgi.codingchainandroid.api.provider

import android.content.Context
import fr.esgi.codingchainandroid.api.cacheManager.model.NetworkInterceptor
import fr.esgi.codingchainandroid.api.cacheManager.model.OfflineCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val BASE_URL: String = "http://10.0.2.2:5002/api/v1/"
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val networkInterceptor = NetworkInterceptor()

    //private val offlineCacheInterceptor = OfflineCacheInterceptor()
    private var client: OkHttpClient? = null;
    private var retrofit: Retrofit? = null

    public fun <T> buildService(service: Class<T>, context: Context): T {
        if (this.client === null) {
            this.updateClient(context)
            this.retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) // change this IP for testing by your actual machine IP
                .addConverterFactory(GsonConverterFactory.create()).client(this.client).build()
        }
        return retrofit?.create(service)!!
    }

    private fun getCache(context: Context): Cache {
        val cacheSize = 5 * 1024 * 1024.toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    private fun updateClient(context: Context) {
        this.client =
            OkHttpClient.Builder().cache(this.getCache(context)).addInterceptor(interceptor)
                .addNetworkInterceptor(
                    networkInterceptor
                )
                .addInterceptor(OfflineCacheInterceptor(context))
                .build()
    }
}