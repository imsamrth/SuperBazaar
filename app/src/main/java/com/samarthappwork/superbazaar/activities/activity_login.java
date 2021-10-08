package com.samarthappwork.superbazaar.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samarthappwork.superbazaar.R;
import com.samarthappwork.superbazaar.fragment_signup;
import com.samarthappwork.superbazaar.last_acivity_data;
import com.samarthappwork.superbazaar.loggined_user_details;
import com.samarthappwork.superbazaar.objectclasses.customer;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class   activity_login extends AppCompatActivity {

    EditText et_number , et_otp ;
    Button btn_getotp ;
    TextView tv_signup ;
    String phone_number ,OTP_ID ;
    FirebaseAuth mAuth ;
    boolean button_state = true ;
    private static final String LOGIN_DATA = "LOGIN_DATA";
    private  static final String COLUMN_ID = "COLUMN_ID";
    private static final String COLUMN_USERNAME = "COLUMN_USERNAME";
    private static final String COLUMN_PHONE = "COLUMN_PHONE";
    private static final String COLUMN_VALUE = "COLUMN_CARTVALUE";
    private static final String COLUMN_MRPVALUE= "COLUMN_CARTMRPVALUE";
  private static final String COLUMN_IS_LOGIN = "COULUMN_IS_LOGIN";
    last_acivity_data dbHelper ;
    private AdView mAdView;

    private static final String LOGIN_DATABASE = "login.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_number = findViewById(R.id.textInput_username_field);
        et_otp = findViewById(R.id.textInput_OTP_field) ;
        btn_getotp = findViewById(R.id.btn_getotp) ;
        mAuth = FirebaseAuth.getInstance();
        tv_signup = findViewById(R.id.loginactivity_usersignup_text);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        btn_getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_state){
                    phone_number = "+91" + et_number.getText().toString() ;
                    btn_getotp.setText("Login");
                    isUser();
                    button_state = false ;
                }
                else {
                    if(et_otp.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(),"Enter Otp",Toast.LENGTH_SHORT).show();
                    else if(et_otp.getText().toString().length() != 6 )
                        Toast.makeText(getApplicationContext(),"Invalid OTP ",Toast.LENGTH_SHORT).show();
                    else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP_ID,et_otp.getText().toString()) ;
                        signInWithPhoneAuthCredential( credential);
                    }
                    button_state = true ;
                    btn_getotp.setText("get otp");
                }



            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_signup,new fragment_signup())
                        .addToBackStack(null).commit();
            }
        });


    }

   public void intiateotp(){


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull @NotNull String s,
                                                   @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               OTP_ID = s ;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            startActivity(new Intent( activity_login.this , MainActivity.class));
                            finish();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI

                            Toast.makeText(getApplicationContext(),"Signin code error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void isUser(){

        // TODO : turn this feature on

        String userenterenphoneno = "MRN" + et_number.getText().toString();
        DatabaseReference databaseReferencevalue = FirebaseDatabase.getInstance().getReference("customers");
        Query checkUser = databaseReferencevalue.orderByKey().equalTo(userenterenphoneno);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    et_number.setError(null);
                    customer namefromdb;
                    namefromdb = snapshot.child(userenterenphoneno).getValue(customer.class);
                    loggined_user_details.setCustomer(namefromdb);
                    loggined_user_details.setPhone(namefromdb.getNumber());
                    loggined_user_details.setCart_mrp_total(namefromdb.getTotal_mrp_amount());
                    loggined_user_details.setCart_total(namefromdb.getTotal_amount());

                    // TODO have to turn on otp option
                    intiateotp();
                    startActivity(new Intent( activity_login.this , MainActivity.class));
                    Toast.makeText(activity_login.this,namefromdb.getCustomer_id(),Toast.LENGTH_SHORT).show();

                    add(new loggined_user_details());
                    last_acivity_data database =  new last_acivity_data(activity_login.this);
                    if(database.islogined() == 1) Toast.makeText(activity_login.this,namefromdb +" logged in" ,Toast.LENGTH_SHORT).show();


                    finish();
                }
                else {
                    et_number.setError("No such user exist");
                    et_number.requestFocus();
                    Toast.makeText(activity_login.this,snapshot.toString(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                et_number.setError("No user exist");
                et_number.requestFocus();
                Toast.makeText(activity_login.this,"hfaskljhasdhf",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean add(loggined_user_details applicationStatus){
        dbHelper = new last_acivity_data(getApplicationContext());
        SQLiteDatabase login_data = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, 0);
        cv.put(COLUMN_USERNAME, applicationStatus.getName());
        cv.put(COLUMN_PHONE, applicationStatus.getName());
        cv.put(COLUMN_VALUE, applicationStatus.getCart_total());
        cv.put(COLUMN_MRPVALUE, applicationStatus.getCart_mrp_total());
        cv.put(COLUMN_IS_LOGIN,applicationStatus.isIslogined());

        long insert =  login_data.insert(LOGIN_DATA, null, cv);
        return  insert != -1 ;
    }


    //TODO user signup  fragment
}