package com.samarthappwork.superbazaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.samarthappwork.superbazaar.Activity_recycler_view;
import com.samarthappwork.superbazaar.R;
import com.samarthappwork.superbazaar.The_Slide_items_Pager_Adapter;
import com.samarthappwork.superbazaar.last_acivity_data;
import com.samarthappwork.superbazaar.loggined_user_details;
import com.samarthappwork.superbazaar.objectclasses.item;
import com.samarthappwork.superbazaar.offer_adapter;
import com.samarthappwork.superbazaar.the_slide_item_model_class;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottom_nav_view;
    private List<the_slide_item_model_class> listItems;
    private ViewPager page;
    private TabLayout tabLayout;
    int present_selected_item;

    //TODO CUSOMIZE APP BAR

    RecyclerView offer_rv ,recent_rv;
    ArrayList<item> offer_list  ;
    offer_adapter offer_myadapter , recent_myadapter ;
    RecyclerView.LayoutManager layoutManager;


    //TODO Warning: Ads may be preloaded by the Mobile Ads SDK or mediation partner SDKs upon
    // calling MobileAds.initialize(). If you need to obtain consent from users in the European
    // Economic Area (EEA), set any request-specific flags (such as tagForChildDirectedTreatment
    // or tag_for_under_age_of_consent), or otherwise take action before loading ads, ensure you
    // do so before initializing the Mobile Ads SDK.






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = findViewById(R.id.my_pager) ;
        tabLayout = findViewById(R.id.my_tablayout);

        listItems = new ArrayList<>() ;
        listItems.add(new the_slide_item_model_class(R.drawable.item1,"Slider 1 Title"));
        listItems.add(new the_slide_item_model_class(R.drawable.item2,"Slider 2 Title"));
        listItems.add(new the_slide_item_model_class(R.drawable.item3,"Slider 3 Title"));
        listItems.add(new the_slide_item_model_class(R.drawable.item4,"Slider 4 Title"));
        listItems.add(new the_slide_item_model_class(R.drawable.item5,"Slider 5 Title"));

        The_Slide_items_Pager_Adapter itemsPager_adapter = new The_Slide_items_Pager_Adapter(this, listItems);
        page.setAdapter(itemsPager_adapter);

        tabLayout.setupWithViewPager(page,true);

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new The_slide_timer(),2000,3000);

        offer_rv = findViewById(R.id.main_rv_1);
        offer_list= new ArrayList<>();
        offer_list.add(new item());

        offer_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        offer_rv.setLayoutManager(layoutManager);

        display_customers();

        recent_rv = findViewById(R.id.recent_view_rv);
        // sizes_list.add(new item());

        recent_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recent_rv.setLayoutManager(layoutManager);

        display_recentviewed();

        bottom_nav_view = this.findViewById(R.id.bottom_nav_view);
        bottom_nav_view.setSelectedItemId(R.id.bottom_nav_menu_home);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                switch (item.getItemId()) {
                    case R.id.bottom_nav_menu_home:
                        if(present_selected_item != item.getItemId())
                            // home_activty_parent_layout.addView(home_activity_toolbar,0);
                            //   temp_fragment = new home_fragment();
                            //  present_selected_item = item.getItemId();
                            //   getSupportFragmentManager().beginTransaction().replace(R.id.post_container, temp_fragment).commit();
                            present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_nav_menu_category:

                        Intent intent_search = new Intent(MainActivity.this, Activity_category.class);
                        startActivity(intent_search);
                        break;
                    case R.id.bottom_nav_menu_shopping:

                        // TODO MAKE IT UNRESPONSIVE

                        Intent intent_addpost = new Intent( MainActivity.this, Activity_recycler_view.class);
                        startActivity(intent_addpost);
                        break;
                    case R.id.bottom_nav_menu_profile:
                        Intent intent_notification = new Intent( getApplicationContext(), activty_account.class);
                        startActivity(intent_notification);
                        //getSupportFragmentManager().beginTransaction().replace(R.id.post_container, temp_fragment).commit();
                        //home_activty_parent_layout.removeView(home_activity_toolbar);

                        present_selected_item = item.getItemId();
                        break;
                }


                return true;
            }


        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public class The_slide_timer extends TimerTask {
        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (page.getCurrentItem()< listItems.size()-1) {
                        page.setCurrentItem(page.getCurrentItem()+1);
                    }
                    else
                        page.setCurrentItem(0);
                }
            });
        }
    }

    private void display_customers(){

        offer_rv.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(),"Customers_list_visible",Toast.LENGTH_SHORT).show();

        // Query search_query = FirebaseDatabase.getInstance().getReference().child("customers");

        FirebaseRecyclerOptions<item> options = new FirebaseRecyclerOptions.Builder<item>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("items")
                        //.equalTo("8319085865p1")
                        , item.class).build();

        offer_myadapter = new offer_adapter(options);
        offer_myadapter.startListening();
        offer_rv.setAdapter(offer_myadapter);
    }



    private void display_recentviewed(){

        recent_rv.setVisibility(View.VISIBLE);

        //Toast.makeText(getApplicationContext(),"Customers_list_visible",Toast.LENGTH_SHORT).show();

        // Query search_query = FirebaseDatabase.getInstance().getReference().child("customers");

        FirebaseRecyclerOptions<item> options = new FirebaseRecyclerOptions.Builder<item>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("recent").child(loggined_user_details.getPhone())
                        //.equalTo("8319085865p1")
                        , item.class).build();

        recent_myadapter = new offer_adapter(options);
        recent_myadapter.startListening();
        recent_rv.setAdapter(recent_myadapter);
    }




}