
package com.sstatsuya.internetspeed;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Callback;


import javax.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class RNInternetSpeedModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private final ReactApplicationContext reactContext;
  private InternetSpeedTimer mInternetSpeedTimer;

  public RNInternetSpeedModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNInternetSpeed";
  }

  @Override
  public void onHostPause() {
      Log.e(getName(), "onHostPause");
  }

  @Override
  public void onHostResume() {
    Log.e(getName(), "onHostResume");
  }

  @Override
  public void onHostDestroy() {
    Log.e(getName(), "onHostDestroy");
  }

  @ReactMethod
  public void startListenInternetSpeed() {
    //创建NetSpeedTimer实例
    mInternetSpeedTimer = new InternetSpeedTimer(reactContext, new InternetSpeed(), new mHandler(reactContext)).setDelayTime(1000).setPeriodTime(2000);
    //在想要开始执行的地方调用该段代码
    mInternetSpeedTimer.startSpeedTimer();
  }

  @ReactMethod
  public void stopListenInternetSpeed() {
    mInternetSpeedTimer.stopSpeedTimer();
  }

  // 处理定时器消息
  private static class mHandler extends Handler {
    // 保存上下文
    private ReactContext reactContext;

    public mHandler(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == InternetSpeedTimer.NET_SPEED_TIMER_DEFAULT){
        InternetSpeed.NetSpeedResult res = (InternetSpeed.NetSpeedResult) msg.obj;

        String downLoadSpeed = res.getDownLoadSpeed();
        String downLoadSpeedUri = res.getDownLoadSpeedUid();
        String upLoadSpeed = res.getUpLoadSpeed();
        String upLoadSpeedUri = res.getUpLoadSpeedUid();

        //打印你所需要的网速值，单位默认为kb/s
        WritableMap params = Arguments.createMap();
        params.putString("downLoadSpeed", downLoadSpeed);
        params.putString("downLoadSpeedCurrent", downLoadSpeedUri);
        params.putString("upLoadSpeed", upLoadSpeed);
        params.putString("upLoadSpeedCurrent", upLoadSpeedUri);

        sendInternetSpeed(reactContext, "onSpeedUpdate", params);
//        Log.i(TAG, "current net speed  = " + downLoadSpeed);
      }
    }

    private void sendInternetSpeed(ReactContext reactContext,
                                  String eventName,
                                  @Nullable WritableMap params) {
      reactContext
              .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
              .emit(eventName, params);
    }
  }
}