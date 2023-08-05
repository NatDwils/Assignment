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
import dagger.hilt.android.AndroidEntryPoint
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

        val args = OtpFragmentArgs.fromBundle(requireArguments())
        val countryCode = args.countryCode
        val mobileNumber = args.mobileNumber
        val number = countryCode + mobileNumber

        binding.tvCountryCode.text = countryCode
        binding.tvMobileNumber.text = mobileNumber

        binding.btnEdit.setOnClickListener {
            val action = OtpFragmentDirections.actionOtpFragmentToPhoneNumberFragment()
            findNavController().navigate(action)
        }


        binding.btnContinue.setOnClickListener {
            val otp = binding.etOtp.text.toString()
            otpViewModel.verifyOtp( number,otp)
        }

        binding.btnResendOtp.setOnClickListener {
            otpViewModel.resendOtp()
        }

        otpViewModel.startOtpTimer(60)

        otpViewModel.timer.observe(viewLifecycleOwner) { timerValue ->
            binding.tvTimer.text = timerValue
            if (timerValue == "00:00") {
                binding.tvTimer.gone()
                binding.btnResendOtp.visible()
            } else {
                binding.tvTimer.visible()
                binding.btnResendOtp.gone()
            }
        }

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