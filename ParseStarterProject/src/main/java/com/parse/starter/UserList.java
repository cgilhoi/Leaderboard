package com.parse.starter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity implements View.OnClickListener  {



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tourneyInfo) {

            Log.i("tourney info", "tapped");

            Intent m = new Intent(getApplicationContext(), TourneyInfo.class);
            startActivity(m);

        } else {

            Log.i("tourney info", "not tapped");
        }
    }
    public void fishOn(View view) {

        Intent i = getIntent();
        String tourneynameField = i.getStringExtra("Tournament");


        Intent k = new Intent(getApplicationContext(), FishDetails.class);
        k.putExtra("Tournament", tourneynameField);
        startActivity(k);


    }

    public void deleteObject(View view) {

        Intent i = getIntent();
        String tourneynameField = i.getStringExtra("Tournament");

        Intent l = new Intent(getApplicationContext(), DeleteObject.class);
        l.putExtra("Tournament", tourneynameField);
        startActivity(l);
    }



    public void leaderBoard(View view) {

        Intent i = getIntent();
        String tourneynameField = i.getStringExtra("Tournament");

        Intent j = new Intent(getApplicationContext(), LeaderboardAlt.class);
        j.putExtra("Tournament", tourneynameField);
        startActivity(j);
    }

    public void sharePhoto(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);

    }

    public void logOut(View view) {

        Log.i("AppInfo", "Logout tapped");

        ParseUser.logOut();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri selectedImage = data.getData();

            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                // ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmapImage);

                Log.i("AppInfo", "Image Received");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] byteArray = stream.toByteArray();

                ParseFile file = new ParseFile("image.png", byteArray);

                ParseObject object = new ParseObject("Images");

                object.put("username", ParseUser.getCurrentUser().getUsername());

                object.put("image", file);

                ParseACL parseACL = new ParseACL();
                parseACL.setPublicReadAccess(true);
                object.setACL(parseACL);

                object.saveInBackground(new SaveCallback() {
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


    ArrayList<String> usernames;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        TextView tourneyInfoTextView;
        tourneyInfoTextView = (TextView) findViewById(R.id.tourneyInfo);


        tourneyInfoTextView.setOnClickListener(this);

        /*WebView webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://icefishing.org");
*/

        Intent i = getIntent();
        String tourneynameField = i.getStringExtra("Tournament");


        setTitle("Tournament: " + tourneynameField + " User: " + ParseUser.getCurrentUser().getUsername());

        usernames = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usernames);
    }


    }



        /*final ListView userList = (ListView)findViewById(R.id.userList);

       // userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent i = new Intent(getApplicationContext(), UserFeed.class);
                i.putExtra("username", usernames.get(position));

                startActivity(i);


            }
        });



        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.addAscendingOrder("username");
        query.whereEqualTo("tournament_ID", tourneynameField);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {



                        for (ParseUser user : objects) {

                            usernames.add(user.getUsername());

                        }



                        userList.setAdapter(arrayAdapter);

                       // System.out.println(userList);
                    }

                }
            }
        });

        */





