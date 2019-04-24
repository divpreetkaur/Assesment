
package com.mycompany.cars;
import java.util.*;


public class customers {
    
    int id;
     static final HashMap <Integer,customers> h=new HashMap<>(); 
    String name=new String();
    Scanner sd=new Scanner(System.in);
    ArrayList<String> carname=new ArrayList<>();
    String cars=new String();
 
    customers()
    {
        
    }
    customers(int i,String n,ArrayList<String> a,String car_name)
    {
        id=i;
        name=n;
       
        
        carname.add(car_name);
        
        
    }
   void insert(int counter, customers c)
   {
                h.put(id,c);
            System.out.println("Your Id is "+counter);
                  
   }
    
   
    void addnewcar(int ID,String new_car)
    {
               if(h.size()==0)
                   System.out.println("NO RECORD FOUND");
               else
               {
           if(h.containsKey(ID))
           {
        customers c3 = h.get(ID);
        
        c3.carname.add(new_car);
    }
           else
               System.out.println("Invalid id");
    }
    }
    void display()//displaying names in sorted order with their respective cars
    {int i,count=0;
        TreeMap<String,ArrayList<String>> t =new TreeMap<>();
        if(h.size()==0)
            System.out.println("NO RECORD FOUND");
        else
        {
        for(i=0;i<h.size();i++)
        {  customers c3=h.get(i+1);
            t.put(c3.name,c3.carname);
            
        }
      
            
            
               System.out.println(t);
        
          
    }
    }
    void display_id()//displaying id's and names of customers
    {  int i;
          if(h.size()==0)
              System.out.println("NO RECORD FOUND");
          else
          {
       for(i=0;i<h.size();i++)
       {
      customers c3=h.get(i+1);
       System.out.println(c3.id+" "+c3.name);
     }
    
}
    }
    void prize()//generating prizes for three customers;
    {
        int i,ans,counter=0;
        int t_id;
        ArrayList<Integer> al=new ArrayList<>();
          if(h.size()<6)
              System.out.println("Add more customers");
          else
          {
         
        for(i=0;i<6;i++)
        {
         ans=(int)(Math.random()*10+1);
         al.add(ans);
         
        }
        System.out.println(al);
        System.out.println("enter id's");
        for(i=0;i<3;i++)
        {
        
            t_id=sd.nextInt();
            if(al.contains(t_id))
                counter++;
        }
        System.out.println(counter+"number of people get prize");
    }
}
}
 abstract class cars
{
    int price,car_id;
    String model;
    String car_name;
   
    
  
    int setPrice(String c_name)//price for particular car
    {int car_price=0;
        if(c_name.equals("hyundai"))
          car_price=1000000;
          else if(c_name.equals("toyota"))
              car_price=250000;
          else if(c_name.equals("maruti"))
              car_price=100000;
          return car_price;
    }
    abstract int resale();
    
}
class Hyundai extends cars
{int ans;
int c_price=setPrice("hyundai");
Hyundai(int id,String c_name,String car_model)
{
  car_id=id;
  car_name=c_name;
  model=car_model;
}
    int resale()
    {
       ans=(40*c_price)/100;
       return ans;
    }

  
  

    
 
  }
class Toyota extends cars
{int ans;

int c_price=setPrice("toyota");
Toyota(int id,String c_name,String car_model)
{
  car_id=id;
  model=car_model;
  car_name=c_name;
  
}
    int resale()
    {
       ans=(80*c_price)/100;
       return ans;
    }
 
  }
class Maruti extends cars
{int ans;
int c_price=setPrice("maruti");
Maruti(int id,String c_name,String car_model)
{
  car_id=id;
  car_name=c_name;
  model=car_model;
}
    int resale()
    {
       ans=(60*c_price)/100;
       return ans;
    }
 
  }




