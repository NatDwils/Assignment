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



        binding.btnContinue.setOnClickListener {
            val countryCode = binding.etCountryCode.text.toString()
            val mobileNumber = binding.etPhoneNumber.text.toString()
            val number = countryCode + mobileNumber

            if (isValidPhoneNumber(countryCode, mobileNumber)) {
                phoneViewModel.phoneNumberLogin(number)
            } else {
                showToast("Please check your number and try again")
            }
        }

        phoneViewModel.phoneNumberResponse.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ResponseRequest.Loading -> {
                    binding.progressBar.visible()
                    binding.btnContinue.gone()
                }
                is ResponseRequest.Success -> {
                    binding.progressBar.gone()
                    binding.btnContinue.visible()
                    if (uiState.data.status) {
                        val action =
                            PhoneNumberFragmentDirections.actionPhoneFragmentToOtpFragment(
                                countryCode = binding.etCountryCode.text.toString(),
                                mobileNumber = binding.etPhoneNumber.text.toString()
                            )

                        findNavController().navigate(action)
                    } else {
                        showToast("Phone number status false. Please try with different number.")
                    }
                }
                is ResponseRequest.Failure -> {
                    binding.progressBar.gone()
                    showToast(uiState.error)
                }
                else -> {}
            }
        }
    }
}
