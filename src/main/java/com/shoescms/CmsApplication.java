package com.shoescms;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class CmsApplication {

    public static void main(String[] args) throws IOException {
        initFirebaseInstance();
        SpringApplication.run(CmsApplication.class, args);
    }

    private static void initFirebaseInstance() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            FileInputStream serviceAccount =
                    new FileInputStream("credentials/firebase_secret.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("springboot-analysis.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }

    }


}
