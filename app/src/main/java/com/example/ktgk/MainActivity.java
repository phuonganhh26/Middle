package com.example.ktgk;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    EditText EditTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPassword2;


    Button button;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteHelper = new SqliteHelper(this);
        initViews();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String name = EditTextName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    if (!sqliteHelper.isEmailExists(Email)) {
                        sqliteHelper.addUser(new Users(null, name, Email, Password));
                        Snackbar.make(button, "User created successfully! Please Login ", Snackbar.LENGTH_LONG).show();
                    }else {
                        Snackbar.make(button, "User already exists with same email ", Snackbar.LENGTH_LONG).show();
                    }
            }
        });
    }


    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        EditTextName = (EditText) findViewById(R.id.name);
        editTextPassword2 = (EditText)  findViewById(R.id.password2);
        button = (Button) findViewById(R.id.button);
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String names = EditTextName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String Password2= editTextPassword2.getText().toString();
        //Handling validation for UserName field
        if (names.isEmpty()) {
            valid = false;
            Toast.makeText(this, "Tên Không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            if (names.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(this, "Tên Không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(this, "Hãy nhập Email", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;

        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(this, "Mật Khẩu Không được để trống", Toast.LENGTH_SHORT).show();

        } else {
            if (Password.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(this, "Mật Khẩu quá ngắn", Toast.LENGTH_SHORT).show();
            }
        }
        if (!Password2.equals(Password)){
            valid = false;
            Toast.makeText(this, "Mật Khẩu Không khớp nhau", Toast.LENGTH_SHORT).show();
        }


        return valid;
    }
}
