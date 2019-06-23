package com.example.assignment5.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment5.R;
import com.example.assignment5.callback.OnItemClickListener;
import com.example.assignment5.model.Student;

import java.util.ArrayList;


    public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
        private ArrayList<Student> studentArrayList=new ArrayList<Student>();
        private OnItemClickListener onItemClickListener;
        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView textName;
            TextView textRollno;
            TextView textClass;



            public MyViewHolder(LinearLayout ll) {
                super(ll);
                  textName=(TextView)ll.findViewById(R.id.recycler_tv_name);
                  textRollno=(TextView)ll.findViewById(R.id.recycler_tv_rollno);
                  textClass=(TextView)ll.findViewById(R.id.recycler_tv_class);
//
                ll.setOnClickListener(this);
            }
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(getAdapterPosition(), v);
            }
        }


        public StudentAdapter(ArrayList<Student> arrayList) {
            studentArrayList = arrayList;
        }


        @Override
        public StudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_text_view, parent, false);

            MyViewHolder vh = new MyViewHolder(linearLayout);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Student s = studentArrayList.get(position);
            holder.textName.setText(s.getName());
            holder.textRollno.setText(s.getRollno());
            holder.textClass.setText(s.getClasses());

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener=onItemClickListener;
        }
        public void deleteItem(int index) {
            studentArrayList.remove(index);

            notifyItemRemoved(index);




        }
        @Override
        public int getItemCount() {
            return studentArrayList.size();

        }

    }



