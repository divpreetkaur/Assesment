
package com.mycompany.cars;

import java.util.*;


public class project extends customers {
    public static void main(String args[])
    {
        String n;
        String name=new String();
        
        int ID;
        int count=0,countcar=100;
        String car_model=new String();
        ArrayList <String> a=new ArrayList<>();
        ArrayList <String> carmodellist=new ArrayList<>();

        String carname;
        customers c1=new customers();
        ArrayList<Object>add_car=new ArrayList<>();
         
        Scanner sd=new Scanner(System.in);
       
          
          
        System.out.println("choose an option");
        System.out.println("1.ADD NEW CUSTOMER");
        System.out.println("2.ADD NEW CAR");
        System.out.println("3.lIST all customers with their cars sorted by name"); 
        System.out.println("4.List individual customers based on ID");  
        System.out.println("5.Generate prizes for customers");
        System.out.println("6.TO print all details");
        System.out.println("Press 0 to exit");
        n=sd.next();
        while(n.equals("0")==false)
        {
        switch(n)
        {
            case "1"://to add new customer
                
                sd.nextLine();
                
                System.out.println("enter name");
                name=sd.nextLine();
                System.out.println("enter the name of car purchased");
                System.out.println("1.Toyota"+"2.Maruti"+"3.Hyundai");
                carname=sd.nextLine();
                  carname=carname.toLowerCase();
                   if(carname.equals("toyota") || carname.equals("maruti") || carname.equals("hyundai"))
                   {  System.out.println("Enter car model");
                      car_model=sd.nextLine();
              
                     customers c=new customers(++count,name,carname,car_model);
                     c.insert(count, c);
                   }
                   else
                       System.out.println("Invalid carname");
                      break;
                      case "2":// to add new car to existing customer
                int id;
                String new_car=new String();
                System.out.println("enter your id");
                id=sd.nextInt();
                if(h.containsKey(id)==false)//to check if entered key exist
                    System.out.println("Invalid ID");
                else
                {
                sd.nextLine();
               System.out.println("enter name of new car");
               System.out.println("1.Toyota"+"2.Maruti"+"3.Hyundai");
                new_car=sd.next();
                  if(new_car.equals("toyota")|| new_car.equals("maruti") || new_car.equals("hyundai"))
                  {
                      System.out.println("ADD CAR MODEL");
                      car_model=sd.next();
                  
                c1.addnewcar(id,new_car,car_model);//function to add new car
                  }
                  else
                      System.out.println("Invalid carname");
                }
                break;
            case "3":
                c1.display();
                break;
            case "4":
                c1.display_id();
                break;
            case "5":
                c1.prize();
                break;
            case "6":
                c1.printalldetails();
                break;
            default:
                System.out.println("Invalid input");
                
                
                
        }
        System.out.println("choose option");
        n=sd.next();
        }
        
    }
    
    
    
}
