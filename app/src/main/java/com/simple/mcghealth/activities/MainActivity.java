package com.simple.mcghealth.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.simple.mcghealth.R;
import com.simple.mcghealth.fragments.CalendarFragment;
import com.simple.mcghealth.fragments.DietFragment;
import com.simple.mcghealth.fragments.HealthFragment;
import com.simple.mcghealth.fragments.HomeFragment;
import com.simple.mcghealth.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation; //menu

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Objects.requireNonNull(getSupportActionBar()).hide();
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


        bottomNavigation.show(1, true); //vị trí trang fragment bắt dầu
        bottomNavigation.setOnClickMenuListener(item -> {
            //Toast.makeText(getApplicationContext(), "You clicked" + item.getId(), Toast.LENGTH_SHORT).show();
        });

        bottomNavigation.setOnReselectListener(item -> {
            //Toast.makeText(getApplicationContext(), "You reselected" + item.getId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Bạn có muốn thoát ứng dụng?");
        builder.setPositiveButton("Thoát ngay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Khoi tao lai Activity main - khi mở lại sẽ vaog main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                //if user pressed "yes", then he is allowed to exit from application
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(startMain);
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_main_layout, fragment)
                .commit();
    }

}