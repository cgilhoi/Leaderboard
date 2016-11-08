package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaderboardAlt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_alt);

        setTitle("Leaderboard");

        Intent j = getIntent();
        String tourneynameField = j.getStringExtra("Tournament");
        Log.i("tourney name", tourneynameField);

        final ListView fisherList = (ListView) findViewById(R.id.listView1);







        final ArrayList<String> fishUserArray = new ArrayList<>();
        final ArrayList<String> idArray = new ArrayList<>();

        fisherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Log.i("App Info", idArray.get(position));

                Intent i = new Intent(getApplicationContext(), UserFeed.class);
                i.putExtra("object ID", idArray.get(position));

                Log.i("App info", idArray.get(position));


                startActivity(i);


            }
        });


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Leaderboard");

        query.addDescendingOrder("length");
        query.whereEqualTo("tournament_ID", tourneynameField);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    int x=1;

                    for (ParseObject fishLength : objects) {





                            String rankString = Integer.toString(x);

                        String idString = fishLength.getObjectId();
                        idArray.add(idString);




                        Integer lengthInt = fishLength.getInt("length");
                        String fishString = Integer.toString(lengthInt);
                        //fishLengthArray.add(fishString);


                        String fishermanString = fishLength.getString("username");

                        //TextView rankViewVar = (TextView) findViewById(R.id.rankView);

                       // rankViewVar.setText(rankString);

                        fishUserArray.add(rankString+"\t\t\t\t\t\t"+ fishermanString+"\t\t\t\t\t\t"+fishString+"\t\t\t\t\t\t"
                        +idString);



                        x++;

                    }

                    ArrayAdapter arrayAdapter2=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fishUserArray);
                    fisherList.setAdapter(arrayAdapter2);


                }
            }
        });
    }
}


