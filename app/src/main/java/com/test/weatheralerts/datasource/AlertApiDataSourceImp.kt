package com.test.weatheralerts.datasource

import com.test.weatheralerts.interfaces.AlertApiDataSource
import com.test.weatheralerts.model.Alerts
import com.test.weatheralerts.network.AlertApiInterface
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class AlertApiDataSourceImp  @Inject constructor(
    private val alertApiInterface: AlertApiInterface
) : AlertApiDataSource {
    override fun getAllWeatherAlerts(): Single<Alerts> {
        return alertApiInterface.getAllWeatherAlerts()
    }

    override fun getImages() : Single<Response<String>> {
        return alertApiInterface.getImages()
    }
}