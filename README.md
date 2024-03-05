# react-native-internet-speed

- This module can obtain the network speed of the current device.
- for IOS : currently only the network speed of the entire device can be obtained

## Getting started

`npm install react-native-internet-speed --save`

or

`yarn add react-native-internet-speed`

### Mostly automatic installation

`react-native link react-native-internet-speed`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-internet-speed` and add `RNInternetSpeed.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNInternetSpeed.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
   - Add `import com.sstatsuya.internetspeed.RNInternetSpeedPackage;` to the imports at the top of the file
   - Add `new RNNetworkSpeedPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-internet-speed'
   project(':react-native-internet-speed').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-internet-speed/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
       compile project(':react-native-internet-speed')
   ```

## Usage

```javascript
import InternetSpeed from "react-native-internet-speed";
// start
InternetSpeed.startListenNetworkSpeed(
  ({
    downLoadSpeed,
    downLoadSpeedCurrent,
    upLoadSpeed,
    upLoadSpeedCurrent,
  }) => {
    console.log(downLoadSpeed + "kb/s"); // download speed for the entire device The download speed of the entire device
    console.log(downLoadSpeedCurrent + "kb/s"); // download speed for the current app The download speed of the current app (currently can only be used on Android)
    console.log(upLoadSpeed + "kb/s"); // upload speed for the entire device The upload speed of the entire device
    console.log(upLoadSpeedCurrent + "kb/s"); // upload speed for the current app The upload speed of the current app (currently can only be used on Android)
  }
);
// stop
InternetSpeed.stopListenNetworkSpeed();
```
