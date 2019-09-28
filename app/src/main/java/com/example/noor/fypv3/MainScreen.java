package com.example.noor.fypv3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    TextView txtLogout;
    BottomNavigationView bnw;
    Fragment childListmainFragment,notificationsFragment;
    FragmentManager fm=getSupportFragmentManager();
    Fragment active;
    public static APIInterface apiInterfaceForAiModule;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        txtLogout = (TextView) findViewById(R.id.txtlogout);
        txtLogout.setOnClickListener(this);
        bnw = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bnw.setOnNavigationItemSelectedListener(bnwListener);
        childListmainFragment = new childListsMAin();
        notificationsFragment=new notifications();
        apiInterfaceForAiModule = APIClient.getApiClientForAiModule().create(APIInterface.class);

        active=childListmainFragment;


        getSupportFragmentManager().beginTransaction().add(R.id.ideaMainActivityfragmentHolder,notificationsFragment,"notifications").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.ideaMainActivityfragmentHolder,childListmainFragment,"childList").commit();

        getSupportFragmentManager().beginTransaction().hide(notificationsFragment).commit();




    }

    BottomNavigationView.OnNavigationItemSelectedListener bnwListener = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId())
            {
                case R.id.main: {
                    if(fm.findFragmentByTag("childactivites")!=null || fm.findFragmentByTag("addchild")!=null)
                        fm.popBackStack();
                    if(active != childListmainFragment)
                        fm.beginTransaction().hide(active).show(childListmainFragment).commit();
                    else
                        fm.beginTransaction().hide(active).show(childListmainFragment).commit();
                    active = childListmainFragment;

                    break;
                }
//                case R.id.notifications: {
//                    active = notificationsFragment;
//                    if(fm.findFragmentByTag("childactivites")!=null || fm.findFragmentByTag("addchild")!=null)
//                        fm.popBackStack();
//                    fm.beginTransaction().hide(childListmainFragment).show(notificationsFragment).commit();
//
//                    break;
//                }
                /*case R.id.favourites: {
                    fm.beginTransaction().hide(active).show(favs).commit();
                    active = favs;
                    if(fm.findFragmentByTag("comment")!=null)
                        fm.popBackStack();
                    break;
                }
                case R.id.search: {
                    fm.beginTransaction().hide(active).show(search).commit();
                    active = search;
                    if(fm.findFragmentByTag("comment")!=null)
                        fm.popBackStack();
                    break;
                }
                case R.id.pastIdeas: {
                    fm.beginTransaction().hide(active).show(pastIdeas).commit();
                    active = pastIdeas;
                    if(fm.findFragmentByTag("comment")!=null)
                        fm.popBackStack();
                    break;
                }*/
            }
            //getSupportFragmentManager().beginTransaction().hide(fragmentArr[])
            //getSupportFragmentManager().beginTransaction().show(selectedFragment);
            // getSupportFragmentManager().beginTransaction().replace(R.id.ideaMainActivityfragmentHolder,selectedFragment).commit();
            return true;
        }
    };



    @Override
    public void onClick(View view) {
        if(view==txtLogout) {
            getSharedPreferences("login", MODE_PRIVATE).edit().clear().commit();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}
