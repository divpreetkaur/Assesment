package com.example.assignment5mvp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String name;
    private String rollno;
    private String cls;
    private String oldRollno;
    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    public Student()
    {}
    public Student(String stuName,String stuRollno,String stuClass)
    {
        name=stuName;
        rollno=stuRollno;
        cls=stuClass;


    }

    public String getName()
    {
        return name;
    }
    public String getRollno()
    {
        return rollno;
    }
    public String getClasses()
    {
        return cls;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rollno);
        dest.writeString(cls);
        dest.writeString(oldRollno);
    }
    protected Student(Parcel in) {
        name = in.readString();
        rollno = in.readString();
        cls = in.readString();
        oldRollno=in.readString();
    }
}
