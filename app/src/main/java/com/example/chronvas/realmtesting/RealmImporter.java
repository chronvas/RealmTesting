package com.example.chronvas.realmtesting;

import android.content.res.Resources;
import android.util.Log;

import java.io.InputStream;

import io.realm.Realm;

/**
 * From json to realm database
 */
public class RealmImporter {

    Resources resources;
    TransactionTime transactionTime;

    public RealmImporter(Resources resources) {
        this.resources = resources;
    }

    public void importFromJson(){
        Realm realm = Realm.getDefaultInstance();

        //transaction timer
        transactionTime = new TransactionTime();
        transactionTime.setStart(System.currentTimeMillis());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = resources.openRawResource(R.raw.people);
                try {
                    realm.createAllFromJson(People.class, inputStream);
                    transactionTime.setEnd(System.currentTimeMillis());
                } catch (Exception e){
                    realm.cancelTransaction();
                }

            }
        });
        if (!realm.isClosed()) realm.close();
        Log.d( "Realm","createAllFromJson Task completed in " + transactionTime.getDuration() + "ms" );
    }

}
