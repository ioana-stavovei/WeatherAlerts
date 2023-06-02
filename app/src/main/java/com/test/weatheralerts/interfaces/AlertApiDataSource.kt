package com.test.weatheralerts.interfaces

import com.test.weatheralerts.model.Alerts
import io.reactivex.Single
import retrofit2.Response

interface AlertApiDataSource {
    fun getAllWeatherAlerts(): Single<Alerts>
    fun getImages() : Single<Response<String>>
}