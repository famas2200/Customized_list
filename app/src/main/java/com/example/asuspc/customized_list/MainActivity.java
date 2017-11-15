package com.example.asuspc.customized_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by student on 2017/10/13.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void btn_1(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, btn1_page1Activity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    public void btn_2(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, btn2_page1Activity.class);
        startActivity(intent);

        overridePendingTransition(R.anim.in_right, R.anim.out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btn2_page4Activity.isBackPressed = false;
    }
}
