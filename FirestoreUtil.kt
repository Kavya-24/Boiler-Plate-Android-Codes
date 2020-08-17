import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration


object FirestoreUtil {
    
    //Lazy Initialization :lazy() is a function that takes a lambda and returns an instance of Lazy<T>
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    

    //This comprises of docs and collections. data>docs>collection Each user has its own document
    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document(
            "users/${FirebaseAuth.getInstance().currentUser?.uid ?: throw NullPointerException(
                "UID Not found"
            )}"
        )

  
    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
  
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
  
                //Extract the User class in a separate file
                /**
                 * data class User(
                 *  val name : String,
                 *  val bio : String,
                 *  val profilePicture : String?
                 *  )
                 */

                val newUser =
                    User(FirebaseAuth.getInstance().currentUser?.displayName ?: "", "", null)
                    currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else {
                onComplete()
            }
        }
    }

    //Update the user details
    fun updateUser(name: String = "", bio: String = "", profilePicture: String? = "") {
        //Initializing these values to blank at first
        val userFieldMap = mutableMapOf<String, Any>()

        //Field map is to send multiple params together
        //We can update any number of strings in this
        //We ll check what values of the user are already there
     
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (profilePicture != null) userFieldMap["profilePicture"] = profilePicture
        currentUserDocRef.update(userFieldMap)

    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            it.toObject(User::class.java)?.let { it1 -> onComplete(it1) }
        }
    }

    
    

}
