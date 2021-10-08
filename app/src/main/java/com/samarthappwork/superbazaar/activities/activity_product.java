package com.samarthappwork.superbazaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samarthappwork.superbazaar.R;
import com.samarthappwork.superbazaar.loggined_user_details;
import com.samarthappwork.superbazaar.objectclasses.customer;
import com.samarthappwork.superbazaar.objectclasses.item;
import com.samarthappwork.superbazaar.objectclasses.sizes;
import com.samarthappwork.superbazaar.offer_adapter;
import com.samarthappwork.superbazaar.sizes_adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_product extends AppCompatActivity {

    TextView product_name ;
    ImageView product_mage ;


    RecyclerView sizes_rv ,recent_rv,similiar_rv;
    ArrayList<sizes> sizes_list  ;
    sizes_adapter sizes_myadapter ;
    offer_adapter recent_myadapter,similiar_myadapter ;
    RecyclerView.LayoutManager layoutManager_recent,layoutManager_similiar,layoutManager_sizes;

    Button addtocart_btn, buynow_btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO to gone view of sizes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //TODO TURN ON BUY NOW OPTION

        //todo dialog box for quantity


        product_name = findViewById(R.id.textView21);
        product_mage = findViewById(R.id.imageView11);

        product_name.setText(getIntent().getStringExtra("name"));
        //product_mage.setImageBitmap(MainActivity.offer_myadapter.bitmap_transfer);
        product_mage.setImageURI(getIntent().getData());

        layoutManager_recent = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager_similiar = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager_sizes = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        sizes_rv = findViewById(R.id.sizes_rv);
        if(getIntent().getStringExtra("sizes") != "0"){
            
            sizes_list= new ArrayList<>();
            // sizes_list.add(new item());

            sizes_rv.setHasFixedSize(true);

            sizes_rv.setLayoutManager(layoutManager_sizes);

            display_sizes(getIntent().getStringExtra("Id"));
        }
        else{ sizes_rv.setVisibility(View.GONE);}

        buynow_btn = findViewById(R.id.buynow_btn);
        addtocart_btn = findViewById(R.id.addtocart_btn);
        buynow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addtocart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item item_added = new item();
                item_added.setQuantity("1");
                item_added.setMrp(getIntent().getStringExtra("mrp"));
                item_added.setSp(getIntent().getStringExtra("sp"));
                item_added.setSizes(getIntent().getStringExtra("sizes"));
                item_added.setWeight(getIntent().getStringExtra("weight"));
                item_added.setName(getIntent().getStringExtra("name"));
                item_added.setId(getIntent().getStringExtra("Id"));


                    FirebaseDatabase rootnode ;
                    DatabaseReference reference_cart,reference;
                    rootnode =FirebaseDatabase.getInstance();
                    reference_cart = rootnode.getReference("cart").child(loggined_user_details.getCustomer().getCustomer_id());


                    update_amount();
                    loggined_user_details.setCart_total(String.valueOf(Integer.parseInt(loggined_user_details.getCart_total())+ Integer.parseInt(item_added.getSp())));
                    loggined_user_details.setCart_mrp_total(String.valueOf(Integer.parseInt(loggined_user_details.getCart_mrp_total())+ Integer.parseInt(item_added.getMrp())));

                    reference_cart.child(item_added.getId()).setValue(item_added);

                    reference = rootnode.getReference("customers").child(loggined_user_details.getCustomer().getCustomer_id());
                    reference.child("total_amount").setValue(loggined_user_details.getCart_total());
                    reference.child("total_mrp_amount").setValue(loggined_user_details.getCart_mrp_total());
                }

        });


        recent_rv = findViewById(R.id.recent_view_rv);
        similiar_rv = findViewById(R.id.similiar_rv);

        // sizes_list.add(new item());

        recent_rv.setHasFixedSize(true);
        similiar_rv.setHasFixedSize(true);
        recent_rv.setLayoutManager(layoutManager_recent);
        similiar_rv.setLayoutManager(layoutManager_similiar);

        display_recentviewed();
        display_similiarItems(getIntent().getStringExtra("category"));



    }

    private void display_sizes(String itemId){

        Toast.makeText(getApplicationContext(),"Customers_list_visible",Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<sizes> options = new FirebaseRecyclerOptions.Builder<sizes>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("sizes").child(itemId)
                        //.equalTo("8319085865p1")
                        , sizes.class).build();

        sizes_myadapter = new sizes_adapter(options);
        sizes_myadapter.startListening();
        sizes_rv.setAdapter(sizes_myadapter);
    }

    private void display_recentviewed(){

        FirebaseRecyclerOptions<item> options = new FirebaseRecyclerOptions.Builder<item>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("recent").child(loggined_user_details.getPhone())
                        //.equalTo("8319085865p1")
                        , item.class).build();

        recent_myadapter = new offer_adapter(options);
       recent_myadapter.startListening();
        recent_rv.setAdapter(recent_myadapter);
    }

    private void display_similiarItems(String category){
        //Toast.makeText(getApplicationContext(),"Customers_list_visible",Toast.LENGTH_SHORT).show();

        // Query search_query = FirebaseDatabase.getInstance().getReference().child("customers");

        FirebaseRecyclerOptions<item> options = new FirebaseRecyclerOptions.Builder<item>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("items").child(category)
                        //.equalTo("8319085865p1")
                        , item.class).build();

        similiar_myadapter = new offer_adapter(options);
        similiar_myadapter.startListening();
     similiar_rv.setAdapter(similiar_myadapter);
    }

    private void update_amount(){

        // TODO : turn this feature on

        String userenterenphoneno = "MRN" + loggined_user_details.getPhone();
        DatabaseReference databaseReferencevalue = FirebaseDatabase.getInstance().getReference("customers");
        Query checkUser = databaseReferencevalue.orderByKey().equalTo(userenterenphoneno);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    customer namefromdb = snapshot.child(userenterenphoneno).getValue(customer.class);
                    loggined_user_details.setCart_mrp_total(namefromdb.getTotal_mrp_amount());
                    loggined_user_details.setCart_total(namefromdb.getTotal_amount());

                }
                else {
                    loggined_user_details.setCart_mrp_total("0");
                    loggined_user_details.setCart_total("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}