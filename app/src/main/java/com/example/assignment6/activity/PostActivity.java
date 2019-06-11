package com.example.assignment6.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment6.R;
import com.example.assignment6.adapter.PostAdapter;
import com.example.assignment6.adapter.UserAdapter;
import com.example.assignment6.interfaces.ApiInterface;
import com.example.assignment6.interfaces.RetrofitBuilder;
import com.example.assignment6.model.Posts;
import com.example.assignment6.model.Users;
import com.example.assignment6.utility.Constants;
import com.example.assignment6.utility.Helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String userId;
    private PostAdapter postAdapter;
    private ProgressBar progressBar;
    private Helper helper;
    private Constants constants;
    private String userName;
    private TextView tvPost,tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        progressBar=(ProgressBar)findViewById(R.id.post_progress_circular);
        helper=new Helper();
        constants=new Constants();
        Intent intent = getIntent();
        userId = intent.getStringExtra(constants.USER_ID);
        userName=intent.getStringExtra(constants.NAME);
        tvName=(TextView)findViewById(R.id.post_tv_name);
        tvPost=(TextView)findViewById(R.id.post_tv);
        tvName.setVisibility(View.INVISIBLE);
        tvPost.setVisibility(View.INVISIBLE);
        tvName.setText(userName);
        getPost(userId);


    }
    @Override
    public void onResume() {

        super.onResume();
        if (helper.checkInternetConnectivity(this))
            getPost(userId);
    }
    //fetching data from api
    public void getPost(final String userId) {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        final ApiInterface apiInterface = retrofitBuilder.getRetrofitApiInterface();
        Call<List<Posts>> call = apiInterface.getPosts(userId);
        if (helper.checkInternetConnectivity(this)) {
            call.enqueue(new Callback<List<Posts>>() {

                @Override
                public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    tvName.setVisibility(View.VISIBLE);
                    tvPost.setVisibility(View.VISIBLE);
                    generateDataList(response.body());

                }

                @Override
                public void onFailure(Call<List<Posts>> call, Throwable t) {
                    Toast.makeText(PostActivity.this,constants.FETCH_DATA_FAILED,Toast.LENGTH_LONG).show();
                }
            });
        } else {
                  progressBar.setVisibility(View.INVISIBLE);
                  createDialogue();

        }
    }
    public void createDialogue()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(constants.INTERNET_CONNECTIVITY)
                .setPositiveButton(constants.TRY_AGAIN, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (helper.checkInternetConnectivity(PostActivity.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            getPost(userId);
                        }
                        else {
                            progressBar.setVisibility(View.INVISIBLE);
                            createDialogue();
                        }
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);

    }


    private void generateDataList(List<Posts> photoList) {
        recyclerView = findViewById(R.id.post_recycler_View);
        postAdapter = new PostAdapter(this, photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(postAdapter);
    }
}