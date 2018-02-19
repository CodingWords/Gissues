package com.codingwords.sobrien.gissues.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

/**
 * Created by Administrator on 2/19/2018.
 */

public class VMFactory implements ViewModelProvider.Factory {
    private GissuesViewModel viewModel;

    @Inject
    public VMFactory(GissuesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GissuesViewModel.class)) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}


