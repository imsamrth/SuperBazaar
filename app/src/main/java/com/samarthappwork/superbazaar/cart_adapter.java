package com.samarthappwork.superbazaar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.samarthappwork.superbazaar.activities.activity_product;
import com.samarthappwork.superbazaar.objectclasses.customer;
import com.samarthappwork.superbazaar.objectclasses.item;

public class cart_adapter extends FirebaseRecyclerAdapter<item, cart_adapter.cart_viewholder> {

    int total_amount = 0 ,total_mrp_amount = 0  ;
    TextView cart_total, cart_saved ;

    public cart_adapter(@NonNull FirebaseRecyclerOptions<item> options,TextView cart_total,TextView cart_saved ) {
        super(options);
        this.cart_total = cart_total;
        this.cart_saved = cart_saved ;
       // viewgone();
    }



    @Override
    protected void onBindViewHolder(@NonNull cart_viewholder holder, int position, @NonNull item model) {

        // TODO THIS IS add image
        holder.name.setText(model.getName());
        holder.quantity.setText(model.getQuantity());
        holder.mrp.setText("Rs " + model.getMrp());
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.sp.setText("Rs " + model.getSp());
        holder.weight.setText(model.getWeight());

        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_quantity_item_to_cart(model, true,v.getContext());
            }
        });

        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_quantity_item_to_cart(model, false, v.getContext());
            }
        });
        // holder.item_image.setImageResource(Integer.parseInt(model.getImg_location()));

        get_total_amount();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_product.class);

                intent.putExtra("Id", model.getId());
                intent.putExtra("name", model.getName());
                intent.putExtra("mrp", model.getMrp());
                intent.putExtra("sp", model.getSp());
                intent.putExtra("sizes", model.getSizes());
                intent.putExtra("category", model.getCategory());

                BitmapDrawable drawable = (BitmapDrawable) holder.item_image.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                holder.item_image.buildDrawingCache();

                v.getContext().startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_item(model,v.getContext());
            }
        });
    }

    @NonNull
    @Override
    public cart_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_view,parent,false);
        return new cart_viewholder(view);
    }

    class cart_viewholder extends RecyclerView.ViewHolder{

        TextView name,mrp , sp , weight,quantity ;
        ImageView item_image ,img_add,img_remove,delete;

        //todo turn object image feature

        StorageReference mstorgerefrence_post, mstorgerefrence_profile ;
        public cart_viewholder(@NonNull View itemView){
            super(itemView);


            name = itemView.findViewById(R.id.cart_name);
            mrp = itemView.findViewById(R.id.cart_mrp);
            sp = itemView.findViewById(R.id.cart_sp);
            weight = itemView.findViewById(R.id.cart_weight);
            item_image = itemView.findViewById(R.id.cart_image) ;
            quantity = itemView.findViewById(R.id.cart_quantity);
            img_add = itemView.findViewById(R.id.imgbtn_add);
            img_remove = itemView.findViewById(R.id.img_btn_remove);
            delete  = itemView.findViewById(R.id.cart_delete);

        }
    }

        public void get_total_amount(){

            total_amount = 0 ;
            total_mrp_amount = 0  ;
            for (int item_position = 0 ;item_position < getItemCount();item_position++ ){
                total_amount = total_amount + Integer.parseInt(getItem(item_position).getQuantity())* Integer.parseInt(getItem(item_position).getSp()) ;
                total_mrp_amount = total_mrp_amount + Integer.parseInt(getItem(item_position).getQuantity())* Integer.parseInt(getItem(item_position).getMrp()) ;
            }

            cart_saved.setText("Saved Rs "+String.valueOf(total_mrp_amount - total_amount));
            cart_total.setText("Rs "+ String.valueOf(total_amount));


        }


    public void update_quantity_item_to_cart(item item,Boolean ADDED,Context context){

        item item_added = item;
        if (ADDED) {item_added.setQuantity(String.valueOf(Integer.parseInt(item_added.getQuantity())+ 1)); }
        else {
            if (Integer.parseInt(item_added.getQuantity() ) != 1){

                item_added.setQuantity(String.valueOf(Integer.parseInt(item_added.getQuantity()) -1));
                //todo add alert box and delete item
                }
        else {delete_item(item,context);}}

        FirebaseDatabase rootnode ;
        DatabaseReference reference_cart,reference;
        rootnode =FirebaseDatabase.getInstance();
        reference_cart = rootnode.getReference("cart").child(loggined_user_details.getCustomer().getCustomer_id());

        reference_cart.child(item.getId()).setValue(item_added);

        get_total_amount();

        reference = rootnode.getReference("customers").child(loggined_user_details.getCustomer().getCustomer_id());

        reference.child("total_amount").setValue(String.valueOf(total_amount));
        reference.child("total_mrp_amount").setValue(String.valueOf(total_mrp_amount));

    }

    public void viewgone(){
        this.cart_saved.setVisibility(View.GONE);
        this.cart_total.setVisibility(View.GONE);
    }

    public  void delete_item(item itemdelete, Context context){
        CharSequence options[]=new CharSequence[]{
                // select any from the value
                "Delete",
                "Cancel",
        };
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete this Item");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // if delete option is choosed
//                // then call delete function
//                if(which==0) {
//                    delete(itemdelete.getId());
//                }
//
//            }
//        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(itemdelete.getId());
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void delete(String time) {
        // creating a variable for our Database
        // Reference for Firebase.
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("cart").child(loggined_user_details.getCustomer().getCustomer_id());
        // we are use add listerner
        // for event listener method
        // which is called with query.
        Query query=dbref.child(time);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // remove the value at reference
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}