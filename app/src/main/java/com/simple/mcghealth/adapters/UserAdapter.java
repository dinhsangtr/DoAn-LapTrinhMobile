package com.simple.mcghealth.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simple.mcghealth.AppDatabase;
import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.User;
import com.simple.mcghealth.interfaces.ItemClickListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewWriter> {
    private List<User> userList;
    private Activity activity;
    public void setData(List<User> list){
        this.userList=list;
        notifyDataSetChanged();
    }

    public UserAdapter(List<User> userList, Activity activity) {
        this.userList = userList;
        this.activity =activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewWriter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewWriter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewWriter holder, int position) {

        User user = userList.get(position);
        if (user==null){
            return;
        }
        holder.txtFullname.setText(user.getFullName());
        holder.txtBirthDay.setText(user.getBirthDay());
        holder.txtHeight.setText(String.valueOf(user.getHeight()));
        holder.txtWeight.setText(String.valueOf(user.getWeight()));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick){

                }
                else {
                    Toast.makeText(activity,"click " + user.getFullName(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList!=null)
        {
            return userList.size();
        }
        return 0;
    }

    public class UserViewWriter extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        private TextView txtFullname ;
        private TextView txtBirthDay;
        private TextView txtHeight ;
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
