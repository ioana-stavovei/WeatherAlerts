package com.test.weatheralerts.network

import com.test.weatheralerts.model.Alerts
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface AlertApiInterface {
    @GET("https://api.weather.gov/alerts/active?status=actual&message_type=alert")
    fun getAllWeatherAlerts(): Single<Alerts>

    @GET("https://picsum.photos/1000")
    fun getImages(): Single<Response<String>>
}