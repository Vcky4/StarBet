package com.starbettipz.di

import com.starbettipz.datastore.Settings
import com.starbettipz.ui.screens.chat.ChatViewModel
import com.starbettipz.ui.screens.home.HomeViewModel
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

    single {
        Settings(androidApplication())
    }

//    //database
//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            DB::class.java,
//            "StarBetDB"
//        ).build()
//    }

    //Doa
//    single { get<DB>().tipDao() }

    //Repositories
//    single {
//        TipsRepository(get())
//    }
}