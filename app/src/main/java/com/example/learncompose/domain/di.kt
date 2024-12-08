package com.example.learncompose.domain

import com.example.learncompose.data.LinesRepositoryImpl
import com.example.learncompose.data.PostRepositoryImpl
import com.example.learncompose.data.SharedPrefRepositoryImpl
import com.example.learncompose.data.UserRepositoryImpl
import com.example.learncompose.ui.login.LoginViewModel
import com.example.learncompose.ui.metro.MetroViewModel
import com.example.learncompose.ui.signup.SignupViewModel
import com.example.learncompose.ui.station.StationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<LinesRepository> { LinesRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
    single<PostRepository> { PostRepositoryImpl() }
    single<SharedPrefRepository> { SharedPrefRepositoryImpl(context = androidContext()) }
}

val domainModule = module {
    factory<LoadLinesUseCase> { LoadLinesUseCase(get()) }
    factory<LoginUseCase> { LoginUseCase(get(), get()) }
    factory<SignupUseCase> { SignupUseCase(get()) }
    factory<LoadPostsUseCase> { LoadPostsUseCase(get()) }
    factory<CheckPastLoginUseCase> { CheckPastLoginUseCase(get()) }
    factory<LogoutUseCase> { LogoutUseCase(get()) }
}

val appModule = module {
    viewModel<MetroViewModel> { MetroViewModel(get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get(), get()) }
    viewModel<SignupViewModel> { SignupViewModel(get()) }
    viewModel<StationViewModel> { StationViewModel(get()) }
}
