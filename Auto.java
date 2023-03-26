package com.company;

import java.text.SimpleDateFormat;
import java.util.*;

public class Auto   {
    private String brand;
    private String model;
    private Date data;
    private int price;
    private static ArrayList<Auto> arr_list = new ArrayList<>();


    public Auto(){
        this.brand = "None";
        this.model = "None";
        this.price = 0;
        this.data = Calendar.getInstance().getTime();
    }

    public Auto( String _brand_, String _model_ , int _price_, Date _date_ ){
        this.brand = _brand_;
        this.model = _model_;
        this.price = _price_;
        this.data = _date_;
    }

    public static void AddAuto ( Auto auto){
        Auto.arr_list.add(auto);
    }
    public static Auto GetAuto (int index){
        return Auto.arr_list.get(index);
    }
    public static void DeleteAuto (int index){
        Auto.arr_list.remove(index);
    }


    public  String GetBrand(){
        return this.brand;
    }
    public  String GetModel(){
        return this.model;
    }
    public String GetPrice(){
        return String.valueOf(this.price);
    }
    public Date GetDate(){
        return this.data;
    }


    public void SetBrand(String brand_){
        this.brand = brand_;
    }
    public void SetModel(String model_){
        this.model = model_;
    }
    public void SetPrice(String price_){
        this.price = Integer.parseInt(price_);
    }
    public void SetDate(Date date_){
        this.data = date_;
    }


    public static int Get_Size_ArrList_Auto(){
        return Auto.arr_list.size();
    }


    public static String Min_Max_Price_Car(){
        int min = Auto.arr_list.get(0).price;
        Auto min_auto = new Auto ();
        int max = 0;
        String str;
        Auto max_auto = new Auto();
        SimpleDateFormat formatted = new SimpleDateFormat("dd.MM.yyyy");

        for (int i = 0; i<Auto.arr_list.size(); i++){

            if (Auto.arr_list.get(i).price <= min){
                min = Auto.arr_list.get(i).price;
                min_auto = Auto.arr_list.get(i);
            }

            if (Auto.arr_list.get(i).price >= max){
                max = Auto.arr_list.get(i).price;
                max_auto = Auto.arr_list.get(i);
            }

        }


        str = ("Мінімальна ціна авто (USD) : " + min_auto.price+
                "\nІнформація про цю автівку : \nМарка: "+min_auto.brand+"\nМодель: "+min_auto.model+ "\nЦіна (USD) : "
                +min_auto.price+"\nДата поставки: "+formatted.format(min_auto.data) +"\n\nMаксимальна ціна авто (USD) : " + max_auto.price+
                "\nІнформація про цю автівку : \nМарка: "+max_auto.brand+"\nМодель: "+max_auto.model+ "\nЦіна (USD) : "
                +max_auto.price+"\nДата поставки: "+formatted.format(max_auto.data)
        );
        return str;
    }



    public static String  Models_in_Brands (){

      ArrayList<ArrayList<Auto >> Brands = new ArrayList<>();
      ArrayList<Auto> acura = new ArrayList<>();
      ArrayList<Auto> audi = new ArrayList<>();
      ArrayList<Auto> bmw = new ArrayList<>();
      ArrayList<Auto> mercedes = new ArrayList<>();

      Brands.add(acura);
      Brands.add(audi);
      Brands.add(bmw);
      Brands.add(mercedes);
      String str,str1,str2;

      for (int i =0; i<Auto.arr_list.size();i++){
          if (Auto.arr_list.get(i).brand.equals("Acura")) acura.add(Auto.arr_list.get(i));
          if (Auto.arr_list.get(i).brand.equals("Audi")) audi.add(Auto.arr_list.get(i));
          if (Auto.arr_list.get(i).brand.equals("BMW")) bmw.add(Auto.arr_list.get(i));
          if (Auto.arr_list.get(i).brand.equals("Mercedes")) mercedes.add(Auto.arr_list.get(i));
      }
      //////////////////////////////////////////////////////


        for (int r=0; r<Brands.size();r++){

            for (int i = 0; i<Brands.get(r).size();i++){

                str1 = Brands.get(r).get(i).brand + Brands.get(r).get(i).model + Brands.get(r).get(i).price;
                for (int j = 0; j<Brands.get(r).size();j++){

                    str2 = Brands.get(r).get(j).brand + Brands.get(r).get(j).model + Brands.get(r).get(j).price;
                    if (i==j)continue;
                    else if (str1.equals(str2))Brands.get(r).remove(j);
                }
            }

        }

        str =("Моделей у бренді Аcura: "+acura.size()+"\nМоделей у бренді Аudi: "+audi.size()+
                "\nМоделей у бренді BMW: "+bmw.size()+"\nМоделей у бренді Mercedes: "+mercedes.size());
            audi.clear();acura.clear();bmw.clear();mercedes.clear();
         return str;
    }


    public static ArrayList<Auto> SearchByDate (Date date_search){
        ArrayList<Auto> auto_search = new ArrayList<>();
        for (int i = 0; i < Auto.arr_list.size(); i++){
            if (date_search.equals(Auto.arr_list.get(i).data)){
                auto_search.add(Auto.arr_list.get(i));
            }
        }
        return auto_search;
     }


}








