package com.saif.hellorealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.saif.hellorealm.model.SocialAccount;
import com.saif.hellorealm.model.User;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

/**
 * Author: Saif Khaled Saifullah
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText etPersonName, etAge, etSocialAccountName, etStatus;

    private Realm myRealm;
    private RealmAsyncTask realmAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPersonName = (EditText) findViewById(R.id.etPersonName);
        etAge = (EditText) findViewById(R.id.etAge);
        etSocialAccountName = (EditText) findViewById(R.id.etSocialAccount);
        etStatus = (EditText) findViewById(R.id.etStatus);

        // Realm instance
        myRealm = Realm.getDefaultInstance();
    }

    // Add data to Realm using Main UI Thread. Be Careful: As it may BLOCK the UI.
    public void addUserToRealm_Synchronously(View view) {

        final String name = etPersonName.getText().toString();
        final int age = Integer.valueOf(etAge.getText().toString());
        final String socialAccountName = etSocialAccountName.getText().toString();
        final String status = etStatus.getText().toString();
        final String id = UUID.randomUUID().toString();

        // insertData with a try catch block

        /*
        try {
            myRealm.beginTransaction();
            SocialAccount socialAccount = myRealm.createObject(SocialAccount.class);
            socialAccount.setName(socialAccountName);
            socialAccount.setStatus(status);
            User user = myRealm.createObject(User.class, id);
            user.setName(name);
            user.setAge(age);
            user.setSocialAccount(socialAccount);
            myRealm.commitTransaction();
        } catch (Exception e) {
            myRealm.cancelTransaction();
        }*/


        // insert Data with executeTransaction Method
        myRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SocialAccount socialAccount = realm.createObject(SocialAccount.class);
                socialAccount.setName(socialAccountName);
                socialAccount.setStatus(status);

                User user = realm.createObject(User.class, id);
                user.setName(name);
                user.setAge(age);
                user.setSocialAccount(socialAccount);
                Toast.makeText(MainActivity.this, "Data inserted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Add Data to Realm in the Background Thread.
    public void addUserToRealm_ASynchronously(View view) {

    }

    //Show all Data From Realm Databse
    public void displayAllUsers(View view) {

    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
