package com.ishmit.aisleassignment.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

// Extension function to show a toast message from a Fragment
fun Fragment.showToast(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
}

// Extension function to set the visibility of a View to GONE
fun View.gone() {
    visibility = View.GONE
}

// Extension function to set the visibility of a View to VISIBLE
fun View.visible() {
    visibility = View.VISIBLE
}
