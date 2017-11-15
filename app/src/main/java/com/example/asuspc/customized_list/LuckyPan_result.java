package com.example.asuspc.customized_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LuckyPan_result extends AppCompatActivity {
    private CharSequence cus_food_list;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_pan_result);

        tv_result = (TextView) findViewById(R.id.tv_result);

        Intent intent = this.getIntent();
        cus_food_list = intent.getCharSequenceExtra("cus_food_list");

        tv_result.setText("吃「"+cus_food_list+"」好嗎?");
    }

    public void clickagain(View view) {
        finish();
    }

    public void cliclOK(View view) {
        Intent intent =new Intent();
        intent.setClass(LuckyPan_result.this,MainActivity.class);
        startActivity(intent);
    }
}
