package com.samarthappwork.superbazaar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.StorageReference;
import com.samarthappwork.superbazaar.activities.activity_product;
import com.samarthappwork.superbazaar.objectclasses.item;
import com.samarthappwork.superbazaar.objectclasses.sizes;

public class sizes_adapter extends FirebaseRecyclerAdapter<sizes, com.samarthappwork.superbazaar.sizes_adapter.sizes_viewholder> {

       int selected_size = 0 ;

        public sizes_adapter(@NonNull FirebaseRecyclerOptions<sizes> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull sizes_viewholder holder, int position, @NonNull sizes model) {

            // TODO THIS IS add image

            holder.mrp.setText("Rs "+model.getMrp());
            holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.sp.setText("Rs " + model.getSp());
            holder.weight.setText(model.getWeight());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().setTheme(R.style.Theme_SuperBazaar_SELECTEDSIZE);
                }
            });


        }

        @NonNull
        @Override
        public sizes_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_layout,parent,false);
            return new sizes_viewholder(view);
        }

        class sizes_viewholder extends RecyclerView.ViewHolder{

            TextView mrp , sp , weight ;

            StorageReference mstorgerefrence_post, mstorgerefrence_profile ;
            public sizes_viewholder(@NonNull View itemView){
                super(itemView);

                mrp = itemView.findViewById(R.id.small_mrp);
                sp = itemView.findViewById(R.id.small_item_sp);
                weight = itemView.findViewById(R.id.small_weight);

            }
        }

}
