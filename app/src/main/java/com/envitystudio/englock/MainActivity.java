package com.envitystudio.englock;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Switch swtEnglockService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestPermission(); //
        InitView();
        InitListeners();
    }
    private void RequestPermission()
    {
        // check xem may cua nguoi dung co dc cap quyen ko
        // phai check 2 dieu kien lon hon va
        if(Build.VERSION.SDK_INT>=23 )// hoac Build.VERSION_CODES.M))
        {
            // Neu chua dc cap quyen
            if(!Settings.canDrawOverlays(this))
            {
                 /// xin quyền bằng cách bật giao diện
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent,200);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==200)
        {
            // kiểm tra xem người dùng chọn có hay khkoong
            if(Build.VERSION.SDK_INT>=23 )// hoac Build.VERSION_CODES.M))
            {
                // Neu chua dc cap quyen
                if(!Settings.canDrawOverlays(this))
                {
                    /// xin quyền bằng cách bật giao diện
                     finish();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitView()
    {

       swtEnglockService=(Switch)findViewById(R.id.swt_englock_service);
    }
    private void InitListeners()
    {
        // đăng ký lắng nghe sự kiện người dùng chọn chức năng on hoặc off
        swtEnglockService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    // TODO
                    // Kich hoat service
                    Intent intent = new Intent(getBaseContext(),EnglockService.class);
                    startService(intent);
                }
                else
                {
                    // TODO
                    /// Huy kich hoat service
                    Intent intent = new Intent(getBaseContext(),EnglockService.class);
                    stopService(intent);
                }
            }
        });
    }


}
