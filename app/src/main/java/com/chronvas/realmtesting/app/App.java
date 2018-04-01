package com.chronvas.realmtesting.app;

import android.app.Application;

import com.chronvas.realmtesting.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static final String testDbFileName = "testdb.realm";
    //Overwrite database on Application startup? - Optional
    private static final boolean shouldOverwriteDatabaseOnAppStartup = true;

    @Override
    public void onCreate() {
        super.onCreate();

        if (shouldOverwriteDatabaseOnAppStartup) {
            copyBundledRealmFile(this.getResources().openRawResource(R.raw.testdb), testDbFileName);
        } else {
            //check if the db is already in place
            if (!fileFound(testDbFileName, this.getFilesDir())) {
                copyBundledRealmFile(this.getResources().openRawResource(R.raw.testdb), testDbFileName);
            }
        }

        //Config Realm for the application
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(testDbFileName)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void copyBundledRealmFile(InputStream inputStream, String outFileName) {
        try {
            File file = new File(this.getFilesDir(), outFileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileFound(String name, File file) {
        File[] list = file.listFiles();
        if (list != null)
            for (File fil : list) {
                if (fil.isDirectory()) {
                    fileFound(name, fil);
                } else if (name.equalsIgnoreCase(fil.getName())) {
                    return true;
                }
            }
        return false;
    }
}
