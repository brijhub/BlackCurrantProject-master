package com.example.brijesh.blackcurrantproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ListPozo> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private FirstLevelAdapter firstLevelAdapter;
    LinearLayoutManager linearLayoutManager;
    private String url="https://iamin-events.appspot.com/_ah/api/appUserApi/v1/collectionresponse_maineventlisting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.firstLevelList);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
      // firstLevelAdapter = new FirstLevelAdapter(list);
        System.out.print("jsonbefore...");
        jsonObjectRequest();
        System.out.print("jsonafter...");
        //finish();

    }

    private void jsonObjectRequest() {
        System.out.print("jsoninside...");
        JsonObjectRequest jsonObjReq=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
                        String desc=c.getString("description");
                        ListPozo lp=new ListPozo();
                        lp.setImage(image);
                        lp.setTitle(name);
                        lp.setId(id);
                      //  lp.setInformation(desc);
                        list.add(lp);
                        System.out.println("jsondata..."+id+ name+ image);
                    }
                    //firstLevelAdapter.
                    firstLevelAdapter=new FirstLevelAdapter(MainActivity.this,list);
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
