package com.example.katarzyna.affective.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.katarzyna.affective.R;
import com.example.katarzyna.affective.fragments.VideoFragment;

/**
 * Created by katarzyna on 09.05.17.
 */


//// TODO: 09.05.17 make grid with some youtube - video
public class VideoGallery extends BaseActivity {

    private String TAG = VideoGallery.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_content);

        if (fragment == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            VideoFragment pdf = VideoFragment.newInstance("Very important video title");
            fragmentTransaction.add(R.id.main_content, pdf, VideoFragment.class.getSimpleName());
            fragmentTransaction.commit();

        }

    }


}
