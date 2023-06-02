package com.test.weatheralerts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Alerts(
    @Expose
    @SerializedName("features")
    val alertProperties: List<AlertProperties>
)

data class AlertProperties(
    @Expose
    @SerializedName("properties")
    val alertEntity: AlertEntity
)

data class AlertEntity(
    @Expose
    @SerializedName("event")
    val name: String,
    @Expose
    @SerializedName("effective")
    val startDate: String?,
    @Expose
    @SerializedName("ends")
    val endDate: String?,
    @Expose
    @SerializedName("senderName")
    val sourceName: String,
    var imageUrl: String
)