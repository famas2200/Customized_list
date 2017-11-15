package com.example.asuspc.customized_list;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class btn2_page4Activity extends AppCompatActivity {
    private static final String MY_API_KEY = "AIzaSyAXOXJTeveEmXOQdzX0ZDIG2CBSv746zPM";

    private static final int REQUEST_FINE_LOCATION = 0;
    private GPSTracker gps;
    private double latitude;
    private double longitude;

    private String hunger;
    private String price;
    private int radius;

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private ArrayList<Map<String, Object>> arrayList = new ArrayList();

    public static boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn2_page4);

        init();
        GPSpermission();
    }

    private void init() {
        Intent q1Intent = getIntent();
        Intent q2Intent = getIntent();
        Intent q3Intent = getIntent();
        CharSequence q1Answer = q1Intent.getStringExtra(btn2_page1Activity.Q1_ANSWER_KEY);
        CharSequence q2Answer = q2Intent.getStringExtra(btn2_page2Activity.Q2_ANSWER_KEY);
        CharSequence q3Answer = q3Intent.getStringExtra(btn2_page3Activity.Q3_ANSWER_KEY);

        if (q1Answer.toString().equals("不餓")) hunger = "輕食";
        if (q1Answer.toString().equals("很餓")) hunger = "大份量";

        if (q2Answer.toString().equals("想吃省一點")) price = "便宜";
        if (q2Answer.toString().equals("錢不是問題")) price = "";

        if (q3Answer.toString().equals("步行")) radius = 500;
        if (q3Answer.toString().equals("騎單車")) radius = 1000;
        if (q3Answer.toString().equals("騎機車")) radius = 1500;

        listView = (ListView) findViewById(R.id.listView);
    }

    private void getGPS() {
        gps = new GPSTracker(this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.d("GPS", latitude + "," + longitude);

            getResult();
        } else {
            gps.showSettingsAlert();
        }
    }

    private void GPSpermission() {
        int permission = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
        } else {
            getGPS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getGPS();
            } else {

            }
        }
    }

    private void getResult() {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + latitude + "," + longitude);
        sb.append("&radius=" + radius);
        sb.append("&types=restaurant");
//        sb.append("&opennow");
        sb.append("&keyword=" + price);
        sb.append("&keyword=" + hunger);
        sb.append("&language=zh-TW");
        sb.append("&key=" + MY_API_KEY);

        Log.d("sb", sb.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(btn2_page4Activity.this);

        StringRequest stringRequest = new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    for (int i = 0; i < jsonObject.getJSONArray("results").length(); i++) {
                        String lat = jsonObject.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                        String lng = jsonObject.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                        String name = jsonObject.getJSONArray("results").getJSONObject(i).getString("name");
                        String rating = "評分:" + jsonObject.getJSONArray("results").getJSONObject(i).getString("rating");
                        String vicinity = jsonObject.getJSONArray("results").getJSONObject(i).getString("vicinity");

                        Location userLoc = new Location("user");
                        userLoc.setLatitude(latitude);
                        userLoc.setLongitude(longitude);

                        Location restaurantLoc = new Location("restaurantLoc");
                        restaurantLoc.setLatitude(Double.valueOf(lat));
                        restaurantLoc.setLongitude(Double.valueOf(lng));

                        Float distance = userLoc.distanceTo(restaurantLoc);

                        int distanceRounded = Math.round(distance);

                        Log.d("JSON", lat + "," + lng + "\n" + name + "\n" + rating + "\n" + vicinity + "\n" + String.valueOf(distance));

                        HashMap<String, Object> hashMap = new HashMap();
                        hashMap.put("lat", lat);
                        hashMap.put("lng", lng);
                        hashMap.put("name", name);
                        hashMap.put("rating", rating);
                        hashMap.put("vicinity", vicinity);
                        hashMap.put("distance", distanceRounded);
                        arrayList.add(hashMap);

                        Collections.sort(arrayList, new Comparator<Map<String, Object>>() {
                            @Override
                            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                                return (int) o1.get("distance") - (int) o2.get("distance");
                            }
                        });
                    }

                    simpleAdapter = new SimpleAdapter(btn2_page4Activity.this,
                            arrayList,
                            R.layout.myitem,
                            new String[]{"name", "rating", "distance", "vicinity"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4});

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            String strLat = arrayList.get(position).get("lat").toString();
                            String strLng = arrayList.get(position).get("lng").toString();
                            String strName = arrayList.get(position).get("name").toString();

                            Uri uri = Uri.parse("geo:" + strLat + "," + strLng + "?q=" + strName);
                            Intent in = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(in);
                        }
                    });

                    listView.setAdapter(simpleAdapter);

                    if (jsonObject.getJSONArray("results") == null) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", error.toString());
            }
        });

        requestQueue.add(stringRequest);

        requestQueue.start();
    }

    public void click(View view) {
        isBackPressed = true;

        finish();
    }
}
