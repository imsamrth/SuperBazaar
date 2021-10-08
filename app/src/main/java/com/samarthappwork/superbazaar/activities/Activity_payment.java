package com.samarthappwork.superbazaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.samarthappwork.superbazaar.R;
import com.samarthappwork.superbazaar.Util.PaymentsUtil;
import com.samarthappwork.superbazaar.loggined_user_details;
import com.samarthappwork.superbazaar.objectclasses.coupon;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

public class Activity_payment extends AppCompatActivity {

    RadioButton deafult_address, new_address ,payment_gpay,payment_upi,payment_wallet,payment_cod ,payment_card;
    int cart_total ,cart_mrp_total , coupon_discount=0 , delevery= 30,
            saved_amount, totalpayable_amount, selected_id_add,selected_id_pay  ;
    boolean iscoupon = false ;
    Button confirm,apply_coupon;

    String coupon_code ;
    coupon coupon_applied ;

    LinearLayout linearLayout_coupon ;

    TextView cart_total_MRP_tv, delevery_tv , saved_tv ,coupon_saved_tv,
            final_total_tv ,name , houseno,near;

    EditText new_locality , new_houseno,new_near;

    private PaymentsClient paymentsClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // casting radiobutton
        deafult_address = findViewById(R.id.radio_default);
        new_address = findViewById(R.id.radio_newaddress);

        payment_card = findViewById(R.id.debit_rb);
        payment_wallet = findViewById(R.id.wallet_rb);
        payment_cod = findViewById(R.id.cod_rb);
        payment_upi = findViewById(R.id.upi_rb);
        payment_gpay = findViewById(R.id.gpay_rb);

        //update on function
        updateRadio_address(deafult_address);
        updateRadio_payment(payment_gpay);

       // gpay_draw = payment_gpay.getCompoundDrawables();

        // casting tv
        cart_total_MRP_tv = findViewById(R.id.cart_MRP_total);
        delevery_tv = findViewById(R.id.payment_deleviry);
        saved_tv = findViewById(R.id.savedonmrp_tv);
        coupon_saved_tv = findViewById(R.id.coupon_discount);
        final_total_tv = findViewById(R.id.fnal_total_tv);

        name = findViewById(R.id.name_address);
        houseno = findViewById(R.id.house_no_address);
        near = findViewById(R.id.near_address);

        // todo put correct error in text input field
        new_locality = findViewById(R.id.signup_locality);
        new_houseno = findViewById(R.id.signup_house);
        new_near = findViewById(R.id.signup_neat);


        // set values
        cart_total = getIntent().getIntExtra("cartValue",0);
        cart_mrp_total = getIntent().getIntExtra("total_mrp",0);
        saved_amount = cart_mrp_total- cart_total ;

        name.setText(loggined_user_details.getName());
        houseno.setText("mig 29" );
        near.setText("loggined_user_details.getCustomer().getNear()");


        CalCULATE_totalpayable_amount();

        cart_total_MRP_tv.setText("Rs "+ cart_mrp_total);
        saved_tv.setText("Rs "+ saved_amount);
        final_total_tv.setText("Rs "+ totalpayable_amount);
        setdelever_coupontv();

        // payment button
        confirm = findViewById(R.id.btn_conform);

        possiblyShowGooglePayButton();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),CheckoutActivity.class);
                startActivity(intent);

            }
        });
    }

    public void radioTapped_address(View view){

         selected_id_add = view.getId();
        if (selected_id_add == R.id.radio_default) updateRadio_address(deafult_address);
        if (selected_id_add == R.id.radio_newaddress) updateRadio_address(new_address);
    }

    public void radioTapped_payment(View view){

         selected_id_pay = view.getId();
        if (selected_id_pay == R.id.cod_rb) updateRadio_payment(payment_cod);
        if (selected_id_pay == R.id.wallet_rb) updateRadio_payment(payment_wallet);
        if (selected_id_pay == R.id.debit_rb) updateRadio_payment(payment_card);
        if (selected_id_pay == R.id.upi_rb) updateRadio_payment(payment_upi);
        if (selected_id_pay == R.id.gpay_rb) updateRadio_payment(payment_gpay);
    }

    private  void updateRadio_address(RadioButton selected){

        deafult_address.setBackgroundResource(R.drawable.radio_off);
        new_address.setBackgroundResource(R.drawable.radio_off);
        selected.setBackgroundResource(R.drawable.radio_on);
    }

    private  void updateRadio_payment(RadioButton selected){

        payment_upi.setBackgroundResource(R.drawable.radio_off);
        payment_wallet.setBackgroundResource(R.drawable.radio_off);
        payment_cod.setBackgroundResource(R.drawable.radio_off);
        payment_card.setBackgroundResource(R.drawable.radio_off);
        payment_gpay.setBackgroundResource(R.drawable.radio_off);
        selected.setBackgroundResource(R.drawable.radio_on);
    }

    private  void CalCULATE_totalpayable_amount(){
        if (iscoupon) {}
        else {
            totalpayable_amount = cart_total + delevery ;
        }
    }

    private  void setdelever_coupontv(){

        // todo turn on apply coupn button
        if (iscoupon){
            delevery_tv.setPaintFlags(delevery_tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            coupon_saved_tv.setText("Rs "+ coupon_discount);
        }
        else{

            delevery_tv.setText("Rs "+ delevery);
            //
            // Gets linearlayout
            LinearLayout layout = findViewById(R.id.coupon_layout);
// Gets the layout params that will allow you to resize the layout
            ViewGroup.LayoutParams params = layout.getLayoutParams();
// Changes the height and width to the specified *pixels*
            params.height = 0;
            layout.setLayoutParams(params);


        }
    }

    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            confirm.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }


}