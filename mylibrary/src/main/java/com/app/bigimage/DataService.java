package com.app.bigimage;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.bigimage.beans.StudentBean;

import java.util.ArrayList;
import java.util.List;

public class DataService extends Service {

    List<StudentBean> students;
    List<StudentBean> list;
    StudentController.Stub stub = new StudentController.Stub() {
        @Override
        public List<StudentBean> getStudents() throws RemoteException {
            return students;
        }

        @Override
        public void addStudent(StudentBean student) throws RemoteException {
            Log.i("process:", String.valueOf(Process.myPid()));
            students.add(student);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        students = new ArrayList<>();
        students.add(new StudentBean("小米",20,1));
        students.add(new StudentBean("小黄",19,2));

    }

}
