package com.example.assignment6.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.model.Users;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private List<Users> dataList;
    private Context context;
    private onItemClickListener OnItemClickListener;
    public UserAdapter(Context context, List<Users> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;

        TextView txtTitle;


        UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.recycler_tv);
            mView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
           OnItemClickListener.onItemClick(getAdapterPosition(),v);

        }
    }
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_text_view, viewGroup, false);
        return new UserViewHolder(view);

    }
    public void setOnItemClickListener(onItemClickListener OnItemClickListener) {
        this.OnItemClickListener=OnItemClickListener;
    }




    @Override
    public void onBindViewHolder(UserViewHolder viewHolder, int position) {
        viewHolder.txtTitle.setText(dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public interface onItemClickListener
    {
        void onItemClick(int position,View view);
    }
}
