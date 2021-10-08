package com.samarthappwork.superbazaar.objectclasses;

public class customer {
    String name ,number , house , locality , near , customer_id ,total_amount ,total_mrp_amount;

    public customer() {
    }

    //TODO DELETE THIS CLASS , BECAUSE IT IS OF NO USE and use hashmap


    public customer(String name, String number, String house, String locality, String near, String customer_id, String total_amount, String total_mrp_amount) {
        this.name = name;
        this.number = number;
        this.house = house;
        this.locality = locality;
        this.near = near;
        this.customer_id = customer_id;
        this.total_amount = total_amount;
        this.total_mrp_amount = total_mrp_amount;
    }

    public customer(String name, String number, String house, String locality, String near, String customer_id) {
        this.name = name;
        this.number = number;
        this.house = house;
        this.locality = locality;
        this.near = near;
        this.customer_id = customer_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }



    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_mrp_amount() {
        return total_mrp_amount;
    }

    public void setTotal_mrp_amount(String total_mrp_amount) {
        this.total_mrp_amount = total_mrp_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getNear() {
        return near;
    }

    public void setNear(String near) {
        this.near = near;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
