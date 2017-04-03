package com.envitystudio.englock;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by thuan on 3/29/2017.
 */

public class LockScreenView extends FrameLayout {
    //Khai bao layout
    // Duoc dung de anh xa 1 file giao dien .xml
    // thanh 1 doi tuong view cu the la 1 doi tuong framelayout
    private LayoutInflater inflate;
    public LockScreenView(Context context) {
        super(context);
        // khoi tao inflater
        inflate=LayoutInflater.from(context);
        // Ánh xạ
        inflate.inflate(R.layout.view_lock_screen,this);
    }
}
