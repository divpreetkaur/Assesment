package com.example.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<user> al = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tname;
        TextView tage;
        TextView tphone;
        ImageView image;
        TextView tcount;
        Button button;
        ImageView remove;
        RatingBar ratingBar;
        int count_likes = 0;
        RelativeLayout relativeLayout;

        public MyViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
            count_likes = 0;
            tname = (TextView) v.findViewById(R.id.text_tv_name);
            tage = (TextView) v.findViewById(R.id.text_tv_age);
            tphone = (TextView) v.findViewById(R.id.text_tv_phone);
            tcount = (TextView) v.findViewById(R.id.text_tv_count);
            image = (ImageView) v.findViewById(R.id.text_iv);
            button = (Button) v.findViewById(R.id.btn_like);
            ratingBar =(RatingBar)v.findViewById(R.id.ratingBar);
            remove = (ImageView) v.findViewById(R.id.text_btn_remove);
            button.setOnClickListener(this);
            remove.setOnClickListener(this);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        user u = al.get(getAdapterPosition());
                        u.setRating(rating);
                    }
                }
            });
        }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_like:
                user temp = al.get(getAdapterPosition());
                temp.likes++;
                notifyItemChanged(getAdapterPosition());
                break;
            case R.id.text_btn_remove:
                al.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), al.size());
                break;


        }
    }

}


    public MyAdapter(ArrayList<user> arrayList) {
            al=arrayList;
        }


        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

            RelativeLayout v =  (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_my_text_view, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            user u = al.get(position);
            my_text_view view = new my_text_view();
            holder.tname.setText(u.getname());
            holder.tage.setText(u.getAge());
            holder.tphone.setText(u.getPhoneNumber());
            holder.tcount.setText(String.valueOf(u.likes));
            holder.ratingBar.setRating(u.ratings);
            Picasso.with(holder.itemView.getContext()).load("https://cdn.pixabay.com/photo/2018/02/09/21/46/rose-3142529__340.jpg").into(holder.image);

        }


            @Override
            public int getItemCount () {
                return al.size();
            }
        }


