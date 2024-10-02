 package com.example.nationalparks.controller;

import android.app.Application;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController  extends Application {
    public static AppController instance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance()
    {
        return instance;

    }
    public RequestQueue getRequestQueue()
    {
        if (requestQueue== null)
        {
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
public  <T> void addToRequestQueue(Request<T> request){getRequestQueue().add(request);}

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;


    }
}
