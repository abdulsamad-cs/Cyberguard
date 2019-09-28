package com.example.noor.fypv3;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noor on 10/23/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    //private List<String> lData;
    private List<String> lDataDescription;
    List<ArrayList<String>> threadsList = new ArrayList<>();

    List<Child> childArrayList;//=new ArrayList<>();

   private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
   // int like,comment,share;
    public static int REQUESTCODE_COMMENT=2;
    String username="";



    // data is passed into the constructor

    MyRecyclerViewAdapter(Context context,List<Child> c) {
        this.mInflater = LayoutInflater.from(context);
        this.childArrayList = c;


    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //String title = lData.get(position);
      final Child temp = childArrayList.get(position);
        ////////////////String description = lDataDescription.get(position);
        //holder.myImageView.setImageResource(R.mipmap.ic_launcher);
        //holder.myTextView.setText(title);
       //////////////////// holder.txtDexcription.setText(description);

        //holder.username.setText( getActivity().getSharedPreferences());
        holder.txtage.setText(temp.getAge()+" years old");
        holder.txtfullname.setText(temp.getFullname());
        holder.txthandle.setText("@"+temp.getHandle());
        holder.btnActivites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment activites=new childActivities();
                AppCompatActivity activity = (AppCompatActivity) view.getRootView().getContext();
                android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
                Fragment rootFragment = fm.findFragmentByTag("childList");
                Bundle b = new Bundle();
                b.putInt("key",position);
                b.putString("fullname",temp.getFullname());
                b.putString("child_email",temp.getChild_email());
                b.putString("twitterHandle",temp.getHandle());
                activites.setArguments(b);
                activites.setTargetFragment(fm.findFragmentByTag("childList"),REQUESTCODE_COMMENT);
                fm.beginTransaction().hide(activity.getSupportFragmentManager().findFragmentByTag("childList")).commit();
                fm.beginTransaction().add(R.id.ideaMainActivityfragmentHolder, activites,"childactivites").addToBackStack(null).commit();



            }
        });




    }


    // total number of rows
    @Override
    public int getItemCount() {
//    /////////////////////////    return lDataDescription.size();
        return childArrayList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       // TextView myTextView;
        ImageView imgDP;
        TextView txthandle,txtfullname,txtage,txtnotification;
        Button btnActivites;



        ViewHolder(View itemView) {
            super(itemView);
            //myTextView = (TextView) itemView.findViewById(R.id.listItems);

           txthandle = (TextView) itemView.findViewById(R.id.twitterHandle);
           txtfullname = (TextView) itemView.findViewById(R.id.kidusername);
            txtage= (TextView) itemView.findViewById(R.id.kidage);

            btnActivites=(Button) itemView.findViewById(R.id.btnAlerts);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Child getItem(int id) {
        return childArrayList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }






    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvComment;
        TextView tvCommentUsername;
        public CommentViewHolder(View itemView) {
            super(itemView);
           // tvComment = (TextView) itemView.findViewById(R.id.txtCommentusername);
            //tvCommentUsername = (TextView) itemView.findViewById(R.id.txtCommentDesc);
            itemView.setOnClickListener(this);

        }

        // convenience method for getting data at click position
        String getItem(int id) {
            return null;//lDataDescription.get(id);

        }


        @Override
        public void onClick(View view) {

        }

    }


}



