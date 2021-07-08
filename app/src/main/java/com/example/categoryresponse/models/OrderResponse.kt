package com.example.categoryresponse.models

import java.io.Serializable

data class OrderResponse(
    val count: Int,
    val `data`: List<OrderHistory>,
    val error: Boolean
)

data class OrderHistory(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<OrderProduct>,
    val shippingAddress: ShippingAddress,
    val user: OrderUser,
    val userId: String
): Serializable

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Float,
    val orderAmount: Float,
    val ourPrice: Float,
    val totalAmount: Float
): Serializable

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
): Serializable

data class OrderProduct(
    val _id: String,
    val image: String,
    val mrp: Float,
    val price: Float,
    val quantity: Float,
    val productName: String
): Serializable

data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String
): Serializable

data class OrderUser(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
): Serializable