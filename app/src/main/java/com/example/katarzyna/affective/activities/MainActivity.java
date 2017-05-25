package com.example.katarzyna.affective.activities;

import android.os.Bundle;
import android.util.Log;

/**
 * This is a very bare sample app to demonstrate the usage of the CameraDetector object from Affectiva.
 * It displays statistics on frames per second, percentage of time a face was detected, and the user's smile score.
 *
 * The app shows off the maneuverability of the SDK by allowing the user to start and stop the SDK and also hide the camera SurfaceView.
 *
 * For use with SDK 2.02
 */

//// TODO: 09.05.17  make baseActivity with toolbar and ...?
public class MainActivity extends BaseActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    }