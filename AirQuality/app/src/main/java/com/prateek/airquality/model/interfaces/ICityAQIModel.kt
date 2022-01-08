package com.prateek.airquality.model.interfaces

interface ICityAQIModel : IBaseModel {

    fun getAQIValue() : Double

    fun getLastUpdatedTimeStamp() : Long
}