package com.codingwords.sobrien.gissues;

import android.app.Application;

import com.codingwords.sobrien.gissues.di.AppComp;
import com.codingwords.sobrien.gissues.di.DaggerAppComp;

/**
 * Created by Administrator on 2/19/2018.
 */
public class GissuesApp extends Application {

 AppComp mAppComp;

 @Override
 public void onCreate() {
        super.onCreate();
        mAppComp = DaggerAppComp.builder().build();
 }

  public AppComp getAppComp() {
     return mAppComp;
  }
}
