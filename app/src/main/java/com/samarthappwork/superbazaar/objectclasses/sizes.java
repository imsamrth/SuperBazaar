package com.samarthappwork.superbazaar.objectclasses;

public class sizes {

    String mrp,sp,weight ;

    public sizes(String mrp, String sp, String weight) {
        this.mrp = mrp;
        this.sp = sp;
        this.weight = weight;
    }

    public sizes() {
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
