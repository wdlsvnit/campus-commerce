package `in`.webdevlabs.campuscommerce.utils

import `in`.webdevlabs.campuscommerce.App
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Created by Shailesh351 on 8/4/18.
 */

object FirebaseMessageUtil {

    fun subscribeToTag(tag: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(tag)
    }

    fun unsubscribeToTag(tag: String) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(tag)
    }

    fun sendNotification() {

        val queue = Volley.newRequestQueue(App.applicationContext())
        val url = "http://www.google.com"

        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(App.applicationContext(), "Response is: ${response.substring(0, 500)}", Toast.LENGTH_SHORT).show()
                    // Display the first 500 characters of the response string.
                },
                Response.ErrorListener {
                    Toast.makeText(App.applicationContext(), "That didn't work!", Toast.LENGTH_SHORT).show()
                }) {

            override fun getHeaders(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["Content-Type"] = "application/json"
                params["Authorization"] = "api-key"

                return params
            }
        }

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}