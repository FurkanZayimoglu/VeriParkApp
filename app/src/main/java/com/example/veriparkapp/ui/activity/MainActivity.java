package com.example.veriparkapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.veriparkapp.R;
import com.example.veriparkapp.ui.fragment.DusenlerFragment;
import com.example.veriparkapp.ui.fragment.Hacim100Fragment;
import com.example.veriparkapp.ui.fragment.Hacim30Fragment;
import com.example.veriparkapp.ui.fragment.Hacim50Fragment;
import com.example.veriparkapp.ui.fragment.HisseEndeksFragment;
import com.example.veriparkapp.ui.fragment.YukselenFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    public static int navItemIndex = 0;
    private static final String TAG_HISSE = "hisseendeks";
    private static final String TAG_YUKSELEN = "yukselen";
    private static final String TAG_DUSEN = "dusenler";
    private static final String TAG_HACIM30 = "hacim30";
    private static final String TAG_HACIM50 = "hacim50";
    private static final String TAG_HACIM100 = "hacim100";
    public static String CURRENT_TAG = TAG_HISSE;

    private String[] activityTitles;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        setUpNavigationView();

        if (savedInstanceState == null){
            navItemIndex = 0;
            CURRENT_TAG = TAG_HISSE;
            loadHisseFragment();
        }
    }

    private void loadHisseFragment() {

        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
            drawer.closeDrawers();
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.frame, fragment, CURRENT_TAG);
                transaction.commitAllowingStateLoss();
            }
        };

        if (runnable != null) {
            mHandler.post(runnable);
        }

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex){
            case 0:
                HisseEndeksFragment hisseEndeksFragment = new HisseEndeksFragment();
                return hisseEndeksFragment;
            case 1:
                YukselenFragment yukselenFragment = new YukselenFragment();
                return yukselenFragment;
            case 2:
                DusenlerFragment dusenlerFragment = new DusenlerFragment();
                return dusenlerFragment;
            case 3:
                Hacim30Fragment hacim30Fragment = new Hacim30Fragment();
                return hacim30Fragment;
            case 4:
                Hacim50Fragment hacim50Fragment = new Hacim50Fragment();
                return hacim50Fragment;
            case 5:
                Hacim100Fragment hacim100Fragment = new Hacim100Fragment();
                return hacim100Fragment;
            default:
                return new HisseEndeksFragment();
        }
    }

    private void setToolbarTitle() {
         getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_hisse:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HISSE;
                        break;
                    case R.id.nav_yukselen:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_YUKSELEN;
                        break;
                    case R.id.nav_dusenler:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_DUSEN;
                        break;
                    case R.id.nav_hacim30:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_HACIM30;
                        break;
                    case R.id.nav_hacim50:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_HACIM50;
                        break;
                    case R.id.nav_hacim100:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_HACIM100;
                        break;
                    default:
                        navItemIndex = 0;
                }
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                item.setChecked(true);
                loadHisseFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
        }

        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HISSE;
                loadHisseFragment();
                return;
            }
        }
        super.onBackPressed();
    }

}
