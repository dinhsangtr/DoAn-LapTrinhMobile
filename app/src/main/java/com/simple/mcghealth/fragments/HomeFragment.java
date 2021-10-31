package com.simple.mcghealth.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.AddInfoMainActivity;
import com.simple.mcghealth.adapters.UserAdapter;
import com.simple.mcghealth.databinding.FragmentHomeBinding;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.entities.WalkingStep;
import com.simple.mcghealth.interfaces.UpdateUiCallBack;
import com.simple.mcghealth.services.StepService;
import com.simple.mcghealth.utils.CommonUtils;

import java.util.List;

public class HomeFragment extends Fragment {

    private RelativeLayout lnWalkingStep;
    private TextView txtStepTarget, txtStepCurrent;
    private ImageView imgViewEditTarget, btnResetStep;
    private String target = "";

    //user
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> listU;
    private FloatingActionButton btnAddUser;

    private AppDatabase appDatabase;
    private WalkingStep ws;

    //step
    private boolean mIsBind;
    private FragmentHomeBinding mBinding;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());
            ws = appDatabase.walkingStepDao().getStep();
            showStepCount(ws.getStep(), 0);

            StepService stepService = ((StepService.StepBinder) service).getService();
            showStepCount(ws == null ? 0 : ws.getStep(), stepService.getStepCount());

            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    showStepCount(ws == null ? 0 : ws.getStep(), stepCount);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        anhxa(mBinding.getRoot());
        initData();


        appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());

        //USER
        listU = appDatabase.userDao().getAllUser();
        if (listU.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            userAdapter = new UserAdapter(listU, getActivity());
            recyclerView.setAdapter(userAdapter);

            userAdapter.setData(listU);
        }

        btnAddUser.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddInfoMainActivity.class);
            startActivity(intent);
        });


        //WALKING STEP
        txtStepTarget.setText(appDatabase.walkingStepDao().getStep().getTarget()+"");


        target = txtStepTarget.getText().toString();
        imgViewEditTarget.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.dialog_update_target);
            int width = WindowManager.LayoutParams.WRAP_CONTENT;
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();

            EditText editText = dialog.findViewById(R.id.edtTarget);
            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

            editText.setSelectAllOnFocus(true);
            editText.setText(target);

            btnUpdate.setOnClickListener(view1 -> {
                String str = editText.getText().toString().trim();
                if (str.length() > 6){
                    str = 999999 + "";
                }
                try {
                    appDatabase.walkingStepDao().updateTarget(ws.getId(), Integer.parseInt(str));
                    txtStepTarget.setText(str);
                    dialog.dismiss();
                }catch (Exception e){
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                    Log.e("ERROR targer", e.toString());
                }
            });

        });


        //Reset step
        btnResetStep.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setMessage("Đặt số bước chân về 0");
            builder.setPositiveButton("Đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        appDatabase.walkingStepDao().updateStep(ws.getId(), 0);
                        //txtStepCurrent.setText("0");
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                        Log.e("ERROR targer", e.toString());
                    }
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();



        });
        /*
        lnWalkingStep.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), WalkingStepActivity.class);
            startActivity(intent);
        });
        */

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsBind) {
            getActivity().unbindService(mServiceConnection);
        }
    }

    private void initData() {
        appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());
        List<WalkingStep> wsList = appDatabase.walkingStepDao().getAll();
        if (wsList.size() == 0) {
            appDatabase.walkingStepDao().insert(new WalkingStep(0, 0, CommonUtils.getKeyToday()));
            WalkingStep ws = appDatabase.walkingStepDao().getStep();
            showStepCount(ws.getStep(), 0);
        } else {
            WalkingStep ws = appDatabase.walkingStepDao().getStep();
            showStepCount(ws.getStep(), 0);
        }
        setupService();
    }

    //gán lại khi số bước thay đổi
    public void showStepCount(int totalStepNum, int currentCounts) {
        if (currentCounts < totalStepNum) {
            currentCounts = totalStepNum;
            //cap nhat lai db
            appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());
            WalkingStep ws = appDatabase.walkingStepDao().getStep();
            appDatabase.walkingStepDao().updateStep(ws.getId(), currentCounts);
        }
        mBinding.curStep.setText(String.valueOf(currentCounts));
    }

    private void setupService() {
        Intent intent = new Intent(getActivity(), StepService.class);
        mIsBind = getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getActivity().startForegroundService(intent);
        } else {
            getActivity().startService(intent);
        }
    }

    private void anhxa(View view) {
        //Step
        lnWalkingStep = view.findViewById(R.id.lnWalkingStep);
        txtStepCurrent =  view.findViewById(R.id.curStep);
        txtStepTarget =  view.findViewById(R.id.txtStepTarget);
        imgViewEditTarget = view.findViewById(R.id.imgViewEditTarget);
        btnResetStep = view.findViewById(R.id.btnResetStep);
        //User
        recyclerView = view.findViewById(R.id.rcvUser);
        btnAddUser = view.findViewById(R.id.btnAddUser);
    }


}