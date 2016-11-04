package com.example.joe.wei.model.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.joe.wei.R;
import com.example.joe.wei.config.Constants;
import com.example.joe.wei.model.service.StepService;
import com.example.joe.wei.widgt.CurveChartView;
import com.example.joe.wei.widgt.StepCircleView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Handler.Callback,NavigationView.OnNavigationItemSelectedListener{
    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL = 500;
    private TextView text_step;
    private Messenger messenger;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Handler delayHandler;
    private StepCircleView stepCircleView;
    private int stepsDegrees=8000;
    private int oldDegree,newDegree;


    private CurveChartView curveChartView;
    private ArrayList<Integer> yList;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
            Message msg = Message.obtain(null, Constants.MSG_FROM_CLIENT);
            msg.replyTo = mGetReplyMessenger;
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constants.MSG_FROM_SERVER:
                // 更新界面上的步数
                text_step.setText(msg.getData().getInt("step") + "");
                //更新界面上的步数圈
                newDegree=msg.getData().getInt("step")*360/stepsDegrees;
                if (oldDegree!=newDegree){
                    stepCircleView.setScanDegrees(newDegree);
                    oldDegree=newDegree;
                }
                delayHandler.sendEmptyMessageDelayed(Constants.REQUEST_SERVER, TIME_INTERVAL);
                break;
            case Constants.REQUEST_SERVER:
                try {
                    Message msg1 = Message.obtain(null, Constants.MSG_FROM_CLIENT);
                    msg1.replyTo = mGetReplyMessenger;
                    messenger.send(msg1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    private void init() {
        curveChartView= (CurveChartView) findViewById(R.id.curve_chart_view);
        setChartData();
        text_step = (TextView) findViewById(R.id.text_step);
        stepCircleView= (StepCircleView) findViewById(R.id.step_circle_view);


        delayHandler = new Handler(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    //红色小信封（系统自带）
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void setChartData() {
        yList = new ArrayList<>();
        yList.add(50);
        yList.add(56);
        yList.add(70);
        yList.add(53);
        yList.add(50);
        yList.add(46);
        yList.add(52);
       curveChartView.setPointsY(yList);

    }


    @Override
    protected void onStart() {
        super.onStart();
        setupService();
    }

    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //        getMenuInflater().inflate(R.menu.main, menu);
    //        点击头像登录注册
        View navTouXiang = findViewById(R.id.nav_touxiang);
        navTouXiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_person) {
            Intent intent = new Intent(MainActivity.this,PersonActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_shop) {
            Intent intent = new Intent(MainActivity.this,XListViewActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rang) {

        } else if (id == R.id.nav_seting) {
            Intent intent = new Intent(MainActivity.this,SetingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
