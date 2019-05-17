package com.example.assignment_3;

public class Student {
    String name;
    String rollno;
    String stu_class;
    Student()
    {}
    Student(String stu_name,String stu_rollno,String stu_class)
    {
        name=stu_name;
        rollno=stu_rollno;
        this.stu_class=stu_class;
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
        return stu_class;
    }
}
