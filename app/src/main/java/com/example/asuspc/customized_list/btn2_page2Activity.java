package com.example.asuspc.customized_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class btn2_page2Activity extends AppCompatActivity {
    public static final String Q2_ANSWER_KEY="Q2";
    private CharSequence m_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btn2_page2);
    }

    public void click1(View view) {
        m_answer=view.getTag().toString();

        Intent in=new Intent(this,btn2_page3Activity.class);

        Intent q1Intent=getIntent();
        CharSequence q1Answer=q1Intent.getStringExtra(btn2_page1Activity.Q1_ANSWER_KEY);
        CharSequence q2Answer=m_answer;
        in.putExtra(btn2_page1Activity.Q1_ANSWER_KEY,q1Answer);
        in.putExtra(btn2_page2Activity.Q2_ANSWER_KEY,q2Answer);
        startActivity(in);

        overridePendingTransition(R.anim.in_right,R.anim.out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (btn2_page4Activity.isBackPressed == true)
        {
            finish();
        }
    }
}
