package com.example.brijesh.blackcurrantproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Brijesh on 3/19/2017.
 */

public class FirstLevelAdapter extends RecyclerView.Adapter<FirstLevelAdapter.MyViewHolder> {

    private List<ListPozo> list;
    private Context context;
    private Activity activity;
    private LayoutInflater inflator;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title,information;
        public ImageView pics;
        public MyViewHolder(View view) {
            super(view);
            pics=(ImageView) view.findViewById(R.id.image1);
            title = (TextView) view.findViewById(R.id.name);
            information = (TextView) view.findViewById(R.id.info);

        }
    }


    public FirstLevelAdapter(Activity context, List<ListPozo> list) {
        this.activity=context;
        inflator=LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.firstlevelcard, parent, false);
       /* final MyViewHolder holder=new MyViewHolder(itemView);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,SecondLevel.class);

                context.startActivity(i);
            }
        });*/

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListPozo l = list.get(position);
        Uri uri=Uri.parse(l.getImage());
       context=holder.pics.getContext();
       // holder.pics.setImageResource(Integer.parseInt(l.getImage()));
        Picasso.with(context).load(uri).fit().into(holder.pics);
        holder.title.setText(l.getTitle().toString());
       // holder.information.setText(l.getInformation());
        final String title1=l.getId();
        holder.pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,SecondLevel.class);
                i.putExtra("id",title1);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
