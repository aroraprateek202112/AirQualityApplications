package com.prateek.airquality.model.interfaces

interface IBaseModel {
    fun getType(): Int

    fun getCityText(): String

    fun getAQIText(): String

    companion object {
        const val TYPE_HEADER: Int = 11
        const val TYPE_ITEM: Int = 12
    }
}