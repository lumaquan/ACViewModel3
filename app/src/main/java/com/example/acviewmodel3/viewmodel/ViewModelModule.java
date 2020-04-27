package com.example.acviewmodel3.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.acviewmodel3.home.ListViewModel;
import com.example.acviewmodel3.home.SelectedRepoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedRepoViewModel.class)
    abstract ViewModel bindSelectRepoViewModel(SelectedRepoViewModel viewModel);

}
