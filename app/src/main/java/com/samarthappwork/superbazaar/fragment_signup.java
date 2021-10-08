package com.samarthappwork.superbazaar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samarthappwork.superbazaar.activities.MainActivity;
import com.samarthappwork.superbazaar.activities.activity_login;
import com.samarthappwork.superbazaar.objectclasses.customer;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class fragment_signup extends Fragment {

    TextView tv_login ;
    EditText tv_signup_name , tv_signup_number,tv_dateofbirth,tv_housenp,tv_locality , tv_near,otp_signup_field ;
    Button btn_signup_otp , btn_signup ;
    String phone_number, OTP_ID;
    FirebaseAuth mAuth ;

    public fragment_signup() {
        // Required empty public constructor
    }

    public  fragment_signup newInstance(String param1, String param2) {
        fragment_signup fragment = new fragment_signup();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_signup, container, false);
        mAuth = FirebaseAuth.getInstance();

      tv_dateofbirth = view.findViewById(R.id.signup_dob) ;
      tv_housenp = view.findViewById(R.id.signup_house);
      tv_locality = view.findViewById(R.id.signup_locality);
      tv_signup_name = view.findViewById(R.id.signup_name);
      tv_signup_number = view.findViewById(R.id.signup_number);
      btn_signup = view.findViewById(R.id.btn_signup);
      btn_signup_otp = view.findViewById(R.id.btn_signup_otp);
      tv_near = view.findViewById(R.id.signup_neat);
      otp_signup_field = view.findViewById(R.id.signup_otpfield) ;
      tv_login = view.findViewById(R.id.tv_login);
      tv_login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             startActivity(new Intent(getActivity(), activity_login.class));
          } });

      btn_signup_otp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              phone_number = "+91" + tv_signup_number.getText().toString() ;
              //btn_getotp.setText("Login");
              isUser();
          }
      });

      btn_signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP_ID,otp_signup_field.getText().toString()) ;
              signInWithPhoneAuthCredential( credential);
          }
      });

        return view ;
    }

    public void intiateotp(){


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone_number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
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
                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

//                            startActivity(new Intent( getContext() , MainActivity.class));
                            upload_data() ;
                            Toast.makeText(getContext(),"you signup successfully, Go back to login",Toast.LENGTH_SHORT).show();

                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI

                            Toast.makeText(getContext(),"Signin code error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void upload_data() {

        customer newuser_data = new customer(tv_signup_name.getText().toString(),tv_signup_number.getText().toString(),
                tv_housenp.getText().toString(),tv_locality.getText().toString(),tv_near.getText().toString(),
                "MRN"+tv_signup_number.getText().toString(),"0","0"
        );

        DatabaseReference firebase_reference = FirebaseDatabase.getInstance().getReference("customers");

        firebase_reference.child(newuser_data.getCustomer_id()).setValue(newuser_data);

    }

    private void isUser(){

        // TODO : turn this feature on

        String userenterenphoneno = "MRN" + tv_signup_number.getText().toString().trim();

        DatabaseReference databaseReferencevalue = FirebaseDatabase.getInstance().getReference("customers");

        Query checkUser = databaseReferencevalue.orderByKey().equalTo(userenterenphoneno);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    tv_signup_number.setError(null);

                    //customer namefromdb = snapshot.child(userenterenphoneno).getValue(customer.class);
                    //loggined_user_details.setCustomer(namefromdb);

                    Toast.makeText(getContext(),"This user already exist ,Please login",Toast.LENGTH_SHORT).show();

                }
                else {
                    intiateotp();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}