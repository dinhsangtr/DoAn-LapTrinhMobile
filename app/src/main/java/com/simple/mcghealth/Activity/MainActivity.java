package com.simple.mcghealth.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.simple.mcghealth.Fragment.CalendarFragment;
import com.simple.mcghealth.Fragment.DietFragment;
import com.simple.mcghealth.Fragment.HealthFragment;
import com.simple.mcghealth.Fragment.HomeFragment;
import com.simple.mcghealth.Fragment.SettingFragment;
import com.simple.mcghealth.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.m_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.m_health));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.m_diet));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.m_calendar));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.m_setting));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new HealthFragment();
                        break;
                    case 3:
                        fragment = new DietFragment();
                        break;
                    case 4:
                        fragment = new CalendarFragment();
                        break;
                    case 5:
                        fragment = new SettingFragment();
                        break;
                }

                loadFragment(fragment);
            }
        });

        bottomNavigation.show(1, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You clicked" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You reselected" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_layout, fragment)
                .commit();
    }

}