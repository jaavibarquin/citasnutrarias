package es.nutrarias.citas.citasnutrarias.repository;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import es.nutrarias.citas.citasnutrarias.entities.RolUsuario;
import es.nutrarias.citas.citasnutrarias.entities.Usuario;
import es.nutrarias.citas.citasnutrarias.firebase.FirebaseInitializer;

@Repository
public class UsuariosRepository  {
	
	@Autowired
	private FirebaseInitializer firebase;
	
	private static final String COLECCION_USUARIOS = "users";
	private static final String CAMPO_EMAIL = "email";
	private static final String CAMPO_ROL = "role";
	
	public List<Usuario> findAll() {
		List<Usuario> listaUsuarios = new LinkedList<>();
		Usuario usuario = null;
		String email="";
		String uid="";
		RolUsuario role;
		ApiFuture<QuerySnapshot> queryApiFuture = getColeccionUsuarios().get();
		
		try {
			for(DocumentSnapshot doc: queryApiFuture.get().getDocuments()) {
				uid =doc.getId();
				email = doc.getString(CAMPO_EMAIL);
				role = this.getRol(doc.getString(CAMPO_ROL));
				usuario = new Usuario(uid, email, role);
				System.out.println("USUARIO: " + usuario.getEmail()+ " " + usuario.getRoles() + " " + usuario.getUid());
				listaUsuarios.add(usuario);
			}
			return listaUsuarios;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
		

	}
	
	public Usuario findByEmail(String emailUsuario) {
		Usuario usuario = null;
		String email="";
		String uid="";
		RolUsuario role;
		ApiFuture<QuerySnapshot> queryApiFuture = getColeccionUsuarios().get();
		
		try {
			for(DocumentSnapshot doc: queryApiFuture.get().getDocuments()) {
				uid =doc.getId();
				email = doc.getString(CAMPO_EMAIL);
				role = this.getRol(doc.getString(CAMPO_ROL));
				if (email.equals(emailUsuario)) {
					usuario = new Usuario(uid, email, role);
				}
				
			}
			return usuario;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Usuario findByUid(String uidUsuario) {
		Usuario usuario = null;
		String email="";
		String uid="";
		RolUsuario role;
		ApiFuture<QuerySnapshot> queryApiFuture = getColeccionUsuarios().get();
		
		try {
			for(DocumentSnapshot doc: queryApiFuture.get().getDocuments()) {
				uid =doc.getId();
				email = doc.getString(CAMPO_EMAIL);
				role = this.getRol(doc.getString(CAMPO_ROL));
				if (uid.equals(uidUsuario)) {
					usuario = new Usuario(uid, email, role);
				}	
			}
			return usuario;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private CollectionReference getColeccionUsuarios() {
		return firebase.getFirestore().collection(COLECCION_USUARIOS);
	}
	
	private RolUsuario getRol(String roleString) {
		if(roleString.equals("ADMIN")) {
			return RolUsuario.ROLE_ADMIN;
		} else if (roleString.equals("BASICO")) {
			return RolUsuario.ROLE_USER;
		}
		return null;
	}

	
}
