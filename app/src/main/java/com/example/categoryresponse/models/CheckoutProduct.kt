package com.example.categoryresponse.models

data class CheckoutProduct(
    var _id : String,
    var productName: String,
    var image: String,
    var price: Float,
    var mrp: Float,
    var quantity: Int
)
