package com.example.assignment5.model;


        public class Student {
        private String mName;
        private String mRollno;
        private String mCls;
        public Student()
        {}
        public Student(String stuName,String stuRollno,String stuClass)
        {
            mName=stuName;
            mRollno=stuRollno;
            mCls=stuClass;
        }
        public String getName()
        {
            return mName;
        }
        public String getRollno()
        {
            return mRollno;
        }
        public String getClasses()
        {
            return mCls;
        }
    }




