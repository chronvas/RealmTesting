package com.example.chronvas.realmtesting;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        Button importbtn = (Button) findViewById(R.id.importbtn);
        importbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmImporter realmImporter = new RealmImporter(getResources());
                realmImporter.importFromJson();
            }
        });

        Button countbtn = (Button) findViewById(R.id.count);
        countbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm = Realm.getDefaultInstance();

                int people = realm.where(People.class).findAll().size();
                if (people > 0) {
                    Snackbar.make(view,
                            "Found: " + people +
                                    " people in the database", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Found no people in the database!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button viewNamebtn = (Button) findViewById(R.id.viewname);
        viewNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                People first = realm.where(People.class).findFirst();
                Snackbar.make(view,
                        "First person's name is: " + first.getName() +
                                " and his age is: " + first.getAge(), Snackbar.LENGTH_LONG).show();
            }
        });

        Button changeNamebtn = (Button) findViewById(R.id.changename);
        changeNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        People michael = realm.where(People.class).findFirst();
                        michael.setName("John");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (!realm.isClosed()) realm.close();
        super.onDestroy();
    }
}
