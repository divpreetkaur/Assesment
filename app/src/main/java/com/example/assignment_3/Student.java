package com.example.assignment_3;

public class Student {
    String name;
    String rollno;
    String cls;
    Student()
    {}
    Student(String stuName,String stuRollno,String stuClass)
    {
        name=stuName;
        rollno=stuRollno;
        cls=stuClass;
    }
    String getName()
    {
        return name;
    }
    String getRollno()
    {
        return rollno;
    }
    String getClasses()
    {
        return cls;
    }
}
