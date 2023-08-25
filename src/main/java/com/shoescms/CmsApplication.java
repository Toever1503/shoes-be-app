package com.shoescms;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class CmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void onStartApplication() throws IOException {
		FileInputStream serviceAccount =
				new FileInputStream("./credentials/springboot-analysis-firebase-adminsdk-4tybt-238e7329ca.json");
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setStorageBucket("springboot-analysis.appspot.com")
				.build();
		FirebaseApp.initializeApp(options);
		testUploadFile();
	}

	public void testUploadFile(){
		Bucket bucket = StorageClient.getInstance().bucket();

		String blobName = "my_blob_name";
		InputStream content = new ByteArrayInputStream("Hello, World!".getBytes(StandardCharsets.UTF_8));
		Blob blob = bucket.create(blobName, content, "text/plain");
	}



}
