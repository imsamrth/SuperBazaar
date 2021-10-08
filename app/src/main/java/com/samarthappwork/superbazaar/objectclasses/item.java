package com.samarthappwork.superbazaar.objectclasses;

public class item {
    String name ,id ,img_location , mrp , sp , weight ,quantity,sizes,category ;

    public item() {
    }

    public String getCategory() {
        return category;
    }

    public item(String name, String id, String img_location, String mrp, String sp, String weight, String quantity, String sizes, String category) {
        this.name = name;
        this.id = id;
        this.img_location = img_location;
        this.mrp = mrp;
        this.sp = sp;
        this.weight = weight;
        this.quantity = quantity;
        this.sizes = sizes;
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public item(String name, String id, String img_location, String mrp, String sp, String weight) {
        this.name = name;
        this.id = id;
        this.img_location = img_location;
        this.mrp = mrp;
        this.sp = sp;
        this.weight = weight;
    }

    public item(String name, String id, String img_location, String mrp, String sp, String weight, String quantity) {
        this.name = name;
        this.id = id;
        this.img_location = img_location;
        this.mrp = mrp;
        this.sp = sp;
        this.weight = weight;
        this.quantity = quantity;
    }

    public item(String name, String id, String img_location, String mrp, String sp, String weight, String quantity, String sizes) {
        this.name = name;
        this.id = id;
        this.img_location = img_location;
        this.mrp = mrp;
        this.sp = sp;
        this.weight = weight;
        this.quantity = quantity;
        this.sizes = sizes;
    }


    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public item(String name, String mrp, String sp, String weight) {
        this.name = name;
        this.mrp = mrp;
        this.sp = sp;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_location() {
        return img_location;
    }

    public void setImg_location(String img_location) {
        this.img_location = img_location;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
