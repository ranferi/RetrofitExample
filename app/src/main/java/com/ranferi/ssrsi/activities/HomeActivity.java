package com.ranferi.ssrsi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ranferi.ssrsi.R;
import com.ranferi.ssrsi.fragments.HomeFragment;
import com.ranferi.ssrsi.fragments.PlaceListFragment;
import com.ranferi.ssrsi.fragments.ProfileFragment;
import com.ranferi.ssrsi.fragments.SearchFragment;
import com.ranferi.ssrsi.fragments.VisitedFragment;
import com.ranferi.ssrsi.helper.SharedPrefManager;
import com.ranferi.ssrsi.model.User;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    NavigationView navigationView;

    // index para identificar el item actual del menu nav.
    public static int navItemIndex = 0;

    // etiquetas usadas para fijar fragmentos
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "perfil";
    private static final String TAG_SEARCH = "busqueda";
    private static final String TAG_PLACES = "sitios";
    private static final String TAG_VISITED = "visitados";
    public static String CURRENT_TAG = TAG_HOME;

    // títulos de cada item en el menu nav.
    private String[] activityTitles;

    public final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration
                .Builder()
                //.deleteRealmIfMigrationNeeded()
                .name("ssrsi.realm")
                .build();
        Realm.setDefaultConfiguration(config);

        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        View headerView = navigationView.getHeaderView(0);
        TextView textViewName = headerView.findViewById(R.id.textViewNameHeader);
        User user = SharedPrefManager.getInstance(this).getUser();
        if (user.getName() != null && user.getName() .isEmpty())
            textViewName.setText(user.getName());
        else if (user.getUser() != null && !user.getUser().isEmpty())
            textViewName.setText(user.getUser());
        else
            textViewName.setText("usuario");
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadFragment().run();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUpNavigationView() {
/*        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                loadFragment();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };*/

        final SmoothActionBarDrawerToggle actionBarDrawerToggle = new SmoothActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                    break;
                case R.id.nav_profile:
                    navItemIndex = 1;
                    CURRENT_TAG = TAG_PROFILE;
                    break;
                case R.id.nav_visited:
                    navItemIndex = 2;
                    CURRENT_TAG = TAG_VISITED;
                    break;
                case R.id.nav_places:
                    navItemIndex = 3;
                    CURRENT_TAG = TAG_PLACES;
                    break;
                case R.id.nav_search:
                    navItemIndex = 4;
                    CURRENT_TAG = TAG_SEARCH;
                    break;
                case R.id.nav_logout:
                    logout();
                    break;
                default:
                    navItemIndex = 0;
            }

            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }
            menuItem.setChecked(true);
            actionBarDrawerToggle.runWhenIdle(loadFragment());

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    private Fragment getFragment() {
        switch (navItemIndex) {
            case 0:
                return new PlaceListFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new VisitedFragment();
            case 3:
                return new VisitedFragment();
            case 4:
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

    private Runnable loadFragment() {
        setToolbarTitle();

 /*       if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }*/

        Runnable mPendingRunnable = () -> {
            // Se actualiza el contenido principal
            Fragment fragment = getFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
            fragmentTransaction.commitAllowingStateLoss();

        };
/*        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }*/


        // drawer.closeDrawer(GravityCompat.START);

        invalidateOptionsMenu();

        return mPendingRunnable;
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, SignInActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

        private Runnable runnable;

        SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }
        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }
        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
        }

        void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }

}
