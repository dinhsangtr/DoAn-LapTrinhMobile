package com.simple.mcghealth.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.adapters.PracticeAdapter;
import com.simple.mcghealth.adapters.UserAdapter;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.utils.BMIclass;
import com.simple.mcghealth.entities.practice;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HealthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvbmi;
    private TextView tvcmt;
    private TextView tvnameUnit;
    private TextView tvContent;
    private TextView tvname;

    private RecyclerView recyclerView;
    private PracticeAdapter practiceAdapter;
    private LinearLayout lnWalkingStep;
    List<practice> practiceslist;
    private AppDatabase appDatabase;


    public HealthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthFragment newInstance(String param1, String param2) {
        HealthFragment fragment = new HealthFragment();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());

        List<practice> practices = practice.lisTTD();
        lnWalkingStep = (LinearLayout) view.findViewById(R.id.lnWalkingStep);
        recyclerView = view.findViewById(R.id.rcvpractice);
        appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());
        tvnameUnit = view.findViewById(R.id.tvnameunit);
        tvContent = view.findViewById(R.id.tvContent);
        tvbmi = view.findViewById(R.id.tvbmi);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        practiceAdapter = new PracticeAdapter(practices, getActivity());
        recyclerView.setAdapter(practiceAdapter);
        int BMI = 0;
        User user = appDatabase.userDao().getTOP1();
        if (user != null) {

            float i = (user.getHeight() * 2 / 100);
            BMI = Math.round(user.getWeight() / i);
            tvbmi.setText(BMI + "");


        } else {
            tvbmi.setText("0");
        }

        return view;
    }

}