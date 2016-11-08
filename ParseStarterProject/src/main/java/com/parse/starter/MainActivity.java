/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usernameField;
    EditText passwordField;
    TextView changeSignUpModeTextView;
    Button signUpButtonText;

    Boolean signUpModeActive;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignUpMode) {

            if (signUpModeActive == true) {

                signUpModeActive = false;

                changeSignUpModeTextView.setText("Sign Up");

                signUpButtonText.setText("Log In");

            } else {

                signUpModeActive = true;

                changeSignUpModeTextView.setText("Log In");

                signUpButtonText.setText("Sign Up");

            }

        }

    }

    public void signUpOrLogIn (View view) {

        if (signUpModeActive==true) {

        ParseUser user = new ParseUser();
        user.setUsername(String.valueOf(usernameField.getText()));
        user.setPassword(String.valueOf(passwordField.getText()));

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {

                    Log.i("AppInfo", "Signup Successful");

                    Intent i = new Intent(getApplicationContext(), TourneyName.class);
                    startActivity(i);

                } else {

                    Toast.makeText(getApplicationContext(),e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();
                }

            }

        });

    } else {

            ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordField.getText()), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        Log.i("AppInfo", "Login Successful");

                        Intent i = new Intent(getApplicationContext(), TourneyName.class);
                        startActivity(i);

                    } else {

                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_LONG).show();

                    }


                }

            });

        }
    }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      setTitle("Leaderboard App Login");

      if (ParseUser.getCurrentUser().getUsername() != null) {



         Intent i = new Intent(getApplicationContext(), TourneyName.class);
          startActivity(i);

      }

      signUpModeActive=true;

      usernameField = (EditText) findViewById(R.id.tourneyName);
      passwordField = (EditText) findViewById(R.id.password);
      changeSignUpModeTextView = (TextView) findViewById(R.id.changeSignUpMode);
      signUpButtonText = (Button) findViewById(R.id.registerButton);

      changeSignUpModeTextView.setOnClickListener(this);


      ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }


}
