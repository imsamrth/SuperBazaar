package com.samarthappwork.superbazaar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.samarthappwork.superbazaar.activities.Activity_payment;
import com.samarthappwork.superbazaar.objectclasses.coupon;
import com.samarthappwork.superbazaar.objectclasses.item;

public class Activity_recycler_view extends AppCompatActivity {

    RecyclerView offer_rv ;
    cart_adapter offer_myadapter ;
    RecyclerView.LayoutManager layoutManager;
    Toolbar rv_toolbar ;
    TextView bottom_title ,bottom_saved_title;
    String total_amount ;
    loggined_user_details uservalues = (loggined_user_details) this.getApplication();
    Button check_btn ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        offer_rv = findViewById( R.id.rv_activity_cart);
        offer_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        offer_rv.setLayoutManager(layoutManager);
        bottom_title = findViewById(R.id.total_mrp_amount);
        bottom_saved_title = findViewById(R.id.total_amount);
        check_btn = findViewById(R.id.btn_checkout);

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(Activity_recycler_view.this, Activity_payment.class);
                 intent.putExtra("cartValue",offer_myadapter.total_amount);
                 intent.putExtra("total_mrp" ,offer_myadapter.total_mrp_amount);
                 startActivity(intent);
            }
        });

        display_cartItems();

    }

    private void display_cartItems(){

        offer_rv.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(),"Orders_visible",Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<item> options = new FirebaseRecyclerOptions.Builder<item>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("cart").child("MRN8319085865")
                        , item.class).build();

        offer_myadapter = new cart_adapter(options,bottom_title,bottom_saved_title);
        offer_myadapter.startListening();
        offer_rv.setAdapter(offer_myadapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Toast.makeText(this,"you clicked messages",Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }



}