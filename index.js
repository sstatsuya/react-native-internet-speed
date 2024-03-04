import {
  NativeModules,
  DeviceEventEmitter,
  Platform,
  NativeEventEmitter,
} from "react-native";

const { RNInternetSpeed } = NativeModules;

let defualtCallback = null;
let eventEmitter = null;

class NetworkSpeed {
  static startListenNetworkSpeed(callback) {
    // Determine whether there is already monitoring
    if (!callback || typeof callback !== "function") {
      throw new Error("callback need a function");
    }
    if (eventEmitter && eventEmitter instanceof NativeEventEmitter) return;
    if (defualtCallback && typeof defualtCallback == "function") return;

    // Initialize listener
    eventEmitter = new NativeEventEmitter(RNInternetSpeed);

    // Initialize monitoring and call native code to start timer and monitoring
    RNInternetSpeed.startListenNetworkSpeed();
    defualtCallback = callback;
    eventEmitter.addListener("onSpeedUpdate", defualtCallback);
  }

  static stopListenNetworkSpeed() {
    if (!defualtCallback && typeof defualtCallback !== "function") return;
    if (eventEmitter && eventEmitter instanceof NativeEventEmitter) {
      RNInternetSpeed.stopListenNetworkSpeed();
      eventEmitter.removeListener("onSpeedUpdate", defualtCallback);
      defualtCallback = null;
      eventEmitter = null;
    }
  }
}

export default NetworkSpeed;
