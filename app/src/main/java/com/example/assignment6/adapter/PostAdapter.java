package com.example.assignment6.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment6.R;
import com.example.assignment6.model.Posts;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
 private List<Posts> postList=new ArrayList<>();
  private Context context;
    public PostAdapter(Context context,List<Posts> postList) {
        this.context=context;
        this.postList=postList;
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvBody;
        public PostViewHolder(View itemView) {
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.post_tv_title);
            tvBody=(TextView)itemView.findViewById(R.id.post_tv_body);
        }
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_post_view, viewGroup, false);
        return new PostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
          postViewHolder.tvTitle.setText(postList.get(i).getTitle());
          postViewHolder.tvBody.setText(postList.get(i).getBody());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
  public interface myClickListener
  {
      void clickListener(int position);
  }

}
