package `in`.webdevlabs.campuscommerce.utils

import `in`.webdevlabs.campuscommerce.model.Post
import `in`.webdevlabs.campuscommerce.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


object FirebaseUtil {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var pids = ArrayList<String>()

    fun addCurrentUserToFirebaseDatabase() {
        val databaseRef: DatabaseReference = database.getReference("users")

        val user = User(firebaseAuth.currentUser?.displayName, firebaseAuth.currentUser?.email, pids)
        databaseRef.child(firebaseAuth.currentUser?.uid).setValue(user)
    }

    fun addPostToFirebaseDatabase(post: Post) {
        val dbref: DatabaseReference = database.getReference("posts").push()
        val key = dbref.key
        pids.add(key)
        dbref.child("name").setValue(post.name)
        dbref.child("price").setValue(post.price)
        dbref.child("uid").setValue(firebaseAuth.currentUser?.uid)
//        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"))
//        val currentLocalTime = cal.getTime()
//        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        dbref.child("time").setValue(post.time)
        dbref.child("type").setValue(post.type)
        dbref.child("tag").setValue(post.tags)
        val dbref1 = database.getReference("users").child(firebaseAuth.currentUser?.uid)
        dbref1.child("posts").setValue(pids)
    }
}