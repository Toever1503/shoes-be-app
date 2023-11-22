package com.shoescms;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class CmsApplication {

    public static void main(String[] args) throws IOException {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("+7")));
        initFirebaseInstance();
        SpringApplication.run(CmsApplication.class, args);
    }

    private static void initFirebaseInstance() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {

            FileInputStream serviceAccount =
                    new FileInputStream("./credentials/firebase_secret.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("springboot-analysis.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }

    }


}
