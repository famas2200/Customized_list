package com.example.asuspc.customized_list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.asuspc.customized_list.circle_view.LuckPanLayout;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements LuckPanLayout.AnimationEndListener {
    public static ArrayList<CharSequence> cus_food_list = new ArrayList();
    private LuckPanLayout luckPanLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = this.getIntent();
        cus_food_list = intent.getCharSequenceArrayListExtra("cus_food_list");

        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);

        luckPanLayout.setAnimationEndListener(this);
    }

    public void rotation(View view) {
        luckPanLayout.rotate(-1, 100);
    }

    @Override
    public void endAnimation(int position) {
//        Toast.makeText(this, "Position = " + (position + 1) + "," + "午餐吃" + cus_food_list.get(position), Toast.LENGTH_SHORT).show();

//        new AlertDialog.Builder(Main2Activity.this)
//                .setMessage("午餐吃「"+cus_food_list.get(position)+"」好嗎?")
//                .setPositiveButton("好!", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent();
//                        intent.setClass(Main2Activity.this,MainActivity.class);
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("再玩一次", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                }).show();

        final int myposition = position;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(Main2Activity.this,LuckyPan_result.class);
                intent.putExtra("cus_food_list", cus_food_list.get(myposition));
                startActivity(intent);

                overridePendingTransition(R.anim.in_right, R.anim.out_left);
            }
        },600);
    }
}
