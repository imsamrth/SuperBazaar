package com.samarthappwork.superbazaar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.samarthappwork.superbazaar.activities.MainActivity;
import com.samarthappwork.superbazaar.activities.activity_login;
import com.samarthappwork.superbazaar.activities.activity_product;
import com.samarthappwork.superbazaar.objectclasses.customer;
import com.samarthappwork.superbazaar.objectclasses.item;

import java.util.ArrayList;

public class offer_adapter extends FirebaseRecyclerAdapter<item, offer_adapter.offer_viewholder> {

    private static Bitmap bitmap_transfer ;


        public offer_adapter(@NonNull FirebaseRecyclerOptions<item> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull offer_viewholder holder, int position, @NonNull item model) {

            // TODO THIS IS add image
            holder.name.setText(model.getName());
            holder.mrp.setText("Rs "+model.getMrp());
            holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.sp.setText("Rs " + model.getSp());
            holder.weight.setText(model.getWeight());
            holder.item_image.setImageResource(R.drawable.beverages);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 Intent intent = new Intent(v.getContext(), activity_product.class);

                        intent.putExtra("Id",model.getId());
                    intent.putExtra("name",model.getName());
                    intent.putExtra("mrp",model.getMrp());
                    intent.putExtra("sp",model.getSp());
                    intent.putExtra("sizes",model.getSizes());
                    intent.putExtra("category",model.getCategory());

                    BitmapDrawable drawable = (BitmapDrawable) holder.item_image.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    holder.item_image.buildDrawingCache();


                    add_item_to_recent(model);
                    //bitmap_transfer =holder.item_image.getDrawingCache();




                    //String bitmappath = MediaStore.Images.Media.insertImage(v.getContext().getContentResolver(),bitmap,"post",null);
                    //Uri uri = Uri.parse(bitmappath);
                    //intent.putExtra("path",uri);
                    //intent.setDataAndType(uri,"image");
                    v.getContext().startActivity(intent);
                }
            });
            holder.ADD_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                         add_item_to_cart(model);
                }
            });


        }

        @NonNull
        @Override
        public offer_adapter.offer_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_view_item,parent,false);
            return new offer_viewholder(view);
        }

        class offer_viewholder extends RecyclerView.ViewHolder{
            // ImageView customer_image;
            //comment_button,sharebutton;
            TextView name,mrp , sp , weight ;
            ImageView item_image ;
             Button ADD_button ;
            // ,captiontext;
            StorageReference mstorgerefrence_post, mstorgerefrence_profile ;
            public offer_viewholder(@NonNull View itemView){
                super(itemView);

                //comment_field.setHeight(0);
                //sharebutton= itemView.findViewById(R.id.post_fragment_sharebutton);
                //comment_button=itemView.findViewById(R.id.post_fragment_commentbutton);
                //customer_profile = itemView.findViewById(R.id.iv_customer_profile);
                //postimage = itemView.findViewById(R.id.post_fragment_postimage);
                // likebutton = itemView.findViewById(R.id.post_fragment_likebutton);
                name = itemView.findViewById(R.id.small_item_name);
                mrp = itemView.findViewById(R.id.small_mrp);
               sp = itemView.findViewById(R.id.small_item_sp);
               weight = itemView.findViewById(R.id.small_weight);
               item_image = itemView.findViewById(R.id.small_item_image) ;
               ADD_button = itemView.findViewById(R.id.small_item_add);

                // status_button = itemView.findViewById(R.id.btn_customer_status);
            }
        }

        public void add_item_to_cart(item item){

        item item_added = item;
        item_added.setQuantity("1");

        FirebaseDatabase rootnode ;
        DatabaseReference reference_cart,reference;
        rootnode =FirebaseDatabase.getInstance();
        reference_cart = rootnode.getReference("cart").child(loggined_user_details.getCustomer().getCustomer_id());

        // todo additem snackbar
        update_amount();
        loggined_user_details.setCart_total(String.valueOf(Integer.parseInt(loggined_user_details.getCart_total())+ Integer.parseInt(item_added.getSp())));
        loggined_user_details.setCart_mrp_total(String.valueOf(Integer.parseInt(loggined_user_details.getCart_mrp_total())+ Integer.parseInt(item_added.getMrp())));

        reference_cart.child(item.getId()).setValue(item_added);

        reference = rootnode.getReference("customers").child(loggined_user_details.getCustomer().getCustomer_id());
        reference.child("total_amount").setValue(loggined_user_details.getCart_total());
        reference.child("total_mrp_amount").setValue(loggined_user_details.getCart_mrp_total());
    }


        public void add_item_to_recent(item item){

        item item_added = item;

        FirebaseDatabase rootnode ;
        DatabaseReference reference;
        rootnode =FirebaseDatabase.getInstance();
        reference = rootnode.getReference("recent").child(loggined_user_details.getPhone());
         reference.child(item.getId()).setValue(item_added);
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
                //et_number.setError("No user exist");
              //  et_number.requestFocus();
              //  Toast.makeText(activity_login.this,"hfaskljhasdhf",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
