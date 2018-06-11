
package com.getwala.amplitude;

import android.app.Activity;
import android.util.Log;

import com.amplitude.api.Amplitude;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import org.json.JSONException;
import org.json.JSONObject;

public class ReactNativeAmplitudeModule extends ReactContextBaseJavaModule {

    private static String TAG = "ReactNativeAmplitude";

    private String apiKey;
    private Boolean initialised = false;

    ReactNativeAmplitudeModule(ReactApplicationContext reactContext, String apiKey) {
        super(reactContext);
        this.apiKey = apiKey;

        this.initialize();
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initialize() {
        if (!this.initialised) {
            try {
                Activity activity = getCurrentActivity();

                if (activity != null) {
                    Amplitude.getInstance().initialize(activity, this.apiKey).enableForegroundTracking(activity.getApplication());
                    this.initialised = true;
                } else {
                    throw new ActivityNullException("Activity is null");
                }
            } catch (ActivityNullException e) {
                Log.e(TAG, "Activity null: " + e.getLocalizedMessage(), e);
            }
        }
    }

    @ReactMethod
    public void logEvent(String identifier, ReadableMap properties) {
        try {
            JSONObject jProperties = convertReadableToJsonObject(properties);
            Amplitude.getInstance().logEvent(identifier, jProperties);
        } catch (JSONException e) {
            Log.e(TAG, "logEvent: " + e.getLocalizedMessage(), e);
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
            Log.e(TAG, "setUserProperties: " + e.getLocalizedMessage(), e);
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
    }

    private static JSONObject convertReadableToJsonObject(ReadableMap map) throws JSONException {
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

    private class ActivityNullException extends Exception {

        private ActivityNullException(String message) {
            super(message);
        }
    }
}



