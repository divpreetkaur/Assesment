package com.example.recyclerview;

public class user {

        String name;
        String age;
        String phoneNumber;
        float ratings;
        int likes;

       public user(String username, String userage, String phone) {
           name=username;
           age=userage;
           phoneNumber=phone;
        }

        String getname() {
            return name;
        }

        String getAge() {
            return age;
        }

        String getPhoneNumber() {
            return phoneNumber;
        }
        void setRating(float rating)
        {
            ratings=rating;
        }
    }




