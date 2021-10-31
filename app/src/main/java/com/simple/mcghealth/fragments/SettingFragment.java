package com.simple.mcghealth.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.HelpActivity;
import com.simple.mcghealth.activities.InfoAppActivity;
import com.simple.mcghealth.activities.InfoDevelopers;
import com.simple.mcghealth.activities.IntroFTActivity;
import com.simple.mcghealth.activities.UserManualActivity;
import com.simple.mcghealth.dao.BmiDao;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.utils.MyPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Button btnReset;
    private AppDatabase appDatabase;
    private Activity activity;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private LinearLayout layout5;
    private MyPreferences myPreferences;


    public SettingFragment() {
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static String getKeyToday() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        anhXa(view);

        this.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("Xác nhận");
                b.setMessage("Bạn có muốn cài đặt lại hệ thống?");

                b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());
                            appDatabase.bmiDao().deleteAllBmi();
                            appDatabase.medicationTimeDao().deleteAllMedication_Time();
                            appDatabase.medicineDao().deleteAllMedicine();
                            //appDatabase.walkingStepDao().deleteAllWalking_Steps();
                            appDatabase.userDao().deleteAllUser();

                            myPreferences = new MyPreferences(getActivity());
                            myPreferences.setFirstTimeLaunch(true);
                            Intent intent = new Intent(getActivity(), IntroFTActivity.class);
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(), "null Infomation", Toast.LENGTH_SHORT).show();
                           // Log.e("Error delete ",e.toString());
                        }
                    }
                });
                b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
        this.layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserManualActivity.class);
                startActivity(intent);
            }
        });
        this.layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoAppActivity.class);
                startActivity(intent);
            }
        });
        this.layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InfoDevelopers.class);
                startActivity(intent);
            }
        });
        this.layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void anhXa( View view){
        btnReset = view.findViewById(R.id.btnReset);
        layout2=view.findViewById(R.id.layout2);
        layout3 = view.findViewById(R.id.layout3);
        layout4 = view.findViewById(R.id.layout4);
        layout5 = view.findViewById(R.id.layout5);
    }
}