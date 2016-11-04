package com.example.joe.wei.model.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.joe.wei.R;

import cn.bmob.v3.BmobUser;

import static com.example.joe.wei.model.activity.LoginActivity.is_login;

/**
 * Created by Joe on 2016/10/26.
 */



public class SetingActivity extends AppCompatActivity implements View.OnClickListener {

    public View setNew;
    public View setFeedback;
    public View setClear;
    public View setPower;
    public View setAbout;
    public View setLoginOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        setNew = findViewById(R.id.set_new);
        setFeedback = findViewById(R.id.set_feedback);
        setClear = findViewById(R.id.set_clear);
        setPower = findViewById(R.id.set_power);
        setAbout = findViewById(R.id.set_about);
        setLoginOut = findViewById(R.id.set_loginout);
//
        setFeedback.setOnClickListener(this);
        setClear.setOnClickListener(this);
        setPower.setOnClickListener(this);
        setAbout.setOnClickListener(this);
        setNew.setOnClickListener(this);
        setLoginOut.setOnClickListener(this);
    }




    private void toast(String msg){
        Toast.makeText(SetingActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if (v == setLoginOut ){
            BmobUser user = BmobUser.getCurrentUser(this);
            if(user!=null){
//                Intent intent = new Intent(SetingActivity.this, MainActivity.class);
//                startActivity(intent);
                BmobUser.logOut(this);
                toast("退出成功。");
            }else{
                toast("没登录瞎按啥呐。");
            }
        }else if(v == setNew){

        }else if(v == setFeedback){

        }else if(v == setPower){

        }else if(v == setClear){

        }else if(v == setAbout){

        }
    }
}
