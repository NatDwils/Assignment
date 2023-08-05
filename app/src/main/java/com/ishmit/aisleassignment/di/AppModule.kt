package com.ishmit.aisleassignment.di

import com.ishmit.aisleassignment.data.remote.network.RetrofitHelper
import com.ishmit.aisleassignment.data.remote.repository.UserRepository
import com.ishmit.aisleassignment.ui.fragments.notesScreen.NotesViewModel
import com.ishmit.aisleassignment.ui.fragments.OtpScreen.OtpViewModel
import com.ishmit.aisleassignment.ui.fragments.phoneNumberScreen.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Create a singleton instance of RetrofitHelper
    single { RetrofitHelper }

    // Create a singleton instance of ApiService using RetrofitHelper
    single { get<RetrofitHelper>().create() }

    // Create a singleton instance of UserRepository with ApiService dependency
    single { UserRepository(get()) }

    // Create a ViewModel instance for PhoneViewModel with UserRepository dependency
    viewModel { PhoneViewModel(get()) }

    // Create a ViewModel instance for OtpViewModel with UserRepository dependency
    viewModel { OtpViewModel(get()) }

    // Create a ViewModel instance for NotesViewModel with UserRepository dependency
    viewModel { NotesViewModel(get()) }
}
