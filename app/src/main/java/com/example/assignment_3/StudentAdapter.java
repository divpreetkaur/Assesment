package com.example.assignment_3;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    ArrayList<Student> studentArrayList = new ArrayList<>();
    private static StudentAdapter.MyClickListener sClickListener;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Textname;
        TextView Textrollno;
        TextView Textclass;
        Button button;
        LinearLayout ll;

        public MyViewHolder(LinearLayout ll) {
            super(ll);

            Textname = (TextView) ll.findViewById(R.id.recycler_tv_name);
            Textrollno = (TextView) ll.findViewById(R.id.recycler_tv_rollno);
            Textclass = (TextView) ll.findViewById(R.id.recycler_tv_class);
            ll.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public StudentAdapter(ArrayList<Student> arrayList) {
        studentArrayList=arrayList;
        notifyDataSetChanged();
    }


    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        LinearLayout linearLayout =  (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Student s = studentArrayList.get(position);
        holder.Textname.setText(s.getName());
        holder.Textrollno.setText(s.getRollno());
        holder.Textclass.setText(s.getClasses());

    }
    void setOnItemClickListener(StudentAdapter.MyClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }


    void deleteItem(int index) {
        studentArrayList.remove(index);
        notifyItemRemoved(index);
    }
    @Override
    public int getItemCount () {
        return studentArrayList.size();
    }
    interface MyClickListener {
        void onItemClick(int position, View v);
    }
}





