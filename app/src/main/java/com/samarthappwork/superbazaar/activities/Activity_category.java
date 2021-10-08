package com.samarthappwork.superbazaar.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.samarthappwork.superbazaar.R;
import com.samarthappwork.superbazaar.category_item_adapter;
import com.samarthappwork.superbazaar.categoryt_name;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_category extends AppCompatActivity {

    category_item_adapter listAdapter;
    ExpandableListView expListView;
   // List<categoryt_name> listDataHeader;
    ArrayList<categoryt_name> categoryt_nameArrayAdapter ;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // storing string resources into Array
        //String[] adobe_products = getResources().getStringArray(R.array.adobe_products);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new category_item_adapter(this, categoryt_nameArrayAdapter, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        categoryt_nameArrayAdapter.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                categoryt_nameArrayAdapter.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        categoryt_nameArrayAdapter.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        categoryt_nameArrayAdapter.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

    }



//
//        // Binding resources Array to ListAdapter
//
//        category_item_adapter = new category_item_adapter(this,categoryt_nameArrayAdapter,new HashMap<>());
//        listView.setAdapter(category_item_adapter);

    private void prepareListData() {

        listDataChild = new HashMap<String, List<String>>();


        categoryt_nameArrayAdapter = new ArrayList<>();
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.foodgrains,"Foodrains ,Oil & Masala"));
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.bakery,"Bakery ,Cakes & Dairy"));
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.beverages,"Beverages"));
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.snacks,"Snacks & Branded Foods"));
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.beauty,"Beauty & Hygiene"));
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.cleaning,"Cleaning & Household"));
        categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.gourmet,"Gourmet & World Food"));
        //categoryt_nameArrayAdapter.add(new categoryt_name(R.drawable.icecream,"Icecream & Freezed Item"));

        // Adding child data
//        listDataHeader.add(categoryt_nameArrayAdapter.get(0).getName());
//        listDataHeader.add(categoryt_nameArrayAdapter.get(1).getName());
//        listDataHeader.add(categoryt_nameArrayAdapter.get(2).getName());

        // Adding child data
        List<String> BCD = new ArrayList<String>();
        BCD.add("Dairy");
        BCD.add("Breads & Beans");
        BCD.add("Non Dairy");
        BCD.add("Cookies, Rusk & Khari");
        BCD.add("Gourmet Breads");
        BCD.add("Ice Cream & Desserts");
        BCD.add("Bakery Snacks");
        BCD.add("Cakes & Pastries");

        List<String> FOM = new ArrayList<String>();
        FOM.add("Atta, Flours & Sooji");
        FOM.add("Rice & Rice Products");
        FOM.add("Dals & Pulses");
        FOM.add("Organic Staples");
        FOM.add("Edible Oils & Ghee");
        FOM.add("Salt, Sugar & Jaggery");
        FOM.add("Masalas & Spices");
        FOM.add("Dry Fruits");

        List<String> BEV = new ArrayList<String>();
        BEV.add("Coffee");
        BEV.add("Water");
        BEV.add("Energy & Soft Drinks");
        BEV.add("Tea");
        BEV.add("Health Drink, Supplement");
        BEV.add("Fruit Juices & Drinks");

        List<String> GWF = new ArrayList<String>();
        GWF.add("Oils & Vinegar");
        GWF.add("Dairy & Chesse");
        GWF.add("Pasta, Soup & Noodles");
        GWF.add("Snacks, Dry Fruits, Nuts");
        GWF.add("Sauces,Spreads & Dips");
        GWF.add("Cereals & Breakfast");
        GWF.add("Cooking & Baking Needs");
        GWF.add("Choclate & Biscuits");
        GWF.add("Drinks & Beverages");
        GWF.add("Tinned & Processed Food");

        List<String> CH = new ArrayList<String>();
        CH.add("Detergents & Dishwash");
        CH.add("All Purpose Cleaners");
        CH.add("Disposables, Garbage Bag");
        CH.add("Freshners & Repellants");
        CH.add("Mops, Brushes & Scrubs");
        CH.add("Pooja Needs");
        CH.add("Bins & Bathroom Ware");
        CH.add("Party & Festive Needs");
        CH.add("Stationery");
        CH.add("Car & Shoe Care");

        List<String> BH = new ArrayList<String>();
        BH.add("Makeup");
        BH.add("Oral Care");
        BH.add("Feminine Hygiene");
        BH.add("Bath & Hand Wash");
        BH.add("Health & Medicine");
        BH.add("Hair Care");
        BH.add("Men`s Grooming");
        BH.add("Skin Care");
        BH.add("Fragrances & Deos");

        List<String> SBF = new ArrayList<String>();
        SBF.add("Choclates & Candies");
        SBF.add("Noodle,Pasta, Vermicelli");
        SBF.add("Breakfast Cereals");
        SBF.add("Biscuits & Cookies");
        SBF.add("Frozen Veggies & Snacks");
        SBF.add("Snacks & Namkeen");
        SBF.add("Spreads, Sauces, Ketchup");
        SBF.add("Ready To Cook & Eat");
        SBF.add("Pickels & Chutney");
        SBF.add("Indian Mithai");


        listDataChild.put(categoryt_nameArrayAdapter.get(0).getName(), FOM); // Header, Child data
        listDataChild.put(categoryt_nameArrayAdapter.get(1).getName(), BCD);
        listDataChild.put(categoryt_nameArrayAdapter.get(2).getName(), BEV);
        listDataChild.put(categoryt_nameArrayAdapter.get(3).getName(), SBF);
        listDataChild.put(categoryt_nameArrayAdapter.get(4).getName(), BH);
        listDataChild.put(categoryt_nameArrayAdapter.get(5).getName(), CH);
        listDataChild.put(categoryt_nameArrayAdapter.get(6).getName(), GWF);
        //listDataChild.put(categoryt_nameArrayAdapter.get(7).getName(), comingSoon);
    }


}