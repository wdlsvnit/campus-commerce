package `in`.webdevlabs.campuscommerce.utils

import `in`.webdevlabs.campuscommerce.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


object FirebaseUtil {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    //private var pids = ArrayList<String>()

    fun addCurrentUserToFirebaseDatabase() {
        val databaseRef: DatabaseReference = database.getReference("users")

        databaseRef.child(firebaseAuth.currentUser?.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                dataSnapshot?.run {
                    if (!dataSnapshot.exists()) {
                        val userRef = databaseRef.child(firebaseAuth.currentUser?.uid)
                        userRef.child("name").setValue(firebaseAuth.currentUser?.displayName)
                        userRef.child("email").setValue(firebaseAuth.currentUser?.email)
                    }
                }
            }
        })
    }

    fun addPostToFirebaseDatabase(post: Post) {
        val postRef: DatabaseReference = database.getReference("posts").push()
        val key = postRef.key
        //pids.add(key)
        postRef.child("name").setValue(post.name)
        postRef.child("price").setValue(post.price)
        postRef.child("uid").setValue(firebaseAuth.currentUser?.uid)
        postRef.child("time").setValue(post.time)
        postRef.child("type").setValue(post.type)
        postRef.child("tag").setValue(post.tags)

        val userPostRef = database.getReference("users").child(firebaseAuth.currentUser?.uid).child("posts")
        //userPostRef.child("posts").setValue(pids)
        userPostRef.child(key).setValue(true)
    }
}