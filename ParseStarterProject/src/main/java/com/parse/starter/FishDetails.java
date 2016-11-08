package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FishDetails extends AppCompatActivity {

    EditText fishLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_details);



        setTitle(ParseUser.getCurrentUser().getUsername() + "'s new catch");

    }

    public void submitWithPhoto (View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1 && resultCode==RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                // ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmapImage);

                Log.i("AppInfo", "Image Received");

                ByteArrayOutputStream stream= new ByteArrayOutputStream();

                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();

                //start of copied <code></code>

                fishLength = (EditText) findViewById(R.id.fishLength);

                String fishString = fishLength.getText().toString();
                final int fishInt = Integer.parseInt(fishString);

                Intent k = getIntent();
                String tourneynameField = k.getStringExtra("Tournament");

                ParseObject leaderBoard = new ParseObject("Leaderboard");
                ParseFile file = new ParseFile("image.png", byteArray);
                leaderBoard.put("username", ParseUser.getCurrentUser().getUsername());
                leaderBoard.put("length", fishInt);
                leaderBoard.put("tournament_ID", tourneynameField);
                leaderBoard.put("image", file);

                ParseACL parseACL = new ParseACL();
                parseACL.setPublicReadAccess(true);
                leaderBoard.setACL(parseACL);

                leaderBoard.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {


                            Toast.makeText(getApplication().getBaseContext(), "Your image has been posted", Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getApplication().getBaseContext(), "There was an error.  Please try again.", Toast.LENGTH_LONG).show();
                        }

                    }


                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




        public void fishDetails (View view) {


       fishLength = (EditText) findViewById(R.id.fishLength);

            String fishString = fishLength.getText().toString();
            final int fishInt = Integer.parseInt(fishString);

            Intent k = getIntent();
            String tourneynameField = k.getStringExtra("Tournament");

            ParseObject leaderBoard = new ParseObject("Leaderboard");
            leaderBoard.put("username", ParseUser.getCurrentUser().getUsername());
            leaderBoard.put("length", fishInt);
            leaderBoard.put("tournament_ID", tourneynameField);

            ParseACL parseACL = new ParseACL();
            parseACL.setPublicReadAccess(true);
            leaderBoard.setACL(parseACL);

        leaderBoard.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Fish", "Save Succeeded");



                } else {
                    Log.i("Fish", "Save Failed");
                }
            }
        });


    }
}
