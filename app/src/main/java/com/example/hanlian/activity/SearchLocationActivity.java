package com.example.hanlian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.hanlian.R;
import com.example.hanlian.adapter.SearchLocationAdapter;
import com.example.hanlian.utils.KeyConstance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchLocationActivity extends AppCompatActivity {

    @BindView(R.id.iv_search_location_back)
    ImageView back;
    @BindView(R.id.et_search_location)
    EditText searchEdit;
    @BindView(R.id.iv_search_location_search)
    ImageView searchImage;
    @BindView(R.id.map_search_location)
    MapView map;
    @BindView(R.id.rv_ssearch_location)
    RecyclerView recyclerView;

    private MarkerOptions markerOption;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private String bigAddress;
    private String detailAddress;
    private boolean isCameraChange;//是否拖动地图
    private GeocodeSearch geocoderSearch;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String city;
    private List<GeocodeAddress> geocodeAddressList;
    private int mPosition;
    private SearchLocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);

        initListener();
        initMap();
        mapLocation();
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(searchEdit.getText().toString().trim());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //                String code = model.getCode();
        locationAdapter = new SearchLocationAdapter(this, new SearchLocationAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position, Tip model) {
                mPosition = position;
                String name = model.getName();
                String address = model.getAddress();
//                String code = model.getCode();
                Intent intent = new Intent();
                intent.putExtra("bigAddress", address);
                intent.putExtra("detailAddress", name);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(locationAdapter);
    }

    private void mapLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                locationChanged(aMapLocation);
            }
        });
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

    private void locationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息

                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                String province = aMapLocation.getProvince();//省信息
                city = aMapLocation.getCity();//城市信息
//                String country = aMapLocation.getCountry();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                String address = aMapLocation.getAddress();
                String district = aMapLocation.getDistrict();//城区信息
                String street = aMapLocation.getStreet();//街道信息
                String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
                String cityCode = aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码

                bigAddress = province + city + district;
                detailAddress = street + streetNum;

                // 缩放地图
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                markerOption.position(latLng);
                LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
                markerOption.title(city).snippet(latLonPoint.toString());

                markerOption.draggable(false);//设置Marker可拖动
                //设置Marker图标
//	            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//	                .decodeResource(getResources(),R.drawable.location)));
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                markerOption.setFlat(true);//设置marker平贴地图效果
                markerOption.visible(true);

                Marker addMarker = aMap.addMarker(markerOption);
                addMarker.setPositionByPixels(400, 400);

                search(street);
                searchAddress(detailAddress, cityCode);

//	            Log.e("bigAddress",bigAddress);
//	            Log.e("detailAddress",detailAddress);
//	            Log.e("省",province);
//	            Log.e("城市信息",city);
//	            Log.e("城区信息",district);
//	            Log.e("街道信息",street);
//	            Log.e("街道门牌号信息",streetNum);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private void initMap() {
        markerOption = new MarkerOptions();
        if (aMap == null) {
            aMap = map.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.interval(200000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // marker 对象被点击时回调的接口
                // 返回 true 则表示接口已响应事件，否则返回false
                Intent intent = new Intent();
                intent.putExtra("bigAddress", bigAddress);
                intent.putExtra("detailAddress", detailAddress);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });
        // 地图的滑动监听事件
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                isCameraChange = true;
                LatLng target = cameraPosition.target;
                double latitude = target.latitude;
                double longitude = target.longitude;
                Log.e("滑动监听", target.toString());
                LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
                getAddress(latLonPoint);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });
    }

    //搜索
    private void search(String key) {
        InputtipsQuery inputquery = new InputtipsQuery(key, city);
        inputquery.setCityLimit(true);//限制在当前城市
        Inputtips inputTips = new Inputtips(SearchLocationActivity.this, inputquery);
        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (list.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
                locationAdapter.setData(list);
            }
        });
        inputTips.requestInputtipsAsyn();//发送请求
    }

    // 点击搜索的地址，定位到具体位置
    private void searchAddress(String name, String code) {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();

//                String city2 = regeocodeAddress.getCity();
//                String building = regeocodeAddress.getBuilding();
//                String cityCode = regeocodeAddress.getCityCode();
//                String district = regeocodeAddress.getDistrict();
//                String province = regeocodeAddress.getProvince();
//                String adCode = regeocodeAddress.getAdCode();
//                String neighborhood = regeocodeAddress.getNeighborhood();
//                List<RegeocodeRoad> roads = regeocodeAddress.getRoads();
//                String towncode = regeocodeAddress.getTowncode();
                String formatAddress = regeocodeAddress.getFormatAddress();
                String township = regeocodeAddress.getTownship();
                Log.e("formatAddress", formatAddress);
                Log.e("township", township);
                int indexOf = formatAddress.indexOf(township);
                String substring = formatAddress.substring(indexOf, formatAddress.length());
                Log.e("substring", substring);
                if (isCameraChange) {
                    search(substring);
                }
                isCameraChange = false;
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                geocodeAddressList = geocodeResult.getGeocodeAddressList();

                //获取到点击的position
                LatLonPoint latLonPoint = geocodeAddressList.get(mPosition).getLatLonPoint();
                Log.e("latLonPoint", latLonPoint + "");

                LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());


                markerOption.position(latLng);
                markerOption.title(city).snippet(latLonPoint.toString());

                markerOption.draggable(false);//设置Marker可拖动
                //设置Marker图标
//              markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.location)));
                // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                markerOption.setFlat(true);//设置marker平贴地图效果
                markerOption.visible(true);
                Marker addMarker = aMap.addMarker(markerOption);
                addMarker.setPositionByPixels(400, 400);
            }
        });
        Log.e("name", name);
        getLatlon(name);
    }

    /**
     * 响应地理编码,查询经纬度
     */
    public void getLatlon(final String name) {
        GeocodeQuery query = new GeocodeQuery(name, city);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码，查询地址
     */
    //
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    @Override//
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("bigAddress", bigAddress);
            intent.putExtra("detailAddress", detailAddress);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        map.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mlocationClient.stopLocation();
    }

    @Override//
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        map.onSaveInstanceState(outState);
    }
}
