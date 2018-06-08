
package com.getwala.amplitude;

import android.app.Application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.*;
import com.facebook.react.BuildConfig;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import com.getwala.amplitude.ReactNativeAmplitudeModule;

public class ReactNativeAmplitudePackage implements ReactPackage {

  private String apiKey;

  public ReactNativeAmplitudePackage(String apiKey) {
    this.apiKey = apiKey;
  }


  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    return Arrays.<NativeModule>asList(new ReactNativeAmplitudeModule(reactContext,this.apiKey));
  }

  public List<Class<? extends JavaScriptModule>> createJSModules() {
    return Collections.emptyList();
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }
}


