package com.example.categoryresponse.configs

class EndpointsConfig {
    companion object{
        private const val URL_CATEGORY = "category"
        private const val URL_SUBCATEGORY = "subcategory"
        private const val URL_PRODUCT = "products"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"
        private const val URL_USER = "users"
        private const val URL_ADDRESS = "address"
        private const val URL_ORDERS = "orders"

        fun getCategoryUrl(): String{
            return BaseConfig.URL + URL_CATEGORY
        }

        fun getSubCategoryUrlByCatId(catId: Int): String{
            return "${BaseConfig.URL + URL_SUBCATEGORY}/${catId}"
        }

        fun getProductUrl(): String{
            return BaseConfig.URL + URL_PRODUCT
        }

        fun getImageUrl(imageName: String): String{
            return BaseConfig.IMAGE_URL+imageName
        }

        fun getRegisterUrl(): String{
            return BaseConfig.URL + URL_REGISTER
        }

        fun getLoginUrl(): String{
            return BaseConfig.URL + URL_LOGIN
        }

        fun getUserUrl(userId: String?): String{
            return "${BaseConfig.URL + URL_USER}/${userId}"
        }

        fun getUserAddressUrl(userId:String?): String{
            return "${BaseConfig.URL + URL_ADDRESS}/${userId}"
        }

        fun getAddressRegisterUrl(): String{
            return BaseConfig.URL + URL_ADDRESS
        }

        fun getAddressDeleteUrl(addressId: String):String{
            return "${BaseConfig.URL + URL_ADDRESS}/${addressId}"
        }

        fun getAddressUpdateUrl(addressId: String?): String{
            return "${BaseConfig.URL + URL_ADDRESS}/${addressId}"
        }

        fun getOrdersRegisterUrl():String{
            return BaseConfig.URL + URL_ORDERS
        }

        fun getOrderHistoryUrl(userId: String?): String{
            return "${BaseConfig.URL + URL_ORDERS}/${userId}"
        }
    }
}