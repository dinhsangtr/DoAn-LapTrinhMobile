package com.simple.mcghealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.AddInfoMainActivity;
import com.simple.mcghealth.activities.WalkingStepActivity;
import com.simple.mcghealth.adapters.UserAdapter;
import com.simple.mcghealth.entities.User;

import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout lnWalkingStep;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> listU;
    private AppDatabase appDatabase;

    private FloatingActionButton btnAddUser;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //ánh xạ
        lnWalkingStep = (LinearLayout) view.findViewById(R.id.lnWalkingStep);
        recyclerView = view.findViewById(R.id.rcvUser);
        btnAddUser = view.findViewById(R.id.btnAddUser);

        //
        appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());

        listU = appDatabase.userDao().getAllUser();
        if (listU.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            userAdapter = new UserAdapter(listU, getActivity());
            recyclerView.setAdapter(userAdapter);

            userAdapter.setData(listU);
        }

        lnWalkingStep.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), WalkingStepActivity.class);
            startActivity(intent);
        });

        btnAddUser.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddInfoMainActivity.class);
            startActivity(intent);
        });

        return view;
    }
}