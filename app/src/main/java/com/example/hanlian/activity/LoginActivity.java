package com.example.hanlian.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.hanlian.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements AMapLocationListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter {

    @BindView(R.id.rg_register_group)
    RadioGroup radioGroup;
    @BindView(R.id.view_vendor)
    View vendorView;
    @BindView(R.id.rb_vendor)
    RadioButton vendorButton;
    @BindView(R.id.view_merchant)
    View merchantView;
    @BindView(R.id.rb_merchant)
    RadioButton merchantButton;

    private AMap aMap;
    private MapView mapView;
    private MyLocationStyle myLocationStyle;

    private String bigAddress;
    private String detailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        initMap();
        initListener();
        mapLocation();
    }

    private void initListener() {
        vendorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,VenderActivity.class));
            }
        });

        //商户注册
        merchantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, BusineActivity.class));
            }
        });
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    //    aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式


        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }


    private ArrayList<String> hslist=new ArrayList<String>();
    private List<Marker> mList = new ArrayList<Marker>();
    private List<infoData> dataList = new ArrayList<infoData>();
    public AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;
    private void mapLocation(){
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        //启动定位
        mlocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = amapLocation.getLatitude();//获取纬度
                double longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息

                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                String country = amapLocation.getCountry();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                String province = amapLocation.getProvince();//省信息
                String city = amapLocation.getCity();//城市信息
                String address = amapLocation.getAddress();
                String district = amapLocation.getDistrict();//城区信息
                String street = amapLocation.getStreet();//街道信息
                String streetNum = amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码

                bigAddress = province + city + district;
                detailAddress = street + streetNum;

//        	Log.e("province",province);
//        	Log.e("city",city);
//        	Log.e("district",district);

                // 缩放地图
                LatLng latLng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

//        	Log.e("address",address);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

            }
        }
    }


    /** API优先回调getInfoWindow ，如果返回的是null，会再回调getInfoContents；
     * 监听自定义infowindow窗口的infowindow事件回调
     */

    @Override
    public View getInfoWindow(Marker marker) {
        final String title = marker.getTitle();

        View infoWindow = getLayoutInflater().inflate(R.layout.item_dialog, null);
        TextView tv_title = (TextView) infoWindow.findViewById(R.id.title);
        tv_title.setText(title);
        TextView tv_detail = (TextView) infoWindow.findViewById(R.id.detail);

        for (int i = 0; i < dataList.size(); i++) {
            infoData infoData = dataList.get(i);

            if(title.equals(infoData.title)){
                tv_detail.setText(infoData.detail);
            }
        }

        infoWindow.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, title, Toast.LENGTH_SHORT).show();
                //跳转到维护界面
                Intent intent = new Intent(LoginActivity.this ,WeihuActivity.class);
                startActivity(intent);
            }
        });

        return infoWindow;
    }


    /**
     *监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
        final String title = marker.getTitle();

        View infoWindow = getLayoutInflater().inflate(R.layout.item_dialog, null);
        TextView tv_title = (TextView) infoWindow.findViewById(R.id.title);
        tv_title.setText(title);
        TextView tv_detail = (TextView) infoWindow.findViewById(R.id.detail);

        for (int i = 0; i < dataList.size(); i++) {
            infoData infoData = dataList.get(i);

            if(title.equals(infoData.title)){
                tv_detail.setText(infoData.detail);
            }
        }

        infoWindow.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        });
        return infoWindow;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();// 显示改点对应 的infowindow

        return false;
    }

    private void setUpMap() {

        hslist.add("28.6508979944,121.4187137676,测试厂家1,测试1");
        hslist.add("28.6509838729,121.4172188215,测试厂家2,测试2");
        hslist.add("28.6499629944,121.4220557676,测试厂家3,测试3");
        hslist.add("28.6497099944,121.4231157676,测试厂家4,测试4");
        hslist.add("28.6494879944,121.4201327676,测试厂家5,测试5");
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

        addMarkers();// 往地图上添加marker
    }


    private void addMarkers() {
        if(hslist!=null){
            for (int i = 0; i < hslist.size(); i++) {
                String[] coordinatearr = convertStrToArray(hslist.get(i));
                if(coordinatearr!=null){
                    if(coordinatearr.length > 3){
                        String longitude=coordinatearr[0];
                        String latitude=coordinatearr[1];
                        String title=coordinatearr[2];
                        String detail=coordinatearr[3];

                        dataList.add(new infoData(title, detail));

                        double dlong=Double.valueOf(longitude).doubleValue();
                        double dlat=Double.valueOf(latitude).doubleValue();

                        MarkerOptions markerOption = new MarkerOptions();
                        LatLng latLng = new LatLng(dlong,dlat);
                        markerOption.position(latLng);
                        markerOption.title(title);

                        //设置Marker图标
                        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(getResources(),R.mipmap.ic_img_denglu)));
                        markerOption.visible(true);
                        Marker addMarker = aMap.addMarker(markerOption);

                    }
                }
            }

        }
    }

    //使用String的split 方法
    public static String[] convertStrToArray(String str){
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }



    class infoData{
        String title;
        String detail;

        public infoData(String title, String detail) {
            super();
            this.title = title;
            this.detail = detail;
        }

    }
}
