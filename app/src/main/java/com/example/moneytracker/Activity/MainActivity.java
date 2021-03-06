package com.example.moneytracker.Activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.moneytracker.R;
import com.example.moneytracker.RoomDB.Database;
import com.example.moneytracker.ModelClass.SecurityTableModel;
import java.util.List;
import java.util.Objects;
public class MainActivity extends AppCompatActivity  {
    Button login;
    EditText name,password;
    TextView register;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        name=findViewById(R.id.UserNameEDTId);
        password=findViewById(R.id.PasswordID);
        register=findViewById(R.id.register_id);
        database=Database.getInstance(this);


        login=findViewById(R.id.LoginBtnId);

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ContainerList.class).putExtra("Tag","Setting"));
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validet()){
                    database.myDao().getUser().observe(MainActivity.this, new Observer<List<SecurityTableModel>>() {
                        @Override
                        public void onChanged(@Nullable List<SecurityTableModel> securityTableModels) {
                            if (securityTableModels != null && securityTableModels.size()== 0){
                                Toast.makeText(MainActivity.this, "No admin Found", Toast.LENGTH_SHORT).show();
                            }else{
                                String databaseName=securityTableModels.get(0).getUserName();
                                String databasePass=securityTableModels.get(0).getUserPassword();
                                String giveName=name.getText().toString();
                                String givePass=password.getText().toString();

                                if (databaseName.equals(giveName) && databasePass.equals(givePass)){
                                    Intent intent=new Intent(MainActivity.this,DrawerActivity.class).putExtra("Data",securityTableModels.get(0));
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(MainActivity.this,"UserName and Password Not Match",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }
                    }
        });
    }

    private boolean validet() {
        boolean val=true;

        String n=name.getText().toString();
        String p=password.getText().toString();

        if (n.isEmpty() || n.startsWith(" ")|| n.length()<4){
            name.setError("Name Not Valid");
            val=false;
        }
        if (p.isEmpty() || n.startsWith(" ")|| p.length()<6){
            name.setError("Password Not Valid");
            val=false;
        }
        return val;
    }
}
