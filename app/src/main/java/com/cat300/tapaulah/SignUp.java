package com.cat300.tapaulah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText edtName, edtPhoneNum, edtEmail, edtPassword, edtConPass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhoneNum = (EditText) findViewById(R.id.edtPhoneNum);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtConPass = (EditText) findViewById(R.id.edtConPass);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        // Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Loading ...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if user  not exist in database
                        if (dataSnapshot.child(edtPhoneNum.getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number Already Register", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            Users user = new Users(edtName.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString());
                            table_user.child(edtPhoneNum.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign Up Successfully !!!", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent category = new Intent(SignUp.this, CategoryMenu.class);
                            startActivity(category);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }



}
