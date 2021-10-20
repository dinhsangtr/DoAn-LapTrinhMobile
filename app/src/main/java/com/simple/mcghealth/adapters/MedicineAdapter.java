package com.simple.mcghealth.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.Medicine;
import com.simple.mcghealth.interfaces.ItemClickListener;

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
        db = AppDatabase.getInstance(context);

        holder.txtDrugName.setText(medicine.getName());
        holder.txtNote.setText(medicine.getNote());

        holder.setItemClickListener((view, position1, isLongClick) -> {
            if (isLongClick)
                Toast.makeText(context, "Long Click: " + medicineList.get(position1), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, " " + medicineList.get(position1), Toast.LENGTH_SHORT).show();
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
