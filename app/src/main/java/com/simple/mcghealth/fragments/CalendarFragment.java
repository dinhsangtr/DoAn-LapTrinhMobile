package com.simple.mcghealth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.AddMedicineActivity;
import com.simple.mcghealth.adapters.MedicineAdapter;
import com.simple.mcghealth.entities.Medicine;

import java.util.List;

public class CalendarFragment extends Fragment {
    private FloatingActionButton addSchedule;
    private RelativeLayout empty_view;
    private RecyclerView recyclerViewSchedule;

    private List<Medicine> medicineList;
    private AppDatabase db;
    private MedicineAdapter adapter;

    private boolean isBackFromB;

    public CalendarFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBackFromB = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isBackFromB = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isBackFromB) updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mappings(view); //init UI
        updateUI();
        return view;
    }

    private void updateUI() {
        db = AppDatabase.getInstance(this.getActivity().getApplicationContext());
        medicineList = db.medicineDao().getAll();

        if (medicineList.size() > 0) {
            empty_view.setVisibility(View.INVISIBLE); //

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewSchedule.setLayoutManager(linearLayoutManager);

            adapter = new MedicineAdapter(medicineList, getActivity());
            recyclerViewSchedule.setAdapter(adapter);
        } else {
            empty_view.setVisibility(View.VISIBLE);
        }
        addSchedule.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), AddMedicineActivity.class)));
    }

    private void mappings(View view) {
        addSchedule = (FloatingActionButton) view.findViewById(R.id.addSchedule);
        empty_view = (RelativeLayout) view.findViewById(R.id.empty_view);
        recyclerViewSchedule = (RecyclerView) view.findViewById(R.id.recyclerViewSchedule);
    }
}