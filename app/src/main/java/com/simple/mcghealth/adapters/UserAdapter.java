package com.simple.mcghealth.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.ThongTinCaNhanActivity;
import com.simple.mcghealth.entities.Bmi;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.interfaces.ItemClickListener;
import com.simple.mcghealth.utils.CommonUtils;

import java.text.ParseException;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewWriter> {
    private List<User> userList;
    private Activity activity;

    public void setData(List<User> list) {
        this.userList = list;
        notifyDataSetChanged();
    }

    public UserAdapter(List<User> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewWriter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewWriter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewWriter holder, int position) {

        AppDatabase appDatabase = AppDatabase.getInstance(activity.getApplicationContext());
        User user = userList.get(position);
        Bmi bmi = appDatabase.bmiDao().getBMILast(user.getId());
        if (user == null) {
            return;
        }
        holder.txtFullname.setText(user.getFullName());

        long age = 0;
        try {
            long distanceYear = CommonUtils.getDifferenceBetweenTwoDates(user.getBirthDay(), CommonUtils.getKeyToday());
            age = Math.round(distanceYear/365);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtBirthDay.setText(String.valueOf(age));

        holder.txtHeight.setText(holder.txtHeight.getText() + String.valueOf(bmi.getHeight()));
        holder.txtWeight.setText(holder.txtWeight.getText() +String.valueOf(bmi.getWeight()));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    new AlertDialog.Builder(activity)
                            .setTitle("Bạn chắc chắn xóa ?")
                            .setCancelable(false)
                            .setPositiveButton("Xóa", (dialog, id) -> {
                                appDatabase.bmiDao().deleteAllBMIByUser(user.getId());
                                appDatabase.userDao().deleteUser(user.getId());
                                userList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, userList.size());
                            })
                            .setNegativeButton("Quay lại", (dialog, id) -> {
                                dialog.cancel();
                            })
                            .create()
                            .show();
                } else {

                    //truyenid
                    Intent intent  = new Intent(activity, ThongTinCaNhanActivity.class);
                    intent.putExtra("IdUser",user.getId());
                    activity.startActivity(intent);
                    //truyenid



                    Toast.makeText(activity, "click " + user.getFullName(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }
        return 0;
    }

    public class UserViewWriter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView txtFullname;
        private TextView txtBirthDay;
        private TextView txtHeight;
        private TextView txtWeight;
        private ItemClickListener itemClickListener;

        public UserViewWriter(@NonNull View itemView) {
            super(itemView);
            txtFullname = itemView.findViewById(R.id.tvname);
            txtBirthDay = itemView.findViewById(R.id.tvbirthday);
            txtHeight = itemView.findViewById(R.id.tvHeight);
            txtWeight = itemView.findViewById(R.id.tvWeight);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }
    }
}

