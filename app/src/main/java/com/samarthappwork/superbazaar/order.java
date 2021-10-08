package com.samarthappwork.superbazaar;

import java.util.ArrayList;
import java.util.List;

public class order {

    String total ,order_id ,status , address  ,totalmrp ;

    public order() {
    }

    public order(String total, String order_id, String status, String address, String totalmrp) {
        this.total = total;
        this.order_id = order_id;
        this.status = status;
        this.address = address;
        this.totalmrp = totalmrp;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalmrp() {
        return totalmrp;
    }

    public void setTotalmrp(String totalmrp) {
        this.totalmrp = totalmrp;
    }
}
