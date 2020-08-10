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
4. add repository as below
   ```
   allprojects {
   	repositories {
   		google()
   		...
   		maven { url "https://dl.bintray.com/paymobsolutions/paymob_accept_sdk" }
   	}
   }
   ```

## Usage

Initialize Payment key

```javascript
import WeAccept from "react-native-we-accept";

WeAccept.initWeAccept({
  paymentKey: "put your payment key here",
});
```

Payment with Token

```javascript
import WeAccept from "react-native-we-accept";

WeAccept.payWithToken({
  token: "12345",
  maskedPanNumber: "XXXXXXXXXXXXXX1234",
  firstName: "first_name",
  lastName: "last_name",
  building: "building",
  floor: "floor",
  apartment: "apartment",
  city: "city",
  state: "state",
  country: "country",
  email: "email",
  phoneNumber: "phoneNumber",
  postalCode: "postalCode",
});
```

| Prop                  | Description | Type   | Default      |
| --------------------- | ----------- | ------ | ------------ |
| **`token`**           | TODO        | String | **Required** |
| **`maskedPanNumber`** | TODO        | String | **Required** |
| **`firstName`**       | TODO        | String | **Optional** |
| **`lastName`**        | TODO        | String | **Optional** |
| **`building`**        | TODO        | String | **Optional** |
| **`floor`**           | TODO        | String | **Optional** |
| **`apartment`**       | TODO        | String | **Optional** |
| **`city`**            | TODO        | String | **Optional** |
| **`state`**           | TODO        | String | **Optional** |
| **`country`**         | TODO        | String | **Optional** |
| **`email`**           | TODO        | String | **Optional** |
| **`phoneNumber`**     | TODO        | String | **Optional** |
| **`postalCode`**      | TODO        | String | **Optional** |

Payment with No Token

```javascript
import WeAccept from "react-native-we-accept";

WeAccept.payWithNoToken({
  showSaveCard: false,
  firstName: "first_name",
  lastName: "last_name",
  building: "building",
  floor: "floor",
  apartment: "apartment",
  city: "city",
  state: "state",
  country: "country",
  email: "email",
  phoneNumber: "phoneNumber",
  postalCode: "postalCode",
});
```

| Prop                | Description | Type    | Default      |
| ------------------- | ----------- | ------- | ------------ |
| **`showSavedCard`** | TODO        | Boolean | **Required** |
| **`firstName`**     | TODO        | String  | **Optional** |
| **`lastName`**      | TODO        | String  | **Optional** |
| **`building`**      | TODO        | String  | **Optional** |
| **`floor`**         | TODO        | String  | **Optional** |
| **`apartment`**     | TODO        | String  | **Optional** |
| **`city`**          | TODO        | String  | **Optional** |
| **`state`**         | TODO        | String  | **Optional** |
| **`country`**       | TODO        | String  | **Optional** |
| **`email`**         | TODO        | String  | **Optional** |
| **`phoneNumber`**   | TODO        | String  | **Optional** |
| **`postalCode`**    | TODO        | String  | **Optional** |
