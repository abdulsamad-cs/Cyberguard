package com.example.noor.fypv3;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.Response;

import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_login extends Fragment implements View.OnClickListener {
    EditText email;

    TextView tvSignupLink;
    ImageView imgBackLogin;

    EditText password;
    Button login;
    SharedPreferences sharedPreferences;
    String useremail,userPass="";
    //Signupdb log;
    String mail;
    String pas;
    ProgressBar progressBar;
    //DatabaseReference fb;




    public fragment_login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment_login,container,false);
        progressBar = v.findViewById(R.id.progressBar);
        imgBackLogin = (ImageView) v.findViewById(R.id.imgBackLogin);
        tvSignupLink = (TextView) v.findViewById(R.id.txtSignupLink);
        imgBackLogin.setOnClickListener(this);
        tvSignupLink.setOnClickListener(this);
        email = (EditText) v.findViewById(R.id.email1);
        password = (EditText) v.findViewById(R.id.pass1);
        login = (Button) v.findViewById(R.id.login);
        login.setOnClickListener(this);
        //log = new Signupdb(getActivity());
        mail = email.getText().toString();
        pas = password.getText().toString();
       // fb =FirebaseDatabase.getInstance().getReference("cyberguards/users");


        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        //c = log.readPrefrnceData();
        //Toast.makeText(getActivity(), c.getString(1), Toast.LENGTH_SHORT).show();


        if(sharedPreferences.getBoolean("isLogined",false)==true) {
            Intent in = new Intent(getActivity(), MainScreen.class);
            startActivity(in);
            getActivity().finish();
        }
        return v;
    }
    private void userLogin() {
        //first getting the values
        final String useremail = email.getText().toString();
        final String userpassword = password.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(useremail)) {
            email.setError("Please enter your username");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userpassword)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }
        retrofit2.Call<User> call = MainActivity.apiInterface.performUserLogin(useremail,userpassword);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {
                if(response.body().getResponse().equals("ok"))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Welcome " +response.body().getUsername(), Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor sh=  sharedPreferences.edit();
                    sh.putString("email", useremail);
                    sh.putString("password", userpassword);
                    sh.putBoolean("isLogined",true);
                    //sh.putString("key",student.getKey());
                    sh.commit();
                    //Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getActivity(), MainScreen.class);
                    startActivity(in);
                    getActivity().finish();

                }
                else if (response.body().getResponse().equals("failed"))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Login failed. Try again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {

            }
        });
        //email.setText("");
        //password.setText("");

        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email"),
                                        userJson.getString("password")
                                );

                                //storing the user in shared preferences
                                //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                //starting the profile activity
                                SharedPreferences.Editor sh = sharedPreferences.edit();

                                sh.putInt("key", user.getId());
                                sh.putString("password", user.getPassword());
                                sh.putString("username", user.getName());
                                sh.putString("email", user.getEmail());
                                sh.putBoolean("isLogined", true);
                                sh.commit();
                                Intent i = new Intent(getActivity(), MainScreen.class);
                                startActivity(i);
                                getActivity().finish();

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", useremail);
                params.put("password", userpassword);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);*/
    }


    @Override
    public void onClick(View view) {
       if(view==login)
        {
            userLogin();
         /*   final String user=email.getText().toString();
            final String pas=password.getText().toString();

            Query query= fb.orderByChild("name").equalTo(email.getText().toString());
            // Toast.makeText(getContext(), "query hogya meri ma click", Toast.LENGTH_SHORT).show();
            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Toast.makeText(getActivity(), "inData", Toast.LENGTH_SHORT).show();
                    // print each k/v pair as a log message
                    for (DataSnapshot student : dataSnapshot.getChildren()) {
                        if(user.equals(student.child("userName").getValue()) && pas.equals(student.child("password").getValue()))
                        {

                            Toast.makeText(getActivity(), "Welcome " + student.child("userName").getValue(), Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor sh=  sharedPreferences.edit();
                            sh.putString("user", user);
                            sh.putString("password", pas);
                            sh.putBoolean("isLogined",true);
                            sh.putString("key",student.getKey());
                            sh.commit();
                            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getActivity(), MainScreen.class);
                            startActivity(in);

                            getActivity().finish();
                            break;



                        }
                        else
                        {
                            //Toast.makeText(getActivity(), user+"."+student.child("userName").getValue()+".."+pas+"."+student.child("password").getValue(), Toast.LENGTH_SHORT).show();
                        }

                    }*/






        }


        // Anusha's login functionality
       /* {


            mail=email.getText().toString();
            pas=password.getText().toString();

            c= log.readData(mail);


            for(int l=0; l<c.getCount();l++)
            {
               // Toast.makeText(getActivity(), "in login"+c.getString(1), Toast.LENGTH_SHORT).show();
                if(c.getString(1).equals(mail) && c.getString(2).equals(pas))
                {

                    SharedPreferences.Editor sh=  sharedPreferences.edit();
                    sh.putString("email", mail);
                    sh.putString("password", pas);
                    sh.commit();
                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(getActivity(),ideasActivity.class);
                    startActivity(in);
                    getActivity().finish();

                }
            }

        }*/
        if(view==imgBackLogin)
        {
            Fragment gettingStarted= new fragment_gettingStarted();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder,gettingStarted).commit();

        }
        if (view==tvSignupLink)
        {
            Fragment signup =new fragment_finishSignup();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder,signup).commit();

        }
    }
    }


