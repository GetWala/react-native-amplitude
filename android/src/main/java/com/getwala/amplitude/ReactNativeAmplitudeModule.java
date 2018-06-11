
package com.getwala.amplitude;

import android.app.Activity;
import android.util.Log;

import com.amplitude.api.Amplitude;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import org.json.JSONException;
import org.json.JSONObject;


public class ReactNativeAmplitudeModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private static String TAG = "ReactNativeAmplitude";
    private String apiKey = null;


    public ReactNativeAmplitudeModule(ReactApplicationContext reactContext, String apiKey)
    {
        super(reactContext);
        this.apiKey=apiKey;

        Activity activity = getCurrentActivity();

        if (activity != null) {
            Amplitude.getInstance().initialize(activity, this.apiKey).enableForegroundTracking(activity.getApplication());
        }

    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initialize() {
        Activity activity = getCurrentActivity();

        if (activity != null) {
            Amplitude.getInstance().initialize(activity, this.apiKey).enableForegroundTracking(activity.getApplication());
        }
    }


    @ReactMethod
    public void logEvent(String identifier, ReadableMap properties) {
        try {
            JSONObject jProperties = convertReadableToJsonObject(properties);
            Amplitude.getInstance().logEvent(identifier, jProperties);
        } catch (JSONException e) {
            Log.e(TAG, "logEvent: " + e.getLocalizedMessage(), e);
            return;
        }
    }


    @ReactMethod
    public void setUserId(String id) {
        Amplitude.getInstance().setUserId(id);
    }

    @ReactMethod
    public void setUserProperties(ReadableMap properties) {
        try {
            JSONObject jProperties = convertReadableToJsonObject(properties);
            Amplitude.getInstance().setUserProperties(jProperties);
        } catch (JSONException e) {
            return;
        }
    }

    @ReactMethod
    public void setOptOut(Boolean optOut) {
        Amplitude.getInstance().setOptOut(optOut);
    }

    @ReactMethod
    public void clearUserProperties() {
        Amplitude.getInstance().clearUserProperties();
    }

    @ReactMethod
    public void regenerateDeviceId() {
        Amplitude.getInstance().regenerateDeviceId();

    public static JSONObject convertReadableToJsonObject(ReadableMap map) throws JSONException {
        JSONObject jsonObj = new JSONObject();
        ReadableMapKeySetIterator it = map.keySetIterator();
        while (it.hasNextKey()) {
            String key = it.nextKey();
            ReadableType type = map.getType(key);
            switch (type) {
                case Map:
                    jsonObj.put(key,
                            convertReadableToJsonObject(map.getMap(key)));
                    break;
                case String:
                    jsonObj.put(key, map.getString(key));
                    break;
                case Number:
                    jsonObj.put(key, map.getString(key));
                    break;
                case Boolean:
                    jsonObj.put(key, map.getString(key));
                    break;
                case Null:
                    jsonObj.put(key, map.getString(key));
                    break;
            }
        }
        return jsonObj;
    }


//    private void startSession()
//    {
//        if (mPinpointManager == null) {
//            if (initializing) return;
//            this.initializeWithSettings(new PinpointCallback<PinpointManager>() {
//                @Override
//                public void onComplete(PinpointManager manager) {
//                    manager.getSessionClient().startSession();
//                    sessionIsStarted = true;
//                }
//            });
//        } else {
//            if (sessionIsStarted) return;
//            mPinpointManager.getSessionClient().startSession();
//            sessionIsStarted = true;
//        }
//    }

    @Override
    public void onHostResume() {
        //startSession();
    }

    @Override
    public void onHostPause() {
//        if (mPinpointManager != null && sessionIsStarted) {
//            mPinpointManager.getSessionClient().stopSession();
//            mPinpointManager.getAnalyticsClient().submitEvents();
//            sessionIsStarted = false;
//        }
    }

    @Override
    public void onHostDestroy() {
//        if (mPinpointManager != null && sessionIsStarted) {
//            mPinpointManager.getSessionClient().stopSession();
//            mPinpointManager.getAnalyticsClient().submitEvents();
//            sessionIsStarted = false;
//        }
    }
}
