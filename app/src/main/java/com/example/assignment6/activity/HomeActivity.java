package com.example.assignment6.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment6.R;
import com.example.assignment6.adapter.UserAdapter;
import com.example.assignment6.interfaces.ApiInterface;
import com.example.assignment6.interfaces.RetrofitBuilder;
import com.example.assignment6.model.Users;
import com.example.assignment6.utility.Constants;
import com.example.assignment6.utility.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements UserAdapter.onItemClickListener {
    private RecyclerView recyclerView;
    private List<Users> mUserArrayList;
    private UserAdapter userAdapter;
    private Users users = new Users();
    private TextView tvUserName, tvUserId, tvUserEmail;
    private Button btnShowPosts, btnShowDetails;
    private int getPosition;
    private String userId;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Helper helper;
    private int count;
    private Constants constants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Toast.makeText(HomeActivity.this, constants.CHOOSE_USER, Toast.LENGTH_LONG).show();
                } else {
                    onItemClick(getPosition, v);
                }
            }
        });


        btnShowPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Toast.makeText(HomeActivity.this, constants.CHOOSE_USER, Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(HomeActivity.this, PostActivity.class);
                    intent.putExtra(constants.USER_ID, userId);
                    intent.putExtra(constants.NAME,tvUserName.getText());
                    startActivity(intent);
                }
            }
        });
        getUserName();


    }
    //initialising the views
    protected void init()
    { constants=new Constants();
     helper=new Helper();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
        mUserArrayList = new ArrayList<>();
        count=0;
        imageView=(ImageView)findViewById(R.id.user_iv);
        tvUserName = (TextView) findViewById(R.id.user_tv_username);
        tvUserId = (TextView) findViewById(R.id.user_tv_userid);
        tvUserEmail = (TextView) findViewById(R.id.user_tv_useremail);
        tvUserEmail.setVisibility(View.INVISIBLE);
        tvUserId.setVisibility(View.INVISIBLE);
        tvUserName.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        userAdapter = new UserAdapter(this, mUserArrayList);
        btnShowPosts = (Button) findViewById(R.id.user_btn_showposts);
        btnShowDetails = (Button) findViewById(R.id.user_btn_showDetails);
        btnShowDetails.setVisibility(View.INVISIBLE);
        btnShowPosts.setVisibility(View.INVISIBLE);
        progressBar=(ProgressBar)findViewById(R.id.user_progress_circular);
        userAdapter.setOnItemClickListener(this);
    }

//checking internet connectivity on resume
    @Override
   public void onResume() {

        super.onResume();
        if (helper.checkInternetConnectivity(this))
            getUserName();
    }
    //fetching data from api
    private void getUserName() {
        RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
        final ApiInterface apiInterface = retrofitBuilder.getRetrofitApiInterface();
        Call<List<Users>> call = apiInterface.getAllUsers();


         if(helper.checkInternetConnectivity(this)) {
             call.enqueue(new Callback<List<Users>>() {

                 @Override
                 public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                     progressBar.setVisibility(View.INVISIBLE);
                     tvUserEmail.setVisibility(View.VISIBLE);
                     tvUserId.setVisibility(View.VISIBLE);
                     tvUserName.setVisibility(View.VISIBLE);
                     imageView.setVisibility(View.VISIBLE);
                     btnShowDetails.setVisibility(View.VISIBLE);
                     btnShowPosts.setVisibility(View.VISIBLE);
                     generateDataList(response.body());
                 }


                 @Override
                 public void onFailure(Call<List<Users>> call, Throwable t) {
                     Toast.makeText(HomeActivity.this,constants.FETCH_DATA_FAILED,Toast.LENGTH_LONG).show();

                 }
             });
         }
         else
         {  progressBar.setVisibility(View.INVISIBLE);
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

                        if (helper.checkInternetConnectivity(HomeActivity.this)) {
                            progressBar.setVisibility(View.VISIBLE);
                            getUserName();
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

    //sending the response to adapter
    private void generateDataList(List<Users> photoList) {
        mUserArrayList = photoList;
        recyclerView = findViewById(R.id.recycler_View);
        userAdapter = new UserAdapter(this, photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        userAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position, View view) {
        if (view.getId() == R.id.user_btn_showDetails) {
            //making dialogue for more details
            final Dialog dialog = new Dialog(this);

            dialog.setContentView(R.layout.dialogue_box_layout);
            TextView tvId = (TextView) dialog.findViewById(R.id.dialogue_tv_id);
            tvId.setText(constants.ID+": "+mUserArrayList.get(position).getId());
            TextView tvName = (TextView) dialog.findViewById(R.id.dialogue_tv_name);
            tvName.setText(constants.NAME+": "+mUserArrayList.get(position).getName());
            TextView tvUserName = (TextView) dialog.findViewById(R.id.dialogue_tv_username);
            tvUserName.setText(constants.USER_NAME+": "+mUserArrayList.get(position).getUsername());
            TextView tvEmail = (TextView) dialog.findViewById(R.id.dialogue_tv_email);
            tvEmail.setText(constants.EMAIL+": "+mUserArrayList.get(position).getEmail());
            TextView tvPhone = (TextView) dialog.findViewById(R.id.dialogue_tv_phone);
            tvPhone.setText(constants.USER_PHONE+": "+mUserArrayList.get(position).getPhone());
            TextView tvWebsite = (TextView) dialog.findViewById(R.id.dialogue_tv_website);
            tvWebsite.setText(constants.WEBSITE+": "+mUserArrayList.get(position).getWebsite());
            dialog.show();
        }
        else {
            count++;
            //setting text views
            users = mUserArrayList.get(position);
            getPosition = position;
            tvUserName.setText(users.getName().toString());
            tvUserId.setText(users.getId().toString());
            tvUserEmail.setText(users.getEmail().toString());
            userId = users.getId();
        }
    }
}

