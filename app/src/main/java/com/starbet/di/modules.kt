package com.starbet.di

import com.starbet.ui.screens.chat.ChatViewModel
import com.starbet.ui.screens.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    viewModel {
        HomeViewModel()
    }

    viewModel {
        ChatViewModel(androidApplication())
    }

//    single {
//        Settings(androidApplication())
//    }

//    //database
//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            DB::class.java,
//            "LSBT"
//        ).build()
//    }

    //Doa
//    single { get<DB>().tipDao() }

    //Repositories
//    single {
//        TipsRepository(get())
//    }
}