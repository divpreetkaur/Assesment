package com.example.assignment6.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Helper {

       //checking internet connectivity
        public boolean checkInternetConnectivity(Context context)
        {  int flag=0;
            ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] info = check.getAllNetworkInfo();
            for (int i = 0; i<info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    flag = 1;
                    break;

                }
            }
            if(flag==1)
                return true;
            else
                return false;
        }



}

