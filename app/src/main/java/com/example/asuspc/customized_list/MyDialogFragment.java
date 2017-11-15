package com.example.asuspc.customized_list;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDialogFragment extends DialogFragment {
    private EditText et_cus_food; //使用者輸入的食物

    public MyDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {//此方法負責產生Dialog對話框

        //需要透過Inflater讀取fragment.xml內容產生View

        //取得Inflater打氣筒
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //從fragment.xml取得自訂畫面
        //inflate(resource, viewGroup)
        View view = inflater.inflate(R.layout.fragment_my_dialog, null);

        et_cus_food = (EditText) view.findViewById(R.id.cus_food);

        //建立AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)//設定自訂Dialog的View
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cus_food = et_cus_food.getText().toString();//取得食物名稱

                        /*
                        想要 DialogFragment與 MainActivity溝通
                        Fragment需要透過 getActivity() 來取得 Activity 物件
                        再轉型成 MainActivity物件 ,來呼叫 MainActivity方法
                        */

                        btn1_page1Activity btn1page1Activity = (btn1_page1Activity) getActivity();//取得Fragment所在的Activity
                        btn1page1Activity.處理確定(cus_food);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btn1_page1Activity btn1page1Activity = (btn1_page1Activity) getActivity();//取得Fragment所在的Activity
                        btn1page1Activity.處理取消();
                    }
                });

        return builder.create();//回傳建立的Dialog
    }
}
