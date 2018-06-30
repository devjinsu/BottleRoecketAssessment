package com.example.jinsukim.bottleroeckettest;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jinsukim.bottleroeckettest.adapters.StoreAdapter;
import com.example.jinsukim.bottleroeckettest.models.StoreModel;
import com.example.jinsukim.bottleroeckettest.utils.Helpers;
import com.example.jinsukim.bottleroeckettest.utils.RestTask;
import com.example.jinsukim.bottleroeckettest.utils.SharePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.client.methods.HttpGet;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements StoreAdapter.OnStoreRecyclerViewListener {
    private final String ACTION_FOR_INTENT_CALLBACK_STORE = "ACTION_FOR_INTENT_CALLBACK_STORE";
    private static final int REQUEST_PERMISSION = 1;
    private List<StoreModel> mStoreList;
    private RecyclerView mStoreView;
    private StoreAdapter mStoreAdapter;
    private RelativeLayout mProgressLayout;
    private int mUserLastSelOfPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initUI();
        requestData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ACTION_FOR_INTENT_CALLBACK_STORE));
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    private void initUI() {
        mStoreView = (RecyclerView) findViewById(R.id.rv_store);
        mStoreAdapter = new StoreAdapter(this, mStoreList);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mStoreView.setLayoutManager(layoutManager);

        mStoreView.setAdapter(mStoreAdapter);

        mProgressLayout = (RelativeLayout) findViewById(R.id.rl_progress);
    }

    private void initData() {
        mStoreList = new ArrayList<>();
        mUserLastSelOfPhone = 0;
    }

    private void requestData() {
        try {
            mProgressLayout.setVisibility(View.VISIBLE);
            HttpGet httpGet = new HttpGet(new URI(Helpers.getInstance().getAPIUrl()));
            RestTask task = new RestTask(this, ACTION_FOR_INTENT_CALLBACK_STORE);
            task.execute(httpGet);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mProgressLayout.setVisibility(View.GONE);
            String response = intent.getStringExtra(RestTask.HTTP_RESPONSE);
            Log.i(TAG, "RESPONSE = " + response);
            StoreParser(response);
        }
    };


    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Thanks, Click again to call", Toast.LENGTH_SHORT).show();
            } else {
                // User refused to grant permission.
                Toast.makeText(MainActivity.this, "Need to allow permission for calling", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void StoreParser(String rawData) {
        JSONObject reader = null;
        if (rawData.equals("")) {
            rawData = SharePref.getInstance(MainActivity.this).getStoreData();
            if (rawData.equals("")) {
                Toast.makeText(MainActivity.this, "Not able to connect to server.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Showing result from cached data.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Showing result from online.", Toast.LENGTH_LONG).show();
            SharePref.getInstance(MainActivity.this).saveStoreData(rawData);
        }
        try {
            reader = new JSONObject(rawData);
            JSONArray results = reader.getJSONArray("stores");
            Log.d(TAG, results.toString());

            for (int i = 0; i < results.length(); i++) {
                int id = results.getJSONObject(i).getInt("storeID");
                String name = results.getJSONObject(i).getString("name");
                String phone = results.getJSONObject(i).getString("phone");
                String address = results.getJSONObject(i).getString("address");
                String city = results.getJSONObject(i).getString("city");
                String state = results.getJSONObject(i).getString("state");
                String storeLogoURL = results.getJSONObject(i).getString("storeLogoURL");
                String zipcode = results.getJSONObject(i).getString("zipcode");
                double latitude = results.getJSONObject(i).getDouble("latitude");
                double longitude = results.getJSONObject(i).getDouble("longitude");

                StoreModel store = new StoreModel(id, address, city, state, zipcode, name, phone, latitude, longitude, storeLogoURL);
                mStoreList.add(store);
            }

            mStoreAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStoreItemClickEvent(int position) {
        Intent intent = new Intent(MainActivity.this, StoreDetailActivity.class);
        intent.putExtra(StoreDetailActivity.EXTRA_STORE_DETAIL, mStoreList.get(position));
        startActivity(intent);
    }

    @Override
    public void onStoreCallClickEvent(int position) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PERMISSION);

            return;

        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + mStoreList.get(position).getPhone()));
            startActivity(intent);
        }
    }
}
