package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeed extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);



        Intent i = getIntent();
        String objectID = i.getStringExtra("object ID");

        //Log.i("AppInfo", activeUserName);

        setTitle("Fish Pic");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Leaderboard");

        query.getInBackground(objectID, new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, ParseException e) {

                if (e==null) {

                            ParseFile file = (ParseFile) object.get("image");

                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if (e==null) {

                                        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);

                                        ImageView imageView = new ImageView(getApplicationContext());

                                        imageView.setImageBitmap(image);

                                        linearLayout.addView(imageView);

                                    }
                                }
                            });

                        }
                    }
                });
            }

    }

