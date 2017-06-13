package com.example.asad.homebuyerproject;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by hassan on 05/01/2017.
 */

public class Property implements Serializable{
    private String Price,address,dealer,selltype,URL;
    HashMap<String, String> map;
    public Property() {
    }
    public Property(String Price, String address, String Dealer, String selltype, String image,HashMap<String, String> Propertymap) {
        this.Price = Price;
        this.address = address;
        this.dealer = Dealer;
        this.selltype = selltype;
        this.URL = image;
        this.map = Propertymap;
    }

    public String getPrice() {
        return Price;
    }
    public void setPrice(String price) {
        this.Price = price;
    }

    public String getDealer() {
        return dealer;
    }
    public void setDealer(String Dealer) {
        this.dealer = Dealer;
    }

    public String getImage() {
        return URL;
    }
    public void setImage(String Image) {
        this.URL = Image;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String Address) {
        this.address = Address;
    }

    public String getSelltype() {
        return selltype;
    }
    public void setSelltype(String Type) {
        this.selltype = Type;
    }

    public void setPropertyMap(HashMap<String,String> Propertymap) {
        this.map = Propertymap;
    }
    public HashMap<String,String> getPropertyMap() {
        return map;
    }

}
