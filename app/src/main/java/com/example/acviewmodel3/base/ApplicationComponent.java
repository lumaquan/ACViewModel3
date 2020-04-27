package com.example.acviewmodel3.base;


import com.example.acviewmodel3.details.DetailsFragment;
import com.example.acviewmodel3.home.ListFragment;
import com.example.acviewmodel3.networking.NetworkModule;
import com.example.acviewmodel3.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, ViewModelModule.class})
public interface ApplicationComponent {

    void inject(ListFragment listFragment);

    void inject(DetailsFragment detailsFragment);
}
