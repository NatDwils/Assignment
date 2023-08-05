package com.ishmit.aisleassignment.ui.fragments.OtpScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ishmit.aisleassignment.databinding.FragmentOtpBinding
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.utils.gone
import com.ishmit.aisleassignment.utils.showToast
import com.ishmit.aisleassignment.utils.visible
import org.koin.android.ext.android.inject

class OtpFragment : Fragment() {

    private lateinit var binding : FragmentOtpBinding
    private val otpViewModel by inject<OtpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Extracting arguments from navigation
        val args = OtpFragmentArgs.fromBundle(requireArguments())
        val countryCode = args.countryCode
        val mobileNumber = args.mobileNumber
        val number = countryCode + mobileNumber

        // Setting country code and mobile number
        binding.countryCode.text = countryCode
        binding.number.text = mobileNumber

        // Edit button click listener to navigate back to PhoneNumberFragment
        binding.edit.setOnClickListener {
            val action = OtpFragmentDirections.actionOtpFragmentToPhoneNumberFragment()
            findNavController().navigate(action)
        }

        // Continue button click listener to verify OTP
        binding.continueButton.setOnClickListener {
            val otp = binding.otp.text.toString()
            otpViewModel.verifyOtp(number, otp)
        }

        // Resend button click listener to request OTP resend
        binding.resendButton.setOnClickListener {
            otpViewModel.resendOtp()
        }

        // Observing the OTP timer
        otpViewModel.timer.observe(viewLifecycleOwner) { timerValue ->
            binding.timer.text = timerValue
            if (timerValue == "00:00") {
                binding.timer.gone()
                binding.resendButton.visible()
            } else {
                binding.timer.visible()
                binding.resendButton.gone()
            }
        }

        // Observing the OTP verification response
        otpViewModel.otpResponse.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ResponseRequest.Loading -> {
                    binding.progressBar.visible()
                }
                is ResponseRequest.Success -> {
                    binding.progressBar.gone()
                    val token = uiState.data.token
                    val action = OtpFragmentDirections.actionOtpFragmentToNotesFragment(
                        token = token
                    )
                    findNavController().navigate(action)
                }
                is ResponseRequest.Failure -> {
                    showToast("Please enter correct otp")
                }
            }
        }
    }
}
