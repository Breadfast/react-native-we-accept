
# react-native-we-accept

## Getting started

`$ npm install react-native-we-accept --save`

### Mostly automatic installation

`$ react-native link react-native-we-accept`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-we-accept` and add `RNWeAccept.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNWeAccept.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.breadfast.reactnative.RNWeAcceptPackage;` to the imports at the top of the file
  - Add `new RNWeAcceptPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-we-accept'
  	project(':react-native-we-accept').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-we-accept/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-we-accept')
  	```


## Usage
```javascript
import RNWeAccept from 'react-native-we-accept';

// TODO: What to do with the module?
RNWeAccept;
```
  