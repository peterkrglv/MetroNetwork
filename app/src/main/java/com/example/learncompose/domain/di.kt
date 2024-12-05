package com.example.learncompose.domain

import com.example.learncompose.data.LinesRepositoryImpl
import com.example.learncompose.data.UserRepositoryImpl
import com.example.learncompose.ui.login.LoginViewModel
import com.example.learncompose.ui.metro.MetroViewModel
import com.example.learncompose.ui.signup.SignupViewModel
import com.example.learncompose.ui.station.StationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<LinesRepository> { LinesRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}

val domainModule = module {
    factory<LoadLinesUseCase> { LoadLinesUseCase(get()) }
    factory<LoginUseCase> { LoginUseCase(get()) }
    factory<SignupUseCase> { SignupUseCase(get()) }
}

val appModule = module {
    viewModel<MetroViewModel> { MetroViewModel(get()) }
    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<SignupViewModel> { SignupViewModel(get()) }
    viewModel<StationViewModel> { StationViewModel() }
}
