package com.packt.rrafols.calculatortestexample;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BillingManager {
    private final static int BILLING_RESPONSE_RESULT_OK = 0;
    private final static int BILLING_VERSION = 3;

    private final static String DETAILS_LIST = "DETAILS_LIST";
    private final static String BUY_INTENT = "BUY_INTENT";
    private final static String RESPONSE_CODE = "RESPONSE_CODE";

    public static final String IAP_TYPE = "inapp";
    public static final String SUB_TYPE = "subs";

    private static final String TAG = BillingManager.class.getName();
    private IInAppBillingService billingService;
    private Activity activity;
    private String packageName;

    public void init(Activity activity) {
        this.activity = activity;

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        this.activity.bindService(serviceIntent, billingServiceConn, Context.BIND_AUTO_CREATE);
        this.packageName = this.activity.getPackageName();
    }

    public void shutdown() {
        if(billingService != null) {
            activity.unbindService(billingServiceConn);
        }
    }

    private final ServiceConnection billingServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Billing service connected");
            billingService = IInAppBillingService.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Billing service disconnected" + SUB_TYPE);
            billingService = null;
        }
    };

    public boolean isBillingSupported() {
        int billingSupported = -1;
        try {

            billingSupported = billingService.isBillingSupported(BILLING_VERSION, packageName,
                    IAP_TYPE);

        } catch(RemoteException e) {
            Log.e(TAG, "Error checking billing support", e);
        }

        return billingSupported == BILLING_RESPONSE_RESULT_OK;
    }

    public void consumePurchase(String purchaseToken) {
        int response = -1;
        try {
            response = billingService.consumePurchase(BILLING_VERSION, packageName, purchaseToken);
        } catch(RemoteException e) {
            Log.e(TAG, "Exception consuming purchase", e);
            return;
        }

        if(response != BILLING_RESPONSE_RESULT_OK) {
            Log.e(TAG, "Error consuming purchase " + getResponseDesc(response));
        }
    }

    public void listPurchasedItems() {
        String continuationToken = null;

        do {
            continuationToken = listPurchases(continuationToken);
        } while(continuationToken != null);
    }

    public void purchaseProduct(int requestCode, String sku, String purchaseType, String payload) {
        try {
            Bundle buyIntentBundle = billingService.getBuyIntent(BILLING_VERSION, packageName,
                    sku, purchaseType, payload);

            int responseCode = getResponseCodeFromBundle(buyIntentBundle);
            if(responseCode != BILLING_RESPONSE_RESULT_OK) {
                Log.e(TAG, "Error " + getResponseDesc(responseCode) + "  getting buy intent");
                return;
            }

            PendingIntent pendingIntent = buyIntentBundle.getParcelable(BUY_INTENT);
            if (pendingIntent != null) {
                activity.startIntentSenderForResult(pendingIntent.getIntentSender(),
                        requestCode, new Intent(), 0, 0, 0);
            } else {
                Log.e(TAG, "No pending intent from getBuyIntent");
            }

        } catch (RemoteException | IntentSender.SendIntentException e) {
            Log.e(TAG, "Error purchasing " + sku, e);
        }
    }

    private String listPurchases(String continuationToken) {
        Bundle ownedItems = null;
        try {
            ownedItems = billingService.getPurchases(3, packageName, "inapp", continuationToken);
        } catch (RemoteException e) {
            Log.e(TAG, "Error getting previous purchases", e);
            return null;
        }

        if (ownedItems == null) {
            Log.w(TAG, "Owned items is null getting previous purchases");
            return null;
        }

        int responseCode = getResponseCodeFromBundle(ownedItems);
        if (responseCode != BILLING_RESPONSE_RESULT_OK) {
            Log.e(TAG, "Error " + responseCode + " getting previous purchases");
            return null;
        }

        ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
        ArrayList<String> purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
        ArrayList<String> signatureList = ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
        continuationToken = ownedItems.getString("INAPP_CONTINUATION_TOKEN");

        for (int i = 0; purchaseDataList != null && i < purchaseDataList.size(); ++i) {
            String purchaseData = purchaseDataList.get(i);
            String signature = signatureList.get(i);
            String sku = ownedSkus.get(i);

            Log.i(TAG, sku + " - " + purchaseData + " - " + signature);
        }

        return continuationToken;
    }

    public void getAllSkuDetails() {
        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("prod_1");
        skuList.add("prod_2");
        getSkuDetails(skuList);
    }

    public void getSkuDetails(ArrayList<String> skuList) {
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        try {
            Bundle skuDetails = billingService.getSkuDetails(BILLING_VERSION, packageName,
                    IAP_TYPE, querySkus);

            int response = getResponseCodeFromBundle(skuDetails);
            if (response == BILLING_RESPONSE_RESULT_OK) {
                ArrayList<String> responseList = skuDetails.getStringArrayList(DETAILS_LIST);
                if(responseList == null) {
                    Log.e(TAG, "Empty response getting SKU details");
                    return;
                }

                for(String responseStr : responseList) {
                    Log.i(TAG, responseStr);
                }
            } else {
                Log.e(TAG, getResponseDesc(response));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Exception getting SKU details", e);
        }
    }

    private static int getResponseCodeFromBundle(Bundle b) {
        Object o = b.get(RESPONSE_CODE);
        if (o == null) {
            Log.d(TAG, "Bundle with null response code, assuming OK (known issue)");
            return BILLING_RESPONSE_RESULT_OK;
        }

        else if (o instanceof Integer) return (Integer) o;
        else if (o instanceof Long) return (int)((Long)o).longValue();
        else {
            Log.e(TAG, "Unexpected type for bundle response code:");
            Log.e(TAG, o.getClass().getName());
            throw new RuntimeException("Unexpected type for bundle response code: " +
                    o.getClass().getName());
        }
    }

    private static int IABHELPER_ERROR_BASE = -1000;

    private static String getResponseDesc(int code) {
        String[] iab_msgs = ("0:OK/1:User Canceled/2:Unknown/" +
                "3:Billing Unavailable/4:Item unavailable/" +
                "5:Developer Error/6:Error/7:Item Already Owned/" +
                "8:Item not owned").split("/");
        String[] iabhelper_msgs = ("0:OK/-1001:Remote exception during initialization/" +
                "-1002:Bad response received/" +
                "-1003:Purchase signature verification failed/" +
                "-1004:Send intent failed/" +
                "-1005:User cancelled/" +
                "-1006:Unknown purchase response/" +
                "-1007:Missing token/" +
                "-1008:Unknown error/" +
                "-1009:Subscriptions not available/" +
                "-1010:Invalid consumption attempt").split("/");

        if (code <= IABHELPER_ERROR_BASE) {
            int index = IABHELPER_ERROR_BASE - code;
            if (index >= 0 && index < iabhelper_msgs.length) return iabhelper_msgs[index];
            else return String.valueOf(code) + ":Unknown IAB Helper Error";
        }
        else if (code < 0 || code >= iab_msgs.length)
            return String.valueOf(code) + ":Unknown";
        else
            return iab_msgs[code];
    }

    public void handleActivityResult(int resultCode, Intent data) {
        int responseCode = data.getIntExtra(RESPONSE_CODE, BILLING_RESPONSE_RESULT_OK);
        String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
        String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

        if (resultCode == Activity.RESULT_OK) {
            if(responseCode == BILLING_RESPONSE_RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Log.i(TAG, sku + " purchased!");
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse purchase data.", e);
                }
            } else {
                Log.e(TAG, "Billing error on purchase " + getResponseDesc(responseCode));
            }
        } else if(resultCode == Activity.RESULT_CANCELED) {
            Log.w(TAG, "Purchase cancelled");
        } else {
            Log.e(TAG, "Unknown result code " + resultCode);
        }
    }
}
