package com.ishmit.aisleassignment.utils.ktx

object ValidateStrings {

    fun isValidPhoneNumber(countryCode: String, mobileNumber: String): Boolean {

        val countryCodeRegex = "^\\+[1-9]\\d{0,2}\$".toRegex()
        val mobileNumberRegex = "^[1-9]\\d{9}\$".toRegex()

        val isValidCountryCode = countryCode.matches(countryCodeRegex)
        val isValidMobileNumber = mobileNumber.matches(mobileNumberRegex)

        val isValidMobileNumberLength = mobileNumber.length == 10
        return isValidCountryCode && isValidMobileNumber && isValidMobileNumberLength

    }


}