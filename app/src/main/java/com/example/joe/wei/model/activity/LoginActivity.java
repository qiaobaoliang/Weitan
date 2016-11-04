package com.example.joe.wei.model.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.example.joe.wei.R;
import com.example.joe.wei.config.Constants;

/**
 *
 * @class  LoginActivity
 * @author smile
 * @date   2015-6-1 上午11:06:42
 * 注：第三方登陆有如下两种应用场景：
 *
 * 一、第三方账号一键登陆的步骤：
 * 1、先进行授权，开发者需要自行根据第三方平台的提供的授权方法完成授权并得到授权信息
 * 2、之后调用bmob提供的loginWithAuthData方法，并自行构造BmobThirdUserAuth对象，调用成功后，在Bmob的User表中会产生一条记录，其username是以此来实现第三方账号与bmob平台的用户表的关联并完成登陆操作
 *
 * 二、关联第三方账号的步骤：
 * 有时候开发者先完成的是bmob用户的登陆操作，此时想要关联第三方账号，那么步骤如下：
 * 1、先登录bmob的用户（比如用户A）
 * 2、开发者需要自行根据第三方平台的提供的授权方法完成授权并得到授权信息
 * 3、调用associateWithAuthData方法，并自行构造BmobThirdUserAuth对象，调用成功后，你就会在后台的用户A的authData这个字段下面看到提交的授权信息。
 */
public class LoginActivity extends Activity implements OnClickListener{

    EditText et_account,et_pwd;
    TextView btn_login,btn_register;
    public static boolean is_login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        //初始化BmobSDK
        Bmob.initialize(this, Constants.BMOB_APPID);
        initView();
        BmobUser user = BmobUser.getCurrentUser(this);
        if(user!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    public void initView(){
        et_account = (EditText)findViewById(R.id.et_account);
        et_pwd = (EditText)findViewById(R.id.et_pwd);

        btn_login = (TextView)findViewById(R.id.btn_login);
        btn_register = (TextView)findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    String account,pwd;

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {



            case R.id.btn_login://登陆
                //判断是否已经登录
                if(is_login){
                    toast("你已经登陆了别乱点了");
                }else{
                    account = et_account.getText().toString().trim();
                    pwd = et_pwd.getText().toString().trim();
                    if(account.equals("")){
                        toast("填写你的用户名");
                        return;
                    }

                    if(pwd.equals("")){
                        toast("填写你的密码");
                        return;
                    }

                    BmobUser user = new BmobUser();
                    user.setUsername(account);
                    user.setPassword(pwd);
                    user.login(this, new SaveListener() {

                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            toast("登录成功");
                            is_login = true;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            toast("登陆失败："+arg1);
                        }
                    });
                    break;
                }


            case R.id.btn_register://注册
                if(is_login){
                    toast("你已经登陆了退出后再试");
                }else {
                    account = et_account.getText().toString().trim();
                    pwd = et_pwd.getText().toString().trim();
                    if (account.equals("")) {
                        toast("填写你的用户名");
                        return;
                    }
                    if (pwd.equals("")) {
                        toast("填写你的密码");
                        return;
                    }
                    BmobUser u = new BmobUser();
                    u.setUsername(account);
                    u.setPassword(pwd);
//			u.setEmail("123456@qq.com");
                    u.signUp(this, new SaveListener() {


                        @Override
                        public void onSuccess() {
                            toast("注册成功");
                            is_login = true;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int arg0, String arg1) {
                            toast("注册失败：" + arg1);
                        }
                    });
                    break;
                }

            default:
                break;
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调内部函数
    }

    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
