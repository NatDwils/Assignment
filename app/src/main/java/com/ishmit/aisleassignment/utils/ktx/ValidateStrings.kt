package com.ishmit.aisleassignment.utils.ktx

object ValidateStrings {

    // Function to validate a phone number with country code
    fun isValidPhoneNumber(countryCode: String, mobileNumber: String): Boolean {
        // Regex pattern for valid country code
        val countryCodeRegex = "^\\+[1-9]\\d{0,2}\$".toRegex()
        // Regex pattern for valid mobile number (without country code)
        val mobileNumberRegex = "^[1-9]\\d{9}\$".toRegex()
        // Check if the provided country code matches the regex pattern
        val isValidCountryCode = countryCode.matches(countryCodeRegex)
        // Check if the provided mobile number matches the regex pattern
        val isValidMobileNumber = mobileNumber.matches(mobileNumberRegex)
        // Check if the length of the mobile number is valid (10 digits)
        val isValidMobileNumberLength = mobileNumber.length == 10
        // Return true if all validation conditions are met, otherwise false
        return isValidCountryCode && isValidMobileNumber && isValidMobileNumberLength
    }
}
