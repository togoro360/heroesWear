package app.heroeswear.com.heroesmakers.login.models;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empatica.empalink.ConnectionNotAllowedException;
import com.empatica.empalink.EmpaDeviceManager;
import com.empatica.empalink.EmpaticaDevice;
import com.empatica.empalink.config.EmpaSensorStatus;
import com.empatica.empalink.config.EmpaSensorType;
import com.empatica.empalink.config.EmpaStatus;
import com.empatica.empalink.delegate.EmpaDataDelegate;
import com.empatica.empalink.delegate.EmpaStatusDelegate;


public class EmpaE4 implements EmpaDataDelegate, EmpaStatusDelegate {

    private static final int REQUEST_ENABLE_BT = 1;

    private static final int REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 1;


    private static final String EMPATICA_API_KEY = ""; //ADD API KEY


    private EmpaDeviceManager deviceManager = null;

    private Context ctx;
    private Activity act;

    public void initEmpa (Context ctx, Activity act) {
        this.ctx = ctx;
        this.act = act;
        initEmpaticaDeviceManager();
    }
    /******
     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     switch (requestCode) {
     case REQUEST_PERMISSION_ACCESS_COARSE_LOCATION:
     // If request is cancelled, the result arrays are empty.
     if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
     // Permission was granted, yay!
     initEmpaticaDeviceManager();
     } else {
     // Permission denied, boo!
     final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
     new AlertDialog.Builder(this)
     .setTitle("Permission required")
     .setMessage("Without this permission bluetooth low energy devices cannot be found, allow it in order to connect to the device.")
     .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int which) {
     // try again
     if (needRationale) {
     // the "never ask again" flash is not set, try again with permission request
     initEmpaticaDeviceManager();
     } else {
     // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
     Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
     Uri uri = Uri.fromParts("package", getPackageName(), null);
     intent.setData(uri);
     startActivity(intent);
     }
     }
     })
     .setNegativeButton("Exit application", new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int which) {
     // without permission exit is the only way
     finish();
     }
     })
     .show();
     }
     break;
     }
     }
     */

     private void initEmpaticaDeviceManager() {
     // Android 6 (API level 23) now require ACCESS_COARSE_LOCATION permission to use BLE
     if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
     ActivityCompat.requestPermissions(act, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, REQUEST_PERMISSION_ACCESS_COARSE_LOCATION);
     } else {

//     if (TextUtils.isEmpty(EMPATICA_API_KEY)) {
//     new AlertDialog.Builder(this)
//     .setTitle("Warning")
//     .setMessage("Please insert your API KEY")
//     .setNegativeButton("Close", new DialogInterface.OnClickListener() {
//     public void onClick(DialogInterface dialog, int which) {
//     // without permission exit is the only way
//     finish();
//     }
//     })
//     .show();
//     return;
//     }

     // Create a new EmpaDeviceManager. MainActivity is both its data and status delegate.
     deviceManager = new EmpaDeviceManager(ctx, this, this);

     // Initialize the Device Manager using your API key. You need to have Internet access at this point.
     deviceManager.authenticateWithAPIKey(EMPATICA_API_KEY);
     }
     }
/*
    @Override
    protected void onPause() {
        super.onPause();
        if (deviceManager != null) {
            deviceManager.stopScanning();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deviceManager != null) {
            deviceManager.cleanUp();
        }
    }
*/
    @Override
    public void didDiscoverDevice(EmpaticaDevice bluetoothDevice, String deviceName, int rssi, boolean allowed) {
        // Check if the discovered device can be used with your API key. If allowed is always false,
        // the device is not linked with your API key. Please check your developer area at
        // https://www.empatica.com/connect/developer.php
        if (allowed) {
            // Stop scanning. The first allowed device will do.
            deviceManager.stopScanning();
            try {
                // Connect to the device
                deviceManager.connectDevice(bluetoothDevice);

                Log.e("moshe","debice gound" );



            } catch (ConnectionNotAllowedException e) {
                // This should happen only if you try to connect when allowed == false.
                Log.e("moshe","couldn't connect" );

            }
        }
    }

    @Override
    public void didRequestEnableBluetooth() {
        // Request the user to enable Bluetooth
        //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }
    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // The user chose not to enable Bluetooth
            if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
                // You should deal with this
                return;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    */
    @Override
    public void didUpdateSensorStatus(@EmpaSensorStatus int status, EmpaSensorType type) {

        didUpdateOnWristStatus(status);
    }

    @Override
    public void didUpdateStatus(EmpaStatus status) {
        // Update the UI
        //     updateLabel(statusLabel, status.name() + devid);
        //     updateLabel(statusLabel, status.name() + devid);
        Log.e("moshe","connected0" );
        // The device manager is ready for use
        Log.e("moshe", "status is " + status);
        if (status == EmpaStatus.READY) {
            // Start scanning
            deviceManager.startScanning();
            Log.e("moshe","start scanning" );

            // The device manager has established a connection
            Log.e("moshe","connected1" );


        } else if (status == EmpaStatus.CONNECTED) {

            Log.e("moshe","connected2" );

            // The device manager disconnected from a device
        } else if (status == EmpaStatus.DISCONNECTED) {
            Log.e("moshe","connected3" );
        }
    }

    @Override
    public void didReceiveAcceleration(int x, int y, int z, double timestamp) {

        Log.e("moshe","acc " + x + " " + y + " " + z  );

    }

    @Override
    public void didReceiveBVP(float bvp, double timestamp) {

        Log.e("moshe","bvp " +bvp);

    }

    @Override
    public void didReceiveBatteryLevel(float battery, double timestamp) {
        Log.e("moshe","battery " + String.format("%.0f %%", battery * 100));
    }

    @Override
    public void didReceiveGSR(float gsr, double timestamp) {
        Log.e("moshe","gsr" +gsr);

    }

    @Override
    public void didReceiveIBI(float ibi, double timestamp) {

        Log.e("moshe","ibi" + 60/ibi  );

    }

    @Override
    public void didReceiveTemperature(float temp, double timestamp) {
        Log.e("moshe","temp " +temp  );
    }

    // Update a label with some text, making sure this is run in the UI thread

    @Override
    public void didReceiveTag(double timestamp) {

    }

    @Override
    public void didEstablishConnection() {

        // show();
    }

    @Override
    public void didUpdateOnWristStatus(@EmpaSensorStatus final int status) {

//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                if (status == EmpaSensorStatus.ON_WRIST) {
//
//                    //           ((TextView) findViewById(R.id.wrist_status_label)).setText("ON WRIST");
//                }
//                else {
//
//                    //((TextView) findViewById(R.id.wrist_status_label)).setText("NOT ON WRIST");
//                }
//            }
//        });
    }


}