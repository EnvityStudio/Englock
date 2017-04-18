package com.envitystudio.englock;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by thuan on 3/29/2017.
 */

public class LockScreenView extends FrameLayout implements View.OnClickListener {
    //Khai bao layout
    // Duoc dung de anh xa 1 file giao dien .xml
    // thanh 1 doi tuong view cu the la 1 doi tuong framelayout
    private LayoutInflater inflate;
    private TextView txtVi1;
    private TextView txtVi2;
    private TextView txtEn;
    private Database database;
    private Random random;
    private Word[] words;
private  Handler handler;
    public LockScreenView(Context context, Handler handler) {
        super(context);
        // khoi tao inflater
        inflate = LayoutInflater.from(context);
        this.handler = handler;
        // Ánh xạ
        inflate.inflate(R.layout.view_lock_screen, this);
        txtEn = (TextView) findViewById(R.id.txt_en);
        txtVi1 = (TextView) findViewById(R.id.txtvi_1);
        txtVi2 = (TextView) findViewById(R.id.txtvi_2);
        txtVi1.setOnClickListener(this);
        txtVi2.setOnClickListener(this);
        database = new Database();
        database.CopyDatabase(context);
        random = new Random();
        BindData();
    }

    //Gán giá trị cho 3 thuộc tính TextView
    private void BindData() {
        words = database.GetRandoomTwoWord();
        txtEn.setText(words[0].getEn());
        if (random.nextInt(100) < 50) {
            txtVi1.setText(words[0].getVi());
            txtVi2.setText(words[1].getVi());
        } else {
            txtVi1.setText(words[1].getVi());
            txtVi2.setText(words[0].getVi());


        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtvi_1:
                if (txtVi1.getText().equals(words[0].getVi())) {
                    // Mo khoa
                    OpenLockScreen();

                } else {
                    BindData();
                }
                break;


            case R.id.txtvi_2:
                if (txtVi2.getText().equals(words[0].getVi())) {
                    // Mo khoa
                    OpenLockScreen();
                } else {
                    BindData();
                }
                break;
        }
    }
    private void OpenLockScreen()
    {
        handler.sendEmptyMessage(1000);
    }
}
