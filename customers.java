
package com.mycompany.cars;
import java.util.*;


public class customers extends cars {
    
    int id;
     static final HashMap <Integer,customers> h=new HashMap<>(); 
    String name=new String();
    String car_model;
    Scanner sd=new Scanner(System.in);
    ArrayList<String> carname=new ArrayList<>();
    ArrayList<String> carlist=new ArrayList<>();

    String cars=new String();
 
    customers()
    {
        
    }
    customers(int i,String n,String car_name,String car_model)
    {
        id=i;
        name=n;
       
        
        carname.add(car_name);
        carlist.add(car_model);
        
    }
   void insert(int counter, customers c)
   {
                h.put(id,c);
            System.out.println("Your Id is "+counter);
                  
   }
    
   
    void addnewcar(int ID,String new_car,String modelofcar)
    {
               if(h.size()==0)
                   System.out.println("NO RECORD FOUND");
               else
               {
           if(h.containsKey(ID))
           {
        customers c3 = h.get(ID);
        
        c3.carname.add(new_car);
        c3.carlist.add(modelofcar);
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
    double resale(String s)
    {
        return 0;
    }
    
    void printalldetails()
    { int i,j;
     Hyundai h1=new Hyundai();
     Maruti m=new Maruti();
     Toyota t=new Toyota();
        for(i=0;i<h.size();i++)
        {
            customers c=h.get(i+1);
            System.out.println(c.id+" "+c.name);
            for(j=0;j<c.carname.size();j++)
            {
                System.out.println(c.carname.get(j)+" car-model: "+c.carlist.get(j)+  " original price: "+setPrice(c.carname.get(j)));
                  if(c.carname.get(j).equals("hyundai"))
                     
                System.out.println("Resale price: "+ h1.resale(c.carname.get(j)));
                 if(c.carname.get(j).equals("maruti"))
                 System.out.println("Resale price: "+ m.resale(c.carname.get(j)));
                 if(c.carname.get(j).equals("toyota"))
                     System.out.println("Resale price: "+ t.resale(c.carname.get(j)));
            }
        }
    }
}
 abstract class cars
{
    int price,car_id;
    String model;
    String car_name;
   
    
  
    double setPrice(String c_name)//price for particular car
    {double car_price=0;
        if(c_name.equals("hyundai"))
          car_price=1000000;
          else if(c_name.equals("toyota"))
              car_price=250000;
          else if(c_name.equals("maruti"))
              car_price=100000;
          return car_price;
    }
    abstract double resale(String s);
    
}
class Hyundai extends cars
{double ans;
double c_price=setPrice("hyundai");
Hyundai()
{
}

Hyundai(int id,String c_name,String car_model)
{
  car_id=id;
  car_name=c_name;
  model=car_model;
  
}
    double resale(String s)
    {double c_price=setPrice("hyundai");
    
       ans=0.40*c_price;
       return ans;
    }

  
  

    
 
  }
class Toyota extends cars
{double ans;

double c_price=setPrice("toyota");
Toyota()
{
    
}
Toyota(int id,String c_name,String car_model)
{
  car_id=id;
  model=car_model;
  car_name=c_name;
  
}
    double resale(String s)
    {double c_price=setPrice("toyota");
       ans=(0.80*c_price);
       return ans;
    }
 
  }
class Maruti extends cars
{double ans;
double c_price=setPrice("maruti");
Maruti()
{
    
}
Maruti(int id,String c_name,String car_model)
{
  car_id=id;
  car_name=c_name;
  model=car_model;
}
    double resale(String s)
    {double c_price=setPrice("maruti");
    
       ans=(0.60*c_price);
       return ans;
    }
 
  }




