package com.test.weatheralerts.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.weatheralerts.interfaces.AlertApiDataSource
import com.test.weatheralerts.model.AlertProperties
import com.test.weatheralerts.model.Alerts
import com.test.weatheralerts.utils.applySchedulersWithObserverSingle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class WeatherAlertViewModel @Inject constructor(private val alertApiDataSource: AlertApiDataSource) :
    BaseViewModel() {

    val alertsList: MutableLiveData<List<AlertProperties>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val hasError: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAlerts() {
        alertApiDataSource.run {
            getAllWeatherAlerts().compose(applySchedulersWithObserverSingle())
                .doOnSubscribe { isLoading.postValue(true) }
                .subscribeWith(object : DisposableSingleObserver<Alerts>() {
                    override fun onError(e: Throwable) {
                        isLoading.postValue(false)
                        hasError.postValue(true)
                    }

                    override fun onSuccess(alerts: Alerts) {
                        alerts.alertProperties.forEach { alertProperties ->
                            viewModelScope.launch {
                                getImages(alertProperties)
                            }
                        }
                        alertsList.postValue(alerts.alertProperties)
                        isLoading.postValue(false)
                    }
                }).addSubscription()
        }

    }

    fun getImages(alertProperties: AlertProperties) {
        alertApiDataSource.run {
            getImages().compose(applySchedulersWithObserverSingle())
                .doOnSubscribe { isLoading.postValue(true) }
                .subscribeWith(object : DisposableSingleObserver<Response<String>>() {
                    override fun onError(e: Throwable) {
                        hasError.postValue(true)
                    }

                    override fun onSuccess(response: Response<String>) {
                        alertProperties.alertEntity.imageUrl = response.raw().request.url.toString()
                    }
                }).addSubscription()
        }
    }
}