package com.codingwords.sobrien.gissues.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.codingwords.sobrien.gissues.api.GAPIService;
import com.codingwords.sobrien.gissues.repo.IssueRepo;
import com.codingwords.sobrien.gissues.repo.IssueRepository;
import com.codingwords.sobrien.gissues.vm.GissuesViewModel;
import com.codingwords.sobrien.gissues.vm.VMFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2/19/2018.
 */

@Module
public class AppModule {
    public static final String BASE_URL = "https://api.github.com/";

    @Provides
    @Singleton
    GAPIService provideGAPIService() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GAPIService.class);
    }

    @Provides
    ViewModelProvider.Factory provideGissuesViewModelFactory(
            VMFactory fact
    ) {
        return fact;
    }


    @Provides
    @Singleton
    IssueRepo provideIssueRepository(IssueRepository repository) {
        return repository;
    }

    @Provides
    ViewModel provideListIssuesViewModel(GissuesViewModel viewModel) {
        return viewModel;
    }


}
