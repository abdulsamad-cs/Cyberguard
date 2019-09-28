package com.example.noor.fypv3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentGettingStarted;
    Fragment loginFragment;
    SharedPreferences sharedPreferences;
    public static APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentGettingStarted = new fragment_gettingStarted();
        loginFragment = new fragment_login();
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        apiInterface = APIClient.getApiClient().create(APIInterface.class);

        //getSupportFragmentManager().beginTransaction().add(R.id.fragmentholder,fragmentGettingStarted).commit();

        if(sharedPreferences.getBoolean("isLogined",false)==true) {


            //Toast.makeText(this, "first if key: "+getSharedPreferences("login",MODE_PRIVATE).getString("key",null), Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentholder,loginFragment).commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentholder,fragmentGettingStarted).commit();

        }

    }
}
