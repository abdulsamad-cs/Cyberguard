package com.example.noor.fypv3;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.noor.fypv3.MainHelpers.GMailSender;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class addChild extends Fragment implements View.OnClickListener {
    EditText handle,email,age,fullname;
    Button btnPost;
    TextView status;
    ImageView cross;
    String body=", Click on the link to confirm.";
    String link="";
    //https://www.000webhost.com/cyberguard/df.php?parent_email=parent_email&child_email=child_email
    SharedPreferences sharedPreferences;
    String parentEmail="";
    String str_email="";

    public static int RESULTCODE_ADDCHILD=18;
    ProgressBar progressBar;
    Intent i;

    public addChild() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_child, container, false);

        handle= (EditText) v.findViewById(R.id.handle);
        email= (EditText) v.findViewById(R.id.cemail);
        age= (EditText) v.findViewById(R.id.txtage);
        btnPost=(Button) v.findViewById(R.id.addButton);
        status=(TextView) v.findViewById(R.id.status);
        btnPost.setOnClickListener(this);
        cross= (ImageView) v.findViewById(R.id.imgBackSignup);
        cross.setOnClickListener(this);
        fullname=(EditText) v.findViewById(R.id.edfullname);
        progressBar = v.findViewById(R.id.progressBar);


        btnPost.setEnabled(false);
        handle.setEnabled(false);
        email.setEnabled(false);
        age.setEnabled(false);

        editextHandler(handle,fullname,email,age,btnPost);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        parentEmail=sharedPreferences.getString("email",null);



        return v;
    }

    void editextHandler(final EditText handle, EditText fullname, final EditText email, final EditText age, final Button btnPost)
    {
        fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0)
                {
                    btnPost.setEnabled(false);
                    handle.setEnabled(false);
                    email.setEnabled(false);
                    age.setEnabled(false);
                }
                else {
                    handle.setEnabled(true);
                    email.setEnabled(false);
                    age.setEnabled(false);
                    btnPost.setEnabled(false);
                    handle.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(charSequence.length()==0)
                            {
                                btnPost.setEnabled(false);
                                age.setEnabled(false);
                                email.setEnabled(false);
                            }
                            else
                            {
                                btnPost.setEnabled(false);
                                email.setEnabled(true);
                                email.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        if(charSequence.length()==0)
                                        {
                                            btnPost.setEnabled(false);
                                        }
                                        else
                                        {
                                            age.setEnabled(true);
                                            age.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                }

                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                    if(charSequence.length()==0)
                                                    {
                                                        btnPost.setEnabled(false);
                                                    }
                                                    else  btnPost.setEnabled(true);
                                                }

                                                @Override
                                                public void afterTextChanged(Editable editable) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void sendMessage(final String sendlink) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("cyberguardparentalcontrol@gmail.com", "Cb12345678");
                    sender.sendMail("EMAIL COMFIRMATION - CyberGuard Parental Control App",
                            "Hello "+handle.getText().toString()+body+"\n\n"+sendlink,
                            "cyberguardparentalcontrol@gmail.com",
                            email.getText().toString());
                    dialog.dismiss();
                    //Toast.makeText(MainScreen.this, "email sent", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    @Override
    public void onClick(View view) {
        if(view==btnPost)
        {


            String str_handle=handle.getText().toString().trim();
            str_email=email.getText().toString();
            String str_age=age.getText().toString().trim();
            String str_fullname=fullname.getText().toString().trim();


           i = new Intent(getActivity(),childListsMAin.class);
            i.putExtra("handle", str_handle);
            i.putExtra("age", str_age);
            i.putExtra("fullname", str_fullname);
            i.putExtra("child_email", str_email);


            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


            Call<User> call = MainActivity.apiInterface.verifyChildStatus(parentEmail,str_email,str_fullname,str_handle,str_age);
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getResponse().equals("ok")) {
                        progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getActivity().getApplicationContext(), "Added Successfully.", Toast.LENGTH_SHORT).show();

                        link="https://cyberguard.000webhostapp.com/cyberguard/df.php?parent_email="+parentEmail+"&child_email="+str_email;
                        sendMessage(link);

                        getTargetFragment().onActivityResult(getTargetRequestCode(),RESULTCODE_ADDCHILD,i);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else{
                        progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getActivity().getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();


                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });






        }
        if(view==cross)
        {
            //Fragment fragment = new ideasTimeline();
            getActivity().getSupportFragmentManager().popBackStack();
            //ft.show ( getActivity().getSupportFragmentManager().findFragmentByTag ( "tag" ) ).commit ();
            //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            //
            // ft.show(fragment);
            //ft.commit();
        }

    }
}
