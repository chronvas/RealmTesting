package com.example.chronvas.realmtesting;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button importbtn = (Button) findViewById(R.id.importbtn);
        importbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmImporter realmImporter = new RealmImporter(getResources());
                realmImporter.execute();
            }
        });

        Button countbtn = (Button) findViewById(R.id.count);
        countbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();

                int people = realm.where(People.class).findAll().size();
                if (people>0) {
                    Snackbar.make(view, "Found: " + people + " people in the database", Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(view, "Found no people in the database!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        Button viewNamebtn = (Button) findViewById(R.id.viewname);
        viewNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                People first = realm.where(People.class).findFirst();
                Snackbar.make(view, "First person's name is: "+ first.getName() + " and his age is: " + first.getAge(), Snackbar.LENGTH_LONG).show();
            }
        });

        Button changeNamebtn = (Button) findViewById(R.id.changename);
        changeNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                People michael = realm.where(People.class).findFirst();
                realm.beginTransaction();
                michael.setName("John");
                realm.commitTransaction();
            }
        });

    }


}
