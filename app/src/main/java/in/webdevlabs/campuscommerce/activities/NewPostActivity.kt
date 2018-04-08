package `in`.webdevlabs.campuscommerce.activities

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Post
import `in`.webdevlabs.campuscommerce.model.PostType
import `in`.webdevlabs.campuscommerce.utils.FirebaseUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_post.*
import java.text.SimpleDateFormat
import java.util.*

class NewPostActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        setupToolbar()
        setup()
    }

    private fun setup() {
        submit.setOnClickListener({
            submit()
        })
    }

    private fun submit() {
        if (text_title.text.toString().isEmpty() || text_price.text.toString().isEmpty()) {
            Toast.makeText(this, "Please fill the above info", Toast.LENGTH_SHORT).show()
            return
        }

        val title = text_title.text.toString()
        val price = text_price.text.toString().toInt()
        val type = when (radioGroup.checkedRadioButtonId) {
            R.id.sellRadioButton -> PostType.SELL
            else -> PostType.RENT
        }

        val currentLocalTime = Calendar.getInstance(TimeZone.getDefault()).time
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        val time = date.format(currentLocalTime)
        val tag = tag_text.text.toString()

        val post = Post("1", title, price, firebaseAuth.currentUser?.uid!!, time, type, tag)
        Toast.makeText(this, "Post submitted", Toast.LENGTH_SHORT).show()

        FirebaseUtil.addPostToFirebaseDatabase(post)

        finish()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
