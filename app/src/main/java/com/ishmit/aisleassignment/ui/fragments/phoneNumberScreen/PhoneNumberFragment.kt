package com.ishmit.aisleassignment.ui.fragments.phoneNumberScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ishmit.aisleassignment.databinding.FragmentPhoneNumberBinding
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.utils.gone
import com.ishmit.aisleassignment.utils.visible
import com.ishmit.aisleassignment.utils.showToast
import com.ishmit.aisleassignment.utils.ktx.ValidateStrings.isValidPhoneNumber
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.inject

class PhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentPhoneNumberBinding
    private val phoneViewModel by inject<PhoneViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhoneNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle the continue button click
        binding.continueButton.setOnClickListener {
            val countryCode = binding.countryCode.text.toString()
            val mobileNumber = binding.number.text.toString()
            val number = countryCode + mobileNumber

            // Validate phone number format
            if (isValidPhoneNumber(countryCode, mobileNumber)) {
                phoneViewModel.phoneNumberLogin(number)
            } else {
                showToast("Please check your number and try again")
            }
        }

        // Observe the phone number login response
        phoneViewModel.phoneNumberResponse.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ResponseRequest.Loading -> {
                    binding.progressBar.visible()
                    binding.continueButton.gone()
                }
                is ResponseRequest.Success -> {
                    binding.progressBar.gone()
                    binding.continueButton.visible()
                    if (uiState.data.status) {
                        // Navigate to OTP fragment with the entered phone number
                        val action = PhoneNumberFragmentDirections.actionPhoneFragmentToOtpFragment(
                            countryCode = binding.countryCode.text.toString(),
                            mobileNumber = binding.number.text.toString()
                        )
                        findNavController().navigate(action)
                    } else {
                        showToast("Phone number status false. Please try with a different number.")
                    }
                }
                is ResponseRequest.Failure -> {
                    binding.progressBar.gone()
                    showToast(uiState.error)
                }
                else -> {} // Handle other cases if needed
            }
        }
    }
}
