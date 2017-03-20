package com.example.brijesh.blackcurrantproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondLevel extends AppCompatActivity {


    private String url="https://iamin-events.appspot.com/_ah/api/appUserApi/v1/collectionresponse_eventcategories/";
TextView txt;
    private List<ListPozo> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private FirstLevelAdapter firstLevelAdapter;
    LinearLayoutManager linearLayoutManager;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level);
        recyclerView = (RecyclerView) findViewById(R.id.secondLevelList);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        id=getIntent().getExtras().getString("id");
        System.out.print("jsoninsideid..."+id);
        jsonObjectRequest(id);
        
        


    }

    private void jsonObjectRequest(String id) {
        System.out.print("jsoninside..."+url+id);
        JsonObjectRequest jsonObjReq=new JsonObjectRequest(Request.Method.GET, url+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray items=jsonObject.getJSONArray("items");
                    System.out.println("json..."+items);
                    for(int i=0;i<items.length();i++)
                    {
                        JSONObject c=items.getJSONObject(i);
                        String id=c.getString("id");
                        String name=c.getString("name");
                        String image=c.getString("backdropUrl");
                       // String desc=c.getString("description");
                        ListPozo lp=new ListPozo();
                        lp.setImage(image);
                        lp.setTitle(name);
                        lp.setId(id);
                        //  lp.setInformation(desc);
                        list.add(lp);
                        System.out.println("jsondata..."+id+ name+ image);
                    }
                    //firstLevelAdapter.
                    firstLevelAdapter=new FirstLevelAdapter(SecondLevel.this,list);
                    recyclerView.setAdapter(firstLevelAdapter);
                    firstLevelAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
