package com.ranferi.ssrsi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.fragments.HomeFragment;
import com.ranferi.ssrsi.fragments.PlaceListFragment;
import com.ranferi.ssrsi.fragments.ProfileFragment;
import com.ranferi.ssrsi.fragments.SearchFragment;
import com.ranferi.ssrsi.helper.SharedPrefManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextViewName;
    private DrawerLayout drawer;
    private Handler mHandler;
    private Toolbar toolbar;
    NavigationView navigationView;

    // index para identificar el item actual del menu nav.
    public static int navItemIndex = 0;

    // etiquetas usadas para fijar fragmentos
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "perfil";
    private static final String TAG_SEARCH = "busqueda";
    private static final String TAG_PLACES = "sitios";
    public static String CURRENT_TAG = TAG_HOME;

    // títulos de cada item en el menu nav.
    private String[] activityTitles;

    public final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        // toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // navigationView.setNavigationItemSelectedListener(this);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        View headerView = navigationView.getHeaderView(0);
        mTextViewName = (TextView) headerView.findViewById(R.id.textViewNameHeader);
        mTextViewName.setText(SharedPrefManager.getInstance(this).getUser().getName());

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // se muestra el fragmento home
        //displaySelectedScreen(R.id.nav_home);

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadFragment();
        }


    }

    @Override
    public void onBackPressed() {
        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = new SearchFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_search:
                fragment = new PlaceListFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        // reemplazar los fragmentos
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_places:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_PLACES;
                        break;
                    case R.id.nav_search:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SEARCH;
                        break;
                    case R.id.nav_logout:
                        logout();
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private Fragment getFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new PlaceListFragment();
            case 3:
                return new SearchFragment();
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(activityTitles[navItemIndex]);
        }
    }

    private void loadFragment() {
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // update the main content by replacing fragments
                        Fragment fragment = getFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        fragmentTransaction
                                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
                        fragmentTransaction.commitAllowingStateLoss();
                    }
                });

            }
        };
        mHandler.post(mPendingRunnable);

        drawer.closeDrawer(GravityCompat.START);

        invalidateOptionsMenu();
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // displaySelectedScreen(item.getItemId());
        // return true;

        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
