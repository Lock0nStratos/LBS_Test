package com.zjt.lbs_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends AppCompatActivity {

    String TAG="Z";
    public LocationClient mLocationClient;

    public BDLocationListener myListener = new MyLocationListener();

    private TextView positontext;

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            StringBuilder sb=new StringBuilder();
            sb.append("纬度：").append(bdLocation.getLatitude()).append("\n");
            sb.append("经度：").append(bdLocation.getLongitude()).append("\n");
            sb.append("定位方式：");
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation){
                sb.append("GPS");
            }else if (bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                sb.append("网络");
            }
            positontext.setText(sb);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        positontext=(TextView) findViewById(R.id.position_text);

        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option=new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        mLocationClient.setLocOption(option);

        mLocationClient.start();
    }
}
