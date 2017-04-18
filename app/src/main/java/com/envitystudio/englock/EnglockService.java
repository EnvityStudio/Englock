package com.envitystudio.englock;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * Created by thuan on 3/29/2017.
 */
/*
Để sử dụng được service cần 2 bước:
    B1: Đăng ký với hệ thống android
    B2: Kích hoạt/ Hủy kích hoạt

 */

public class EnglockService extends Service {
    // Khai bao thuoc tinh
    private ScreenStateReceiver screenStateReceiver;
    private WindowManager windowManager;
    private LockScreenView lockScreenView;
    // truyền và nhận tin nhắn
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        // phuong thuc dau tien duoc chay khi
        registerScreenStateReceiver();
        DisableKeyguard();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1000)
                {
                    HideLockScreen();
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Tuy chon service tu dong song lai va chay tiep
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        unregisterScreenStateReceiver();
        EnableKeyguard();
        super.onDestroy();
    }

    private void showLockScreenView() {
        // Tao ra doi tuong
        lockScreenView = new LockScreenView(this,handler);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // hien thi
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.TRANSPARENT;
        windowManager.addView(lockScreenView, params);
    }

    // Mo khoa
    private void HideLockScreen() {
        windowManager.removeView(lockScreenView);
    }

    private void DisableKeyguard() {

        // Huy bao ve man hinh thi chung ta moi co the hien thi duoc customView
        KeyguardManager manager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("IN");
        // Tắt màn hình khóa
        lock.disableKeyguard();

    }

    // Kich hoat lai bao ve man hinh
    private void EnableKeyguard() {
        // Huy bao ve man hinh thi chung ta moi co the hien thi duoc customView
        KeyguardManager manager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("IN");
        lock.reenableKeyguard();
    }

    // ĐỐi tượng BroadcastReceiver được dùng để lắng nghe các sự kiện được phát bởi hệ thống
    // Cụ thẻ lắng nghe sự kiện SCREEN_OFF
    private class ScreenStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO
            // Đoạn code được chạy khi màn hình tắt
            //Hiển thị giao diện câu hỏi
            showLockScreenView();


        }
    }

    // dung de dang ky su dung
    private void registerScreenStateReceiver() {
        // TODO
        // khoi tao doi tuong
        screenStateReceiver = new ScreenStateReceiver();
        // Tao bo loc
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        // dang ky
        registerReceiver(screenStateReceiver, intentFilter);


    }

    // dung de huy dang ky
    private void unregisterScreenStateReceiver() {
        unregisterReceiver(screenStateReceiver);
        //TODO
    }
}
