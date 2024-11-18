package edu.ucam.myapp.Helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.DocumentSnapshot

class FirebaseManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getInstance(): FirebaseAuth {
        return auth
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signInUser(email: String, password: String, callback: (Boolean, String?, String?, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userEmail = auth.currentUser?.email
                    val userName = auth.currentUser?.displayName
                    callback(true, null, userEmail, userName)
                } else {
                    callback(false, task.exception?.message, null, null)
                }
            }
    }

    fun registerUser(name: String, email: String, password: String, callback: (Boolean, String?, String?, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userEmail = user?.email
                    val userName = name
                    callback(true, null, userEmail, userName)
                } else {
                    callback(false, task.exception?.message, null, null)
                }
            }
    }

    fun signOutUser() {
        auth.signOut()
    }

    fun insertData(collection: String, data: Map<String, Any>, callback: (Boolean, String?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection(collection)
            .add(data)
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception.message)
            }
    }

    fun getData(collection: String, callback: (QuerySnapshot?, String?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection(collection)
            .get()
            .addOnSuccessListener { documents ->
                callback(documents, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun getDocumentById(collection: String, documentId: String, callback: (DocumentSnapshot?, String?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection(collection).document(documentId)
            .get()
            .addOnSuccessListener { document ->
                callback(document, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    /*
    Ejemplos de uso:

    // Insertar datos en Firestore:
    val data = hashMapOf(
        "nombre" to "Café Espresso",
        "precio" to 1.99,
        "categoria" to "Bebidas"
    )

    FirebaseAuthManager.insertData("productos", data) { success, message ->
        if (success) {
            Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al agregar producto: $message", Toast.LENGTH_SHORT).show()
        }
    }

    // Leer datos de una colección:
    FirebaseAuthManager.getData("productos") { documents, message ->
        if (documents != null) {
            for (document in documents) {
                val nombre = document.getString("nombre")
                val precio = document.getDouble("precio")
                val categoria = document.getString("categoria")
                // Haz algo con los datos obtenidos
            }
        } else {
            Toast.makeText(this, "Error al obtener productos: $message", Toast.LENGTH_SHORT).show()
        }
    }

    // Leer un documento específico por ID:
    FirebaseAuthManager.getDocumentById("productos", "ID_DEL_DOCUMENTO") { document, message ->
        if (document != null) {
            val nombre = document.getString("nombre")
            val precio = document.getDouble("precio")
            val categoria = document.getString("categoria")
            // Haz algo con los datos obtenidos
        } else {
            Toast.makeText(this, "Error al obtener el documento: $message", Toast.LENGTH_SHORT).show()
        }
    }
    */
}
