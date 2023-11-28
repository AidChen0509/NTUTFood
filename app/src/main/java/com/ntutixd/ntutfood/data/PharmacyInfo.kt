package com.ntutixd.ntutfood.data

data class PharmacyInfo(
    val features: MutableList<Feature>,
    val type: String
)

data class Feature(
    val name: String,
    val loc: String,
    val features: String,
    val gettime: String,
    val price: String,
    val rank: String,
    val imgurl: String,
    val time: String
)
data class notify_img(
    val url: String
        )
data class respayment(
    val cashpay: Boolean,
    val creditpay: Boolean,
    val easycardpay: Boolean,
    val fiveticketpay: Boolean,
    val phonepay: Boolean,
    val resdetailimg: String
)

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

data class Properties(
    val time: String,
    val features: String,
    val loc: String,
    val name: String
)