package com.app.bigimage.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentBean implements Parcelable {
    public StudentBean(String name,int age,int number){
        this.name = name;
        this.age = age;
        this.number = number;
    }

    public StudentBean(Parcel in) {
        name = in.readString();
        age = in.readInt();
        number = in.readInt();
    }

    public static final Creator<StudentBean> CREATOR = new Creator<StudentBean>() {
        @Override
        public StudentBean createFromParcel(Parcel in) {
            return new StudentBean(in);
        }

        @Override
        public StudentBean[] newArray(int size) {
            return new StudentBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private String name;
    private int age;
    private int number;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeInt(number);
    }

    //不加这个编译会报错
    public void readFromParcel(Parcel reply) {
        this.name = reply.readString();
        this.age = reply.readInt();
        this.number = reply.readInt();
    }
}
