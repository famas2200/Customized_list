package com.example.asuspc.customized_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class btn2_page1Activity extends AppCompatActivity {
    public static final String Q1_ANSWER_KEY="Q1";
    private CharSequence m_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btn2_page1);
    }

    public void click(View view) {
        m_answer=view.getTag().toString();

        Intent in=new Intent(this,btn2_page2Activity.class);
        in.putExtra(Q1_ANSWER_KEY,m_answer);
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
