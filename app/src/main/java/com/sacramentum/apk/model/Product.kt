package com.sacramentum.apk.com.sacramentum.apk.model

data class Product(
    val uuid: Int,
    val name: String,
    val price: Double,
    val image: Int? = null
);