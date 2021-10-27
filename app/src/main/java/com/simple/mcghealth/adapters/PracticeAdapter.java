package com.simple.mcghealth.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.practice;
import com.simple.mcghealth.interfaces.ItemClickListener;

import java.util.List;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.PracticeViewHodlo>{

    private List<practice> practiceList;
    private Activity activity;
    public void setData(List<practice> practiceList){
        this.practiceList =practiceList;
        notifyDataSetChanged();
    }

    public PracticeAdapter(List<practice> practiceList, Activity activity) {
        this.practiceList = practiceList;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PracticeViewHodlo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_healt,parent, false);
        return new PracticeViewHodlo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticeViewHodlo holder, int position) {

        practice practice= practiceList.get(position);
        if (practice == null){
            return;
        }
        holder.tvnameunit.setText(practice.getName());
        holder.tvContent.setText(practice.getContent());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick){

                }
                else {
                    Toast.makeText(activity,"click " + practice.getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (practiceList!=null)
        {
            return practiceList.size();
        }
        return 0;
    }

    public class PracticeViewHodlo extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvnameunit;
        private TextView tvContent;
        private ItemClickListener itemClickListener;

        public PracticeViewHodlo(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvnameunit=itemView.findViewById(R.id.tvnameunit);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
