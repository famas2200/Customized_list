package com.example.asuspc.customized_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class btn2_page3Activity extends AppCompatActivity {
    public static final String Q3_ANSWER_KEY="Q3";
    private CharSequence m_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btn2_page3);
    }

    public void click(View view) {
        m_answer=view.getTag().toString();

        Intent in=new Intent(this,btn2_page4Activity.class);

        Intent q1Intent=getIntent();
        Intent q2Intent=getIntent();
        CharSequence q1Answer=q1Intent.getStringExtra(btn2_page1Activity.Q1_ANSWER_KEY);
        CharSequence q2Answer=q2Intent.getStringExtra(btn2_page2Activity.Q2_ANSWER_KEY);
        CharSequence q3Answer=m_answer;
        in.putExtra(btn2_page1Activity.Q1_ANSWER_KEY,q1Answer);
        in.putExtra(btn2_page2Activity.Q2_ANSWER_KEY,q2Answer);
        in.putExtra(btn2_page3Activity.Q3_ANSWER_KEY,q3Answer);
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
