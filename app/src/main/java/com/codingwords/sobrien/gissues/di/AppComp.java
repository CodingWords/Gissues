package com.codingwords.sobrien.gissues.di;

import com.codingwords.sobrien.gissues.MainActivity;

import javax.inject.Singleton;
import dagger.Component;
/**
 * Created by Administrator on 2/19/2018.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComp {
    void inject(MainActivity mainActivity);
}
