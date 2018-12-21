package zyh.com.yuekao_lianxi02;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;

public class TwoActivity extends AppCompatActivity {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private TextView mLocateText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        initLocation();
        if(Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                }, 100);
            }
        }
        mLocateText = findViewById(R.id.locate_text);
        button = findViewById(R.id.start_locate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.startLocation();
            }
        });

    }

    private void initLocation() {
        //声明AMapLocationClient类对象
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //异步获取定位结果
        AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //解析定位结果
                        mLocateText.setText(amapLocation.getAddress());
//                        mLocationClient.stopLocation();
                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //启动定位
        //可以写在点击事件中
    }
}
