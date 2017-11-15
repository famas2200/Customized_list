package com.example.asuspc.customized_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class btn1_page1Activity extends AppCompatActivity {
    private static final String TAG = "btn1_page1Activity";
    public static ArrayList<CharSequence> cus_food_list = new ArrayList();
    public static int position2;
    private SideslipListView mSideslipListView;
    private Button btn_add, btn_go, load;
    private File fname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btn1_page1);

        //設定隱藏標題
        //  getSupportActionBar().hide();

        //設定隱藏狀態
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_go = (Button) findViewById(R.id.btn_luckpan);
        load = (Button) findViewById(R.id.load);
        mSideslipListView = (SideslipListView) findViewById(R.id.sideslipListView);

        mSideslipListView.setAdapter(new CustomAdapter(this));//設置遙控器

        //設置Item點擊事件
        mSideslipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position2 = position;

                if (mSideslipListView.isAllowItemClick()) {
                    new Fragment_Edit().show(getSupportFragmentManager(), "Fragment_Edit");

//                    Log.i(TAG, cus_food_list.get(position) + "被點擊了");
//
//                    Toast.makeText(btn1_page1Activity.this, cus_food_list.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        //設置Item長按事件
//        mSideslipListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mSideslipListView.isAllowItemClick()) {
//                    Log.i(TAG, cus_food_list.get(position) + "被長按了");
//                    Toast.makeText(btn1_page1Activity.this, cus_food_list.get(position), Toast.LENGTH_SHORT).show();
//
//                    return true;//若返回,回傳true表示本次事件被消耗了
//                }
//
//                return false;
//            }
//        });

        if (cus_food_list.size() == 0) {
            cus_food_list.add("目前尚無資料");
        }

        if (cus_food_list.size() < 2) {
            btn_go.setEnabled(false);

            btn_go.setText("最少2個");
        }
    }

    public ArrayList<CharSequence> getmcus_food_list() {
        return cus_food_list;
    }

    //資料儲存
    public void clicksave(View view) {
        new AlertDialog.Builder(btn1_page1Activity.this)
                .setTitle("是否儲存資料?")
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder sb = new StringBuilder();

                        for (CharSequence c : cus_food_list) {
                            sb.append(c + ",");
                        }

                        String path = getFilesDir().getAbsolutePath();
                        fname = new File(path + File.separator + "data.txt");

                        try {
                            FileWriter fw = new FileWriter(fname);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(sb.toString());

                            bw.close();
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        load.setEnabled(true);
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
            }).show();
    }

    public void clickload(View view) {
        new AlertDialog.Builder(btn1_page1Activity.this)
                .setTitle("是否要載入上次的資料?")
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String path = getFilesDir().getAbsolutePath();
                        fname = new File(path + File.separator + "data.txt");

                        cus_food_list.clear();

                        try {
                            FileReader fr = new FileReader(fname);
                            BufferedReader br = new BufferedReader(fr);

                            String str;

                            while ((str = br.readLine()) != null) {
                                String strdata[] = str.split(",");

                                for (String s : strdata) {
                                    if (s.length() > 0) {
                                        cus_food_list.add(s);
                                    }
                                }

                                if (cus_food_list.size() >= 50) {
                                    btn_add.setEnabled(false);

                                    btn_add.setText("最多50個");
                                }

                                if (cus_food_list.size() < 2) {
                                    btn_go.setEnabled(false);

                                    btn_go.setText("最少2個");
                                }

                                if (getmcus_food_list().size() > 1) {
                                    btn_go.setEnabled(true);

                                    btn_go.setText("GO");
                                }

                                CustomAdapter customAdapter = (CustomAdapter) mSideslipListView.getAdapter();
                                customAdapter.notifyDataSetChanged();
                            }

                            br.close();
                            fr.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
            }).show();
    }

    //自定義ListView遙控器
    class CustomAdapter extends BaseAdapter {
        private btn1_page1Activity btn1page1Activity;

        public CustomAdapter(btn1_page1Activity btn1page1Activity) {
            this.btn1page1Activity = btn1page1Activity;
        }

        @Override
        public int getCount() {
            return btn1page1Activity.getmcus_food_list().size();
        }

        @Override
        public Object getItem(int position) {
            return btn1page1Activity.getmcus_food_list().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (null == convertView) {
                convertView = View.inflate(btn1_page1Activity.this, R.layout.item, null);

                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
                viewHolder.textNumber= (TextView) convertView.findViewById(R.id.textNumber);
                viewHolder.txtv_delete = (TextView) convertView.findViewById(R.id.txtv_delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(btn1page1Activity.getmcus_food_list().get(position));

            if (cus_food_list.contains("目前尚無資料")) {
                viewHolder.textNumber.setText("");
            }
            else {
                viewHolder.textNumber.setText(String.valueOf(position+1));
            }

            final int pos = position;

            viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(btn1_page1Activity.this, btn1page1Activity.getmcus_food_list().get(pos) + "被删除了", Toast.LENGTH_SHORT).show();

                    btn1page1Activity.getmcus_food_list().remove(pos);

                    notifyDataSetChanged();

                    if (getmcus_food_list().size() < 50) {
                        btn_add.setEnabled(true);

                        btn_add.setText("新增食物");
                    }

                    if (getmcus_food_list().size() < 2) {
                        btn_go.setEnabled(false);

                        btn_go.setText("最少2個");
                    }

                    if (cus_food_list.size() == 0) {
                        cus_food_list.add("目前尚無資料");
                    }

                    mSideslipListView.turnNormal();
                }
            });

            return convertView;
        }
    }

    class ViewHolder {
        public TextView textView;
        public TextView textNumber;
        public TextView txtv_delete;
    }

    /*
    //新增食物按鈕為方法一:利用主程式的內部類別來實作DialogInterface.OnClickListener方法

    //按下「新增食物」按鈕將出現Dialog對話框的兩個按鈕(確定、取消)給同一個Listenr物件處理
    public void clickAlertDialogAdd(View view) {
        AlertDialogAddListener listener = new AlertDialogAddListener();

        new AlertDialog.Builder(this)
                .setPositiveButton("確定", listener)
                .setNegativeButton("取消", listener)
                .show();
    }

    //方法一的內部類別
    private class AlertDialogAddListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {//which代表Dialog裡哪個按鈕被按下
            switch(which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }
    */

    /*
    //刪除按鈕方法二:利用匿名內部類別直接在個別按鈕內實作程式碼,省去另外寫一個專程去實作的內部類別

    public void clickAlertDialogDelete(View view) {
        new AlertDialog.Builder(this)
                .setPositiveButton("確定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    */


    /*
    //方法三:利用Fragment自訂一個Dialog

    public void clickAlertDialogAdd(View view) {
        //建立自訂的Dialog
        MyDialogFragment dialog = new MyDialogFragment();

        //顯示建立的Dialog
        dialog.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    /*
    DialogFragment與MainActivity溝通
    在MainActivity新增兩個方法給Fragment呼叫分別為:處理確定()、處理取消()
    Fragment則需要透過getActivity()來取得Activity物件
    再轉型成MainActivity物件,來呼叫MainActivity方法
    */
    public void 處理確定(CharSequence cus_food) {
        if (cus_food.toString().trim().length() != 0) {
            cus_food_list.remove("目前尚無資料");

            cus_food_list.add(cus_food);

            CustomAdapter customAdapter = (CustomAdapter) mSideslipListView.getAdapter();

            customAdapter.notifyDataSetChanged();

            if (getmcus_food_list().size() >= 50) {
                btn_add.setEnabled(false);

                btn_add.setText("最多50個");
            }

            if (getmcus_food_list().size() > 1) {
                btn_go.setEnabled(true);

                btn_go.setText("GO");
            }
        }
    }

    public void 處理確定2(CharSequence cus_food) {
        cus_food_list.set(position2, cus_food);

        CustomAdapter customAdapter = (CustomAdapter) mSideslipListView.getAdapter();

        customAdapter.notifyDataSetChanged();
    }

    public static ArrayList<CharSequence> getCus_food_list() {
        return cus_food_list;
    }

    public void 處理取消() {

    }

    public void clickluckpan(View view) {
        final Intent intent = new Intent();
        intent.setClass(btn1_page1Activity.this, Main2Activity.class);
        intent.putExtra("cus_food_list", cus_food_list);
        startActivity(intent);

        overridePendingTransition(R.anim.in_down, R.anim.out_up);

//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(intent);//覆蓋非透明Activity上的透明Activity
//            }
//        });
    }
}
