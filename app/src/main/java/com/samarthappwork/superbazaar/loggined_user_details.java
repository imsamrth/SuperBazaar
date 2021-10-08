package com.samarthappwork.superbazaar;

import android.app.Application;

import com.samarthappwork.superbazaar.objectclasses.customer;

public class loggined_user_details extends Application {

        private static  String notification_count = "0" ;
        private static int islogined = 0;
        private static String name = "" ;
        private static String phone = "" ;
        private static  String Cart_total = "0" ;
        private  static  String cart_mrp_total = "0" ;
        private static customer customer ;

    public static com.samarthappwork.superbazaar.objectclasses.customer getCustomer() {
        return customer;
    }

    public static void setCustomer(com.samarthappwork.superbazaar.objectclasses.customer customer)  {
        loggined_user_details.customer = customer;
    }

    public static String getCart_total() {
        return Cart_total;
    }

    public static void setCart_total(String cart_total) {
        Cart_total = cart_total;
    }

    public static String getCart_mrp_total() {
        return cart_mrp_total;
    }

    public static void setCart_mrp_total(String cart_mrp_total) {
        loggined_user_details.cart_mrp_total = cart_mrp_total;
    }

    public static String getName() {
            return name;
        }

        public static void setName(String name) {
            com.samarthappwork.superbazaar.loggined_user_details.name = name;
        }

        public static String getPhone() {
            return phone;
        }

        public static void setPhone(String phone) {
            com.samarthappwork.superbazaar.loggined_user_details.phone = phone;
        }

        public static String getNotification_count() {
            return notification_count;
        }

        public static void setNotification_count(String notification_count) {
            com.samarthappwork.superbazaar.loggined_user_details.notification_count = notification_count;
        }

        public static int getIslogined() {
            return islogined;
        }

        public static int isIslogined() {
            return islogined;
        }

        public static void setIslogined(int islogined) {
            com.samarthappwork.superbazaar.loggined_user_details.islogined = islogined;
        }

}
