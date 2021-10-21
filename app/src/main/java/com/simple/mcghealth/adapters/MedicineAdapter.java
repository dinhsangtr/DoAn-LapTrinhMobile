package com.simple.mcghealth.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.activities.SetupReminderActivity;
import com.simple.mcghealth.entities.MedicationTime;
import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.entities.relations.MedicationWithTime;
import com.simple.mcghealth.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<Medicine> medicineList;
    private Activity context;
    private AppDatabase db;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Medicine> list) {
        this.medicineList = list;
        notifyDataSetChanged();
    }

    public MedicineAdapter(List<Medicine> dataList, Activity context) {
        this.medicineList = dataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        if (medicine == null) return;
        db = AppDatabase.getInstance(context);

        //lấy danh sách thời gian thông báo
        String time = "";
        MedicationWithTime mWtList = db.medicationTimeDao().getDataById(medicine.getId());
        List<MedicationTime> mtList = mWtList.medicationTimeList;
        List<MedicationTime> mtListStatus0 = new ArrayList<>();

        for (int i = 0; i < mtList.size(); i++) {
            if (mtList.get(i).getStatus() == 0) {
                mtListStatus0.add(mtList.get(i));
            }
        }

        for (int i = 0; i < mtListStatus0.size(); i++) {
            mtList.remove(mtListStatus0.get(i));
        }

        if (mtList.size() == 0) {
            time = "Không có";
        } else {
            for (int i = 0; i < mtList.size(); i++) {
                time += mtList.get(i).getTimeDrink();
                if (i != (mtList.size() - 1)) {
                    time += " - ";
                }
            }
        }

        holder.txtDrugName.setText(medicine.getName());
        holder.txtNote.setText(medicine.getNote());
        holder.txtTime.setText(time);
        //image: byte[] --> bitmap
        byte[] drugImage = medicine.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(drugImage, 0, drugImage.length);
        holder.imageDrug.setImageBitmap(bitmap);

        holder.setItemClickListener((view, position1, isLongClick) -> {
            if (isLongClick) {
                new AlertDialog.Builder(context)
                        .setTitle("Bạn chắc chắn xóa ?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", (dialog, id) -> {
                            db.medicationTimeDao().deleteTime(medicine.getId());
                            db.medicineDao().deleteMedicine(medicine);
                            medicineList.remove(position1);
                            notifyItemRemoved(position1);
                            notifyItemRangeChanged(position1, medicineList.size());
                        })
                        .setNegativeButton("Quay lại", (dialog, id) -> {
                            dialog.cancel();
                        })
                        .create()
                        .show();
            } else {

                Intent intent = new Intent(context, SetupReminderActivity.class);
                intent.putExtra("medicineID", medicine.getId());
                context.startActivity(intent);

                Toast.makeText(context, "Click" + medicineList.get(position1).getName(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txtDrugName, txtTime, txtNote;
        ImageView imageDrug;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDrugName = itemView.findViewById(R.id.txtDrugName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtNote = itemView.findViewById(R.id.txtNote);
            imageDrug = itemView.findViewById(R.id.imageDrug);

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
