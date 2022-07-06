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
	
	private static final String SECRET_SDK_KEY_PATH = "nutrariashealth-firebase-adminsdk.json";
	private static final String FIRESTORE_DATABASE_URL = "https://nutrariashealth.firebaseio.com";

	@PostConstruct
	private void initFireStore() throws IOException {

		InputStream serviceAccount = getClass().getClassLoader()
				.getResourceAsStream(SECRET_SDK_KEY_PATH);

		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl(FIRESTORE_DATABASE_URL)
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
