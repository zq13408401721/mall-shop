package com.mall.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.bigimage.DataService;
import com.app.bigimage.StudentController;
import com.app.bigimage.beans.StudentBean;
import com.mall.R;

import java.util.List;

public class ServiceActivity extends AppCompatActivity {


    private Button btnGet,btnAdd;
    private TextView txtView;

    StudentController studentController;
    private boolean isConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btnGet = findViewById(R.id.btn_get);
        btnAdd = findViewById(R.id.btn_add);
        txtView = findViewById(R.id.txt_data);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<StudentBean> list = studentController.getStudents();
                    StringBuilder sb = new StringBuilder();
                    for(StudentBean item:list){
                        sb.append(item.getName());
                        sb.append("/n");
                    }
                    txtView.setText(sb.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentBean studentBean = null;
                try {
                    Log.i("process-id",String.valueOf(Process.myPid()));
                    studentController.addStudent(studentBean);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        bindService();
    }

    //隐式启动
    private void bindService(){
        Intent intent = new Intent();
        intent.setAction("com.mall.myservice");
        intent.setPackage(getPackageName());
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        //startService(intent);

        //显示启动
        /*Intent in = new Intent(this,DataService.class);
        startService(in);*/
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            studentController = StudentController.Stub.asInterface(service);
            isConnect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnect = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isConnect){
            unbindService(conn);
        }
    }
}