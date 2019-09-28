package com.example.noor.fypv3;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_finishSignup extends Fragment implements View.OnClickListener {
    EditText username, email, password;
    String str_username, str_email, str_password;
    Button signup, login;
    //Signupdb sign;
    TextView txtPolicy;
    String policyText = "By signing up, you agree to the Terms of Service and Privacy Policy. Others will be able to find you by email when provided.";
    //DatabaseReference ref;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    Toolbar toolbar;
    ImageView imgBack;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_finish_signup, container, false);
        //getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //ref= FirebaseDatabase.getInstance().getReference("cyberguards/users");
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        progressBar = v.findViewById(R.id.progressBar);

        username = (EditText) v.findViewById(R.id.edusername);
        email = (EditText) v.findViewById(R.id.edemail);
        password = (EditText) v.findViewById(R.id.pass);
        //sign = new Signupdb(getActivity());
        signup = (Button) v.findViewById(R.id.signup);
        signup.setOnClickListener(this);
        // Inflate the layout for this fragment

        toolbar = (Toolbar) v.findViewById(R.id.toolbarSignup);
        //((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                Toast.makeText(getActivity(), "hy samad", Toast.LENGTH_SHORT).show();
            }
        });
        imgBack = (ImageView) v.findViewById(R.id.imgBackSignup);
        imgBack.setOnClickListener(this);

        txtPolicy = (TextView) v.findViewById(R.id.txtPolicy);
        SpannableString ss = new SpannableString(policyText);
        ForegroundColorSpan fgs = new ForegroundColorSpan(Color.parseColor("#D83731"));
        ForegroundColorSpan fgs1 = new ForegroundColorSpan(Color.parseColor("#D83731"));

        ss.setSpan(fgs, 32, 48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fgs1, 53, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtPolicy.setText(ss);
        return v;
    }

    public void Register() {
        str_email = email.getText().toString().trim();
        str_username = username.getText().toString().trim();
        str_password = password.getText().toString().trim();
        if (TextUtils.isEmpty(str_username)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(str_email)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(str_password)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }
        Call<User> call = MainActivity.apiInterface.performRegistration(str_username,str_email,str_password);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.body().getResponse().equals("ok"))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    SharedPreferences.Editor sh = sharedPreferences.edit();

                    //sh.putInt("key", user.getId());
                    sh.putString("password", str_password);
                    sh.putString("username", str_username);
                    sh.putString("email", str_email);
                    sh.putBoolean("isLogined", true);
                    sh.commit();
                    Toast.makeText(getActivity().getApplicationContext(), "Registered Successfully.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MainScreen.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else if(response.body().getResponse().equals("exist"))

                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(),"User Already Exist.", Toast.LENGTH_SHORT).show();

                }
                else if(response.body().getResponse().equals("error"))
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(),"Something went wrong.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        //email.setText("");
        //username.setText("");
        //password.setText("");
        /* StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, new Response.Listener<String>() {
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity().getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", str_username);
                params.put("email", str_email);
                params.put("password", str_password);

                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);*/
    }




        @Override
    public void onClick(View view) {
        if(view==signup) {
            Register();

           /* String name = username.getText().toString();
            String mail = email.getText().toString();
            String pass = password.getText().toString();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass)) {
                // Post post= new Post("","",0);
                String id = ref.push().getKey();
                User user = new User(id, name, mail, pass);
                ref.child(id).setValue(user);
                Toast.makeText(getActivity(), "Record inserted", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor sh = sharedPreferences.edit();

                sh.putString("key", id);
                sh.putString("password", pass);
                sh.putString("user", name);
                sh.putBoolean("isLogined", true);
                sh.commit();
                Intent i = new Intent(getActivity(), MainScreen.class);
                startActivity(i);
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();

            }*/

        }

        if(view==imgBack)
        {
            Fragment gettingStarted= new fragment_gettingStarted();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder,gettingStarted).commit();

        }
    }
}
