package fr.esgi.codingchainandroid.api.cacheManager.model

import android.util.Log
import fr.esgi.codingchainandroid.api.cacheManager.api.data.Cacheable
import fr.esgi.codingchainandroid.api.cacheManager.api.data.HeaderConstants

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val invocation: Invocation? = request.tag(Invocation::class.java)

        if (invocation != null) {
            val annotation: Cacheable? =
                invocation.method().getAnnotation(Cacheable::class.java)

            /* check if this request has the [Cacheable] annotation */
            if (annotation != null &&
                annotation.annotationClass.simpleName.equals("Cacheable")
            ) {
                Log.d("Network interceptor", "network interceptor: called.")

                val response = chain.proceed(chain.request())

                val cacheControl = CacheControl.Builder()
                    .maxAge(30, TimeUnit.SECONDS)
                    .build()

                return response.newBuilder()
                    .removeHeader(HeaderConstants.HEADER_PRAGMA)
                    .removeHeader(HeaderConstants.HEADER_CACHE_CONTROL)
                    .header(HeaderConstants.HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
            }
        }
        return chain.proceed(request)
    }

}