package com.example.hearthelper.module

import com.example.hearthelper.ParameterViewModel
import com.example.hearthelper.ResultViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module



val viewModelModule = module {

        viewModel { ParameterViewModel()}
        viewModel { ResultViewModel() }

}
