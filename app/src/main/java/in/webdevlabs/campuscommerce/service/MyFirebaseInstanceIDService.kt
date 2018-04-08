package `in`.webdevlabs.campuscommerce.service

import `in`.webdevlabs.campuscommerce.utils.Config
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


/**
 * Created by Shailesh351 on 8/4/18.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken)

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken)

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        val registrationComplete = Intent(Config.REGISTRATION_COMPLETE)
        registrationComplete.putExtra("token", refreshedToken)
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete)
    }

    private fun sendRegistrationToServer(token: String?) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token!!)
    }

    private fun storeRegIdInPref(token: String?) {
        val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val editor = pref.edit()
        editor.putString("regId", token)
        editor.commit()
    }

    companion object {
        private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
    }
}