package com.ishmit.aisleassignment.di

import com.ishmit.aisleassignment.data.remote.network.RetrofitHelper
import com.ishmit.aisleassignment.data.remote.repository.UserRepository
import com.ishmit.aisleassignment.ui.fragments.notesScreen.NotesViewModel
import com.ishmit.aisleassignment.ui.fragments.OtpScreen.OtpViewModel
import com.ishmit.aisleassignment.ui.fragments.phoneNumberScreen.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitHelper }
    single { get<RetrofitHelper>().create() }

    single { UserRepository(get()) }

    viewModel { PhoneViewModel(get()) }
    viewModel { OtpViewModel(get()) }
    viewModel { NotesViewModel(get()) }
}
