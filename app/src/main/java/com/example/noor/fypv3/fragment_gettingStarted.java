package com.example.noor.fypv3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_gettingStarted extends Fragment implements View.OnClickListener {



    Button btnGettingStarted;
    TextView txtRandomQuote;
    TextView txtLoginLink;
    TextView txtGettingStarted;

    public fragment_gettingStarted() {
        // Required empty public constructor
    }

    String[] quotes={
            "&ldquo;Cyber bullying is bullying. Hiding behind a pretty screen doesn’t make it any less hateful&rdquo;",
            "&ldquo;Nearly 43% of kids have been bullied online. 1 in 4 has had it happen more than once.&rdquo;",
            "&ldquo;Over 80% of teens use a cell phone regularly, making it the most common medium for cyber bullying.&rdquo;",
            "&ldquo;90% of teens who have seen social-media bullying say they have ignored it. 84% have seen others tell cyber bullies to stop.&rdquo;",
            "&ldquo;About 58% of kids admit someone has said mean or hurtful things to them online. More than 4 out 10 say it has happened more than once.&rdquo;"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fragment_getting_started, container, false);
        Random rand = new Random();
        int randomNum = rand.nextInt(quotes.length);
        btnGettingStarted = (Button) v.findViewById(R.id.btngetting);
        txtRandomQuote = (TextView) v.findViewById(R.id.txtQuote);
        txtLoginLink= (TextView) v.findViewById(R.id.txtLoginLink);
        txtRandomQuote.setText(Html.fromHtml(quotes[randomNum]));
        btnGettingStarted.setOnClickListener(this);
        txtLoginLink.setOnClickListener(this);
        txtGettingStarted= (TextView) v.findViewById(R.id.txtGettingStarted);





        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==btnGettingStarted)
        {
            fragment_finishSignup signupfragment= new fragment_finishSignup();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder,signupfragment).commit();
        }
        if(v==txtLoginLink)
        {
            Fragment loginfragment= new fragment_login();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentholder,loginfragment).commit();
        }

    }
}
