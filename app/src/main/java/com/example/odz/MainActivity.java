package com.example.odz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        Toolbar toolbar = (Toolbar) getSupportActionBar().getCustomView();

        TabLayout tabLayout = toolbar.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Library"));
        NavController navController =  Navigation.findNavController(this, R.id.nav_host_fragment);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                switch (selectedTabPosition) {
                    case 0:
                        navController.navigate(R.id.mainFragment);
                        break;
                    case 1:
                        navController.navigate(R.id.libraryFragment);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int selectedTabPosition = tab.getPosition();
                switch (selectedTabPosition) {
                    case 0:
                        navController.navigate(R.id.mainFragment);
                        break;
                    case 1:
                        navController.navigate(R.id.libraryFragment);
                        break;
                    default:
                        break;
                }
            }
        });



    }


}