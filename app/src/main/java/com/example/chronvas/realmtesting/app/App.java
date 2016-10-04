    package com.example.chronvas.realmtesting.app;

    import android.app.Application;

    import com.example.chronvas.realmtesting.R;

    import java.io.File;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;

    import io.realm.Realm;
    import io.realm.RealmConfiguration;

    public class App extends Application {

        @Override
        public void onCreate() {
            super.onCreate();

            //Set true to overwrite database - Optional
            boolean overwriteDatabase = true;

            if (overwriteDatabase){
                copyBundledRealmFile(this.getResources().openRawResource(R.raw.testdb), "testdb.realm");
            }else{
                //check if the db is already in place
                if (!fileFound("testdb.realm", this.getFilesDir())){
                    copyBundledRealmFile(this.getResources().openRawResource(R.raw.testdb), "testdb.realm");
                }
            }

            //Config Realm for the application
            Realm.init(this);
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .name("testdb.realm")
                    .build();

            Realm.setDefaultConfiguration(realmConfiguration);
        }

        private String copyBundledRealmFile(InputStream inputStream, String outFileName) {
            try {
                File file = new File(this.getFilesDir(), outFileName);
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, bytesRead);
                }
                outputStream.close();
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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
