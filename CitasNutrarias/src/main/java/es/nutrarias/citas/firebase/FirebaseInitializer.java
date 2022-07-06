package es.nutrarias.citas.firebase;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitializer {

	@PostConstruct
	private void initFireStore() throws IOException {

		InputStream serviceAccount = getClass().getClassLoader()
				.getResourceAsStream("nutrariashealth-firebase-adminsdk.json");

		@SuppressWarnings("deprecation")
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://nutrariashealth.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp();
		}

	}

	public Firestore getFirestore() {
		return com.google.firebase.cloud.FirestoreClient.getFirestore();
	}

}
