package com.chronvas.realmtesting;

import android.content.res.Resources;
import android.util.Log;

import java.io.InputStream;

import io.realm.Realm;

/**
 * From json to realm database
 */
public class RealmImporter {

    static void importFromJson(final Resources resources) {
        Realm realm = Realm.getDefaultInstance();

        //transaction timer
        final TransactionTime transactionTime = new TransactionTime(System.currentTimeMillis());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = resources.openRawResource(R.raw.people);
                try {
                    realm.createAllFromJson(People.class, inputStream);
                    transactionTime.setEnd(System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    realm.close();
                }
            }
        });
        Log.d("Realm", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
    }
}
