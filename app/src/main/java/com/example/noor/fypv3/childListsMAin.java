package com.example.noor.fypv3;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class childListsMAin extends Fragment implements View.OnClickListener,MyRecyclerViewAdapter.ItemClickListener {


    //ArrayList<String> myList = new ArrayList<>();
    ArrayList<ArrayList<String>> handleList = new ArrayList<>();
    ArrayList<String> myListDescription = new ArrayList<>();
    String ideaPostUsername;
    //String[] myList = {"samad","!","2","3","4","5","6","7","8","9","10"};
    //Button btnAdd;
    EditText edText;
    ListView listView;
    public static int REQUESTCODE_ADDCHILD=19;
    boolean checker=false;
    ArrayAdapter<String> adapter;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    RecyclerView recyclerView;
    Button btnAdd;
    SharedPreferences sharedPreferences;
    List<Child> childrenList=new ArrayList<>();
    ProgressBar progressBar;
    public childListsMAin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_child_lists_main, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),childrenList);
        myRecyclerViewAdapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener) this);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String parent_email=sharedPreferences.getString("email",null);

        if(childrenList.isEmpty()) {
            Call<List<Child>> call = MainActivity.apiInterface.fetchRecords(parent_email);
            progressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<List<Child>>() {
                @Override
                public void onResponse(Call<List<Child>> call, Response<List<Child>> response) {

                    List<Child> ls = response.body();
                    if (response.isSuccessful()) {
                        //Toast.makeText(getActivity(), response.body().get(0).getChild_email(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.INVISIBLE);
                        childrenList = ls;

                        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), childrenList);
                        recyclerView.setAdapter(myRecyclerViewAdapter);
                        myRecyclerViewAdapter.notifyDataSetChanged();

                    } else {

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "response not ok", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Child>> call, Throwable t) {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Faliure" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        btnAdd= (Button) v.findViewById(R.id    .btnaddchild);
        btnAdd.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View view) {
        if(view==btnAdd)
        {
            Fragment addchildFragment = new addChild();
        addchildFragment.setTargetFragment(this,REQUESTCODE_ADDCHILD);
            FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
            ft.hide(this);
            checker=true;
            ft.addToBackStack(addchildFragment.getClass().getName());
            ft.add(R.id.ideaMainActivityfragmentHolder,addchildFragment,"addchild").commit();
            //getActivity().overridePendingTransition( R.anim.slide_down, R.anim.slide_up );
            ft.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==addChild.RESULTCODE_ADDCHILD) {
            if (requestCode == REQUESTCODE_ADDCHILD) {
                //String description = data.getStringExtra("description");
                //String description = data.getStringExtra("description");

                //Toast.makeText(getActivity(), "hello samad", Toast.LENGTH_SHORT).show();
                Child child = new Child(data.getStringExtra("fullname"),data.getStringExtra("child_email"),data.getStringExtra("handle"),data.getStringExtra("age"));
                childrenList.add(child);
                ///////////////////////////////////
                //ArrayList<String> temp= new ArrayList<>();
                //temp.add(description);
                //threadsList.add(temp);
                ////////////////////////////////////////////
                // myListDescription.add(description);    dpoing
                //myListDescription.add(description);
                myRecyclerViewAdapter.notifyDataSetChanged();

                //Toast.makeText(getActivity(), myListDescription.get(myList.size()-1), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
