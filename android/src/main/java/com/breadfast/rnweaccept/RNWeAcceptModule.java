
package com.breadfast.reactnative;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;



import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReadableMap;




import com.paymob.acceptsdk.IntentConstants;
import com.paymob.acceptsdk.PayActivity;
import com.paymob.acceptsdk.PayActivityIntentKeys;
import com.paymob.acceptsdk.PayResponseKeys;
import com.paymob.acceptsdk.SaveCardResponseKeys;
import com.paymob.acceptsdk.ToastMaker;

import java.util.HashMap;

public class RNWeAcceptModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  static final int ACCEPT_PAYMENT_REQUEST = 10;

    // Replace this with your actual payment key
  private String paymentKey = "will be replaced with init function";
  private String token= "will be replaced with startPay functions";
  private String Verification= "will be replaced with startPay functions";
  private String cardNumber= "will be replaced with startPay functions";
  private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";
  private Callback mSuccessCallback;
  private Callback mErrorCallback;
  private ReadableMap mParams;


  
  private void startPayActivityNoToken(Activity currentActivity, Boolean showSaveCard) {
    Intent pay_intent = new Intent(currentActivity, PayActivity.class);

    putNormalExtras(pay_intent);
    pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, true);
    pay_intent.putExtra(PayActivityIntentKeys.SHOW_ALERTS, showSaveCard);
    pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, showSaveCard);
    // pay_intent.putExtra(PayActivityIntentKeys.BUTTON_COLOR, 0x8033B5E5);

    currentActivity.startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
}

private void startPayActivityToken(Activity currentActivity, String token, String maskedPanNumber) {
    Intent pay_intent = new Intent(currentActivity, PayActivity.class);

    putNormalExtras(pay_intent);
    // replace this with your actual card token
    pay_intent.putExtra(PayActivityIntentKeys.TOKEN, token);
    pay_intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, maskedPanNumber);
    pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);
    pay_intent.putExtra(PayActivityIntentKeys.SHOW_ALERTS, true);
    pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false);

    currentActivity.startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
}

private void putNormalExtras(Intent intent) {
    // Pass the correct values for the billing data keys
    intent.putExtra(PayActivityIntentKeys.FIRST_NAME, "first_name");
    intent.putExtra(PayActivityIntentKeys.LAST_NAME, "last_name");
    intent.putExtra(PayActivityIntentKeys.BUILDING, "1");
    intent.putExtra(PayActivityIntentKeys.FLOOR, "1");
    intent.putExtra(PayActivityIntentKeys.APARTMENT, "1");
    intent.putExtra(PayActivityIntentKeys.CITY, "cairo");
    intent.putExtra(PayActivityIntentKeys.STATE, "new_cairo");
    intent.putExtra(PayActivityIntentKeys.COUNTRY, "egypt");
    intent.putExtra(PayActivityIntentKeys.EMAIL, "email@gmail.com");
    intent.putExtra(PayActivityIntentKeys.PHONE_NUMBER, "2345678");
    intent.putExtra(PayActivityIntentKeys.POSTAL_CODE, "3456");

    intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey);
}

private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
      super.onActivityResult(activity, requestCode, resultCode, data);

      Bundle extras = data.getExtras();

      if (requestCode == ACCEPT_PAYMENT_REQUEST) {
        if (resultCode == IntentConstants.USER_CANCELED) {
          // User canceled and did no payment request was fired
          ToastMaker.displayShortToast(activity, "User canceled!!");
        } else if (resultCode == IntentConstants.MISSING_ARGUMENT) {
          // You forgot to pass an important key-value pair in the intent's extras
          ToastMaker.displayShortToast(activity, "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
        } else if (resultCode == IntentConstants.TRANSACTION_ERROR) {
          // An error occurred while handling an API's response
          ToastMaker.displayShortToast(activity, "Reason == " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));
        } else if (resultCode == IntentConstants.TRANSACTION_REJECTED) {
          // User attempted to pay but their transaction was rejected
          // Use the static keys declared in PayResponseKeys to extract the fields you want
          ToastMaker.displayShortToast(activity, extras.getString(PayResponseKeys.DATA_MESSAGE));
        } else if (resultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
          // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
          ToastMaker.displayShortToast(activity, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
          // User finished their payment successfully
          // Use the static keys declared in PayResponseKeys to extract the fields you want
          ToastMaker.displayShortToast(activity, extras.getString(PayResponseKeys.DATA_MESSAGE));
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
          // User finished their payment successfully. An error occured while reading the returned JSON.
          ToastMaker.displayShortToast(activity, "TRANSACTION_SUCCESSFUL - Parsing Issue");
          // ToastMaker.displayShortToast(activity, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
        } else if (resultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
        // User finished their payment successfully and card was saved.
        // Use the static keys declared in PayResponseKeys to extract the fields you want
        // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
          ToastMaker.displayShortToast(activity, "Token == " + extras.getString(SaveCardResponseKeys.TOKEN));
        } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION) {
          ToastMaker.displayShortToast(activity, "User canceled 3-d secure verification!!");
          // Note that a payment process was attempted. You can extract the original returned values
          // Use the static keys declared in PayResponseKeys to extract the fields you want
          ToastMaker.displayShortToast(activity, extras.getString(PayResponseKeys.PENDING));
        } else if (resultCode == IntentConstants.USER_CANCELED_3D_SECURE_VERIFICATION_PARSING_ISSUE) {
          ToastMaker.displayShortToast(activity, "User canceled 3-d scure verification - Parsing Issue!!");
          // Note that a payment process was attempted.
          // User finished their payment successfully. An error occured while reading the returned JSON.
          ToastMaker.displayShortToast(activity, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
        }
      }

      // if (requestCode == ACCEPT_PAYMENT_REQUEST) {
      //     final int acceptResultCode = extras.getInt(IntentConstants.ACCEPT_RESULT_CODE, 0);

      //     if (acceptResultCode == 0) {
      //         ToastMaker.displayLongToast(activity, "No ACCEPT_RESULT_CODE returned!!");
      //     }
      //     else if (acceptResultCode == IntentConstants.USER_CANCELED) {
      //         // User canceled and did no payment request was fired
      //         ToastMaker.displayLongToast(activity, "User canceled!!");
      //     } else if (acceptResultCode == IntentConstants.MISSING_ARGUMENT) {
      //         // You forgot to pass an important key-value pair in the intent's extras
      //         ToastMaker.displayLongToast(activity, "Missing Argument == " + extras.getString(IntentConstants.MISSING_ARGUMENT_VALUE));
      //     } else if (acceptResultCode == IntentConstants.TRANSACTION_ERROR) {
      //         // An error occurred while handling an API's response
      //         ToastMaker.displayLongToast(activity, "Reason == " + extras.getString(IntentConstants.TRANSACTION_ERROR_REASON));
      //     } else if (acceptResultCode == IntentConstants.TRANSACTION_REJECTED) {
      //         // User attempted to pay but their transaction was rejected

      //         // Use the static keys declared in PayResponseKeys to extract the fields you want
      //         ToastMaker.displayLongToast(activity, extras.getString(PayResponseKeys.DATA_MESSAGE));
      //     } else if (acceptResultCode == IntentConstants.TRANSACTION_REJECTED_PARSING_ISSUE) {
      //         // User attempted to pay but their transaction was rejected. An error occured while reading the returned JSON
      //         ToastMaker.displayLongToast(activity, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
      //     } else if (acceptResultCode == IntentConstants.TRANSACTION_SUCCESSFUL) {
      //         // User finished their payment successfully

      //         // Use the static keys declared in PayResponseKeys to extract the fields you want
      //         ToastMaker.displayLongToast(activity, extras.getString(PayResponseKeys.DATA_MESSAGE));
      //     } else if (acceptResultCode == IntentConstants.TRANSACTION_SUCCESSFUL_PARSING_ISSUE) {
      //         // User finished their payment successfully. An error occured while reading the returned JSON.
      //         ToastMaker.displayLongToast(activity, extras.getString(IntentConstants.RAW_PAY_RESPONSE));
      //     } else if (acceptResultCode == IntentConstants.TRANSACTION_SUCCESSFUL_CARD_SAVED) {
      //         // User finished their payment successfully and card was saved.

      //         // Use the static keys declared in PayResponseKeys to extract the fields you want
      //         // Use the static keys declared in SaveCardResponseKeys to extract the fields you want
      //         ToastMaker.displayLongToast(activity, "Token == " + extras.getString(SaveCardResponseKeys.TOKEN));
      //     }
      // }
  }
};

  public RNWeAcceptModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(mActivityEventListener);
    this.reactContext = reactContext;
  }

  
  //********************* React Methods ******************************/

  @ReactMethod
  public void initWeAccept(ReadableMap params) {
    Activity currentActivity = getCurrentActivity();
    mParams = params;

    if (currentActivity == null) {
      mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
      return;
    }

    HashMap paramsMap = params.toHashMap();
    this.paymentKey = (String) paramsMap.get("paymentKey");
  }


  @ReactMethod
  public void payWithToken(ReadableMap params, Callback successCallback, Callback errorCallback) {
    Activity currentActivity = getCurrentActivity();
    mSuccessCallback = successCallback;
    mErrorCallback = errorCallback;
    mParams = params;

    if (currentActivity == null) {
      mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
      return;
    }

    HashMap paramsMap = params.toHashMap();

    final String token = (String) paramsMap.get("token");
    final String maskedPanNumber = (String) paramsMap.get("maskedPanNumber");

    this.startPayActivityToken(currentActivity, token, maskedPanNumber);
  }
  
  // @ReactMethod
  // public void startSDK(ReadableMap params, Callback successCallback, Callback errorCallback) {
  //   Activity currentActivity = getCurrentActivity();
  //   mSuccessCallback = successCallback;
  //   mErrorCallback = errorCallback;
  //   mParams = params;

  //   if (currentActivity == null) {
  //     mErrorCallback.invoke(E_ACTIVITY_DOES_NOT_EXIST);
  //     return;
  //   }

  //   HashMap paramsMap = params.toHashMap();

  //   final String paymentKey = (String) paramsMap.get("paymentKey");

  //   final String token = (String) paramsMap.get("token");
  //   final String Verification = (String) paramsMap.get("verification");
  //   final String cardNumber = (String) paramsMap.get("cardNumber");




  //   // If you have a card token you will need to send the following information
  //   //   ● Payment key
  //   //   ● 3D Secure
  //   //   ● Card Token
  //   //   ● Masked pan number
  //   //   ● The default for whether to save a card or not
  //   //   ● Whether or not to show the save card option (if you choose to hide it, then saving the
  //   //   card or not will be the default you set in the line above)

  //   // if(token != ""){
  //   //   Intent pay_intent = new Intent(this, PayActivity.class);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE,
  //   //   verification);
  //   //   // replace this with your actual card token
  //   //   pay_intent.putExtra(PayActivityIntentKeys.TOKEN, token);

  //   //   pay_intent.putExtra(PayActivityIntentKeys.MASKED_PAN_NUMBER, cardNumber);

  //   //   pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, false);
  //   //   startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
  //   // } else{
  //   //   // If you do not have a card token then you will need to send the following info
  //   //   //   ● Payment Key
  //   //   //   ● 3D secure
  //   //   //   ● The default to whether to save a card or not
  //   //   //   ● Whether or not to show the save card option (if you choose to hide it, then saving the
  //   //   //   card or not will be the default you set in the line above)
  //   //   //   ● The theme color(so that you can set it the same as your app)


  //   //   Intent pay_intent = new Intent(this, PayActivity.class);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.PAYMENT_KEY, paymentKey);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.THREE_D_SECURE_ACTIVITY_TITLE,
  //   //   "Verification");
  //   //   pay_intent.putExtra(PayActivityIntentKeys.SAVE_CARD_DEFAULT, false);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.SHOW_SAVE_CARD, showSaveCard);
  //   //   pay_intent.putExtra(PayActivityIntentKeys.THEME_COLOR, 0x8033B5E5);
  //   //   startActivityForResult(pay_intent, ACCEPT_PAYMENT_REQUEST);
  //   // }

    



  //     // HashMap paramsMap = params.toHashMap();

  //     // if (this.isCustomFlow(paramsMap)) {
  //     //   Intent intent = new Intent(currentActivity, OnfidoCustomDocumentTypesActivity.class);
  //     //   ArrayList docTypes = (ArrayList) paramsMap.get("documentTypes");
  //     //   int[] documentTypes = new int[docTypes.size()];
  //     //   for(int i = 0; i < docTypes.size(); i++) {
  //     //     Double docTypeValue = (Double) docTypes.get(i);
  //     //     documentTypes[i] = docTypeValue.intValue();
  //     //   }
  //     //   intent.putExtra("documentTypes", documentTypes);
  //     //   currentActivity.startActivityForResult(intent, REQUEST_CODE_DOCUMENT_TYPE);
  //     // } else {
  //     //   OnfidoConfig onfidoConfig = this.getOnfidoConfig(paramsMap);
  //     //   client.startActivityForResult(currentActivity, REQUEST_CODE_ONFIDO, onfidoConfig);
  //     // }
  //   }



  @Override
  public String getName() {
    return "RNWeAccept";
  }
}