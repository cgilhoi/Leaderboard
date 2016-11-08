package com.parse.starter;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;



import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class    Leaderboard2 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard2);

        setTitle("Leaderboard");



        final ListView fishList = (ListView) findViewById(R.id.lengthListView);





        final ListView fishermanList = (ListView) findViewById(R.id.fishermanListView);
        final ListView idList = (ListView) findViewById(R.id.objectIDListView);
        final ListView rankList = (ListView) findViewById(R.id.rankList);


        //List<Integer> arr = Arrays.asList(new Integer[10]);

        Intent j = getIntent();
        String tourneynameField = j.getStringExtra("Tournament");
        Log.i("tourney name", tourneynameField);



        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Leaderboard");

        query.addDescendingOrder("length");
        query.whereEqualTo("tournament_ID", tourneynameField);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e==null){






                        final ArrayList<String> fishLengthArray = new ArrayList<String>();



                        ArrayList<String> fishUserArray = new ArrayList<String>();
                        final ArrayList<String> idArray = new ArrayList<String>();

                    idList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                            Log.i("App Info", idArray.get(position));

                            Intent i = new Intent(getApplicationContext(), UserFeed.class);
                            i.putExtra("object ID", idArray.get(position));

                            Log.i("App info", idArray.get(position));


                            startActivity(i);


                        }
                    });


                        final ArrayList<String> rankArray = new ArrayList<String>();






                            for (ParseObject fishLength : objects) {



                                Integer lengthInt = fishLength.getInt("length");
                                String fishString = Integer.toString(lengthInt);
                                fishLengthArray.add(fishString);


                                String fishermanString = fishLength.getString("username");
                                fishUserArray.add(fishermanString);


                                String idString = fishLength.getObjectId();
                                idArray.add(idString);




                            }

                    int x=1;

                    while (x<=8){

                        String rankString = Integer.toString(x);
                        rankArray.add(rankString);

                        x++;
                    }





                        ArrayAdapter arrayAdapter2=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fishLengthArray);
                        fishList.setAdapter(arrayAdapter2);

                        ArrayAdapter arrayAdapter3=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, fishUserArray);
                        fishermanList.setAdapter(arrayAdapter3);

                            ArrayAdapter arrayAdapter4=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, idArray);
                            idList.setAdapter(arrayAdapter4);

                    ArrayAdapter arrayAdapter5=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, rankArray);
                    rankList.setAdapter(arrayAdapter5);


                } else {
                    e.printStackTrace();
                }
            }
        });

        class ListViewLoader extends ListActivity
                implements LoaderManager.LoaderCallbacks<Cursor> {

        SimpleCursorAdapter mAdapter;

        


            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return null;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        }







    }
}
