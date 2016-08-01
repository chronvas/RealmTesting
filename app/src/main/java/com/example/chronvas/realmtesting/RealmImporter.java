package com.example.chronvas.realmtesting;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;

/**
 * From json to realm database
 */
public class RealmImporter extends AsyncTask<Void, Void, Long> {

    Resources resources;

    public RealmImporter(Resources resources) {
        this.resources = resources;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        Realm realm = Realm.getDefaultInstance();
        //count time start
        long start = System.currentTimeMillis();
        InputStream inputStream = resources.openRawResource(R.raw.people);
        try {
            realm.beginTransaction();
            realm.createAllFromJson(People.class, inputStream);
            realm.commitTransaction();
        } catch (IOException e) {
            Log.e("error", "json");
            realm.cancelTransaction();
            e.printStackTrace();
        }
        //count time stop
        long elapsedTime = System.currentTimeMillis() - start;
        Log.e( "doInBackground: ","Task completed in " + elapsedTime + "ms" );
        return null;
    }

}
