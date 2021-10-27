package com.simple.mcghealth.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.AddInfoMainActivity;
import com.simple.mcghealth.activities.MainActivity;
import com.simple.mcghealth.activities.WalkingStepActivity;
import com.simple.mcghealth.adapters.UserAdapter;
import com.simple.mcghealth.entities.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> listU;
    private AppDatabase appDatabase;
    private LinearLayout lnWalkingStep;
    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lnWalkingStep = (LinearLayout) view.findViewById(R.id.lnWalkingStep);
        recyclerView = view.findViewById(R.id.rcvUser);
        appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());

        listU=appDatabase.userDao().getAll();
        if (listU.size()>0 ){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            userAdapter = new UserAdapter(listU,getActivity());
            recyclerView.setAdapter(userAdapter);

            userAdapter.setData(listU);


        }





        lnWalkingStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalkingStepActivity.class);
                startActivity(intent);
            }
        });


        Button btnTest = view.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddInfoMainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}