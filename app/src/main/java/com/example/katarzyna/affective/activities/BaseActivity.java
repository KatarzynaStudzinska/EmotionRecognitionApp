package com.example.katarzyna.affective.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.katarzyna.affective.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

/**
 * Created by katarzyna on 09.05.17.
 */

public class BaseActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    Drawer result;
    Toolbar toolbar;

    protected static final int LOGIN = 0;
    protected static final int PREVIEW = 1;
    protected static final int VIDEO_GALLERY = 2;
    protected static final int NEWS = 2;
    protected static final int NAVDRAWER_ITEM_INVALID = -1;
    protected static final int DIVIDER = -2;

    // titles for navdrawer items (indices must correspond to the above)
    protected static final int[] NAVDRAWER_TITLE_RES_ID = new int[]{
            R.string.drawer_login,
            R.string.drawer_emotionPreview,
            R.string.drawer_videoGallery,
            R.string.drawer_news,
    };

    // Icons for the above items
    protected static final int[] NAVDRAWER_ICONS = new int[] {
            R.drawable.ic_exmp,
            R.drawable.ic_exmp,
            R.drawable.ic_exmp,
            R.drawable.ic_exmp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        ;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onPostCreate");
        super.onPostCreate(savedInstanceState);



        ArrayList<IDrawerItem> drawerItems = createNavigationDrawerItems();
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(drawerItems.toArray(new IDrawerItem[drawerItems.size()]))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Log.d(TAG, "position  " +position);
                        Intent intent;
                        switch (position - 2){
                            case LOGIN:
                                Log.d(TAG, "LOGIN");
                                break;
                            case PREVIEW:
                                intent = new Intent(BaseActivity.this, EmotionPreviewActivity.class);
                                startActivity(intent);
                                break;
                            case VIDEO_GALLERY:
                                intent = new Intent(BaseActivity.this, VideoGallery.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    private ArrayList<IDrawerItem> createNavigationDrawerItems() {

        ArrayList<Integer> navigationItemIndex = new ArrayList<>();
        navigationItemIndex.add(DIVIDER);
        navigationItemIndex.add(LOGIN);
        navigationItemIndex.add(PREVIEW);
        navigationItemIndex.add(VIDEO_GALLERY);

        ArrayList<IDrawerItem> navigationDrawerItems = new ArrayList<>();

        for (int itemId : navigationItemIndex) {

            if (itemId == DIVIDER) {
                navigationDrawerItems.add(new DividerDrawerItem().withIdentifier(DIVIDER));
            }
            else {
                navigationDrawerItems.add(new PrimaryDrawerItem()
                        .withName(NAVDRAWER_TITLE_RES_ID[itemId])
                        .withIcon(getResources().getDrawable(NAVDRAWER_ICONS[itemId]))
                        .withIdentifier(itemId));
            }
        }
        return navigationDrawerItems;
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

}