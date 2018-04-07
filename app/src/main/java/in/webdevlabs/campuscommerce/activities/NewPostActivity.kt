package `in`.webdevlabs.campuscommerce.activities

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Post
import `in`.webdevlabs.campuscommerce.model.PostType
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_post.*

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
        val title = text_title.text.toString()
        val price = text_price.text.toString().toInt()
        val type = when (radioGroup.checkedRadioButtonId) {
            R.id.sellRadioButton -> PostType.SELL
            else -> PostType.RENT
        }
        val time = "time"

        val post = Post("1", title, price, firebaseAuth.currentUser?.uid!!, time, type, listOf(""))
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
