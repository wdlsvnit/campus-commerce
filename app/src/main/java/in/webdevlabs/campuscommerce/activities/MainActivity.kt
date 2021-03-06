package `in`.webdevlabs.campuscommerce.activities

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.fragments.HomeFragment
import `in`.webdevlabs.campuscommerce.fragments.MyChats
import `in`.webdevlabs.campuscommerce.utils.Config
import `in`.webdevlabs.campuscommerce.fragments.SubscriptionFragment
import `in`.webdevlabs.campuscommerce.utils.Constants
import `in`.webdevlabs.campuscommerce.utils.FirebaseUtil
import `in`.webdevlabs.campuscommerce.utils.NotificationUtils
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private var registrationBroadcastReceiver: BroadcastReceiver? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private var drawer: Drawer? = null
    private var header: AccountHeader? = null
    private var profileDrawerItem: ProfileDrawerItem? = null
    private var itemSignIn: PrimaryDrawerItem? = null
    private var itemSignOut: PrimaryDrawerItem? = null
    private var itemVerifiedProfile: PrimaryDrawerItem? = null
    private var itemUnverifiedProfile: PrimaryDrawerItem? = null
    private var itemHome: PrimaryDrawerItem? = null
    private var itemChats: PrimaryDrawerItem? = null
    private var itemSubscriptions: PrimaryDrawerItem? = null
    private var currentProfile: PrimaryDrawerItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        instantiateUser()
        instantiateMenuItems()
        setupProfileDrawer()
        setupNavigationDrawerWithHeader()
        setupBroadcastReceiver()
    }

    private fun setupBroadcastReceiver() {
        registrationBroadcastReceiver = object : BroadcastReceiver() {
            @Override
            override fun onReceive(context: Context, intent: Intent) {

                // checking for type intent filter
                if (intent.action == Config.REGISTRATION_COMPLETE) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.action == Config.PUSH_NOTIFICATION) {
                    // new push notification is received

                    val message: String = intent.getStringExtra("message");

                    Toast.makeText(applicationContext, "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    override fun onResume() {
        super.onResume()

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(this.registrationBroadcastReceiver!!,
                object : IntentFilter(Config.REGISTRATION_COMPLETE) {})

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(this.registrationBroadcastReceiver!!,
                object : IntentFilter(Config.REGISTRATION_COMPLETE) {})

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(applicationContext)
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.registrationBroadcastReceiver!!)
        super.onPause()
    }

    private fun displayFirebaseRegId() {
        val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0);
        val regId: String = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!regId.isEmpty())
            Log.d("fcm", "Firebase Reg Id: " + regId)
        else
            Log.d("fcm", "Firebase Reg Id is not received yet!")
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun instantiateUser() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private fun checkCurrentProfileStatus(): PrimaryDrawerItem {
        currentProfile = if (firebaseAuth.currentUser?.isEmailVerified!!) {
            itemVerifiedProfile
        } else {
            itemUnverifiedProfile
        }
        return currentProfile as PrimaryDrawerItem
    }

    private fun instantiateMenuItems() {
        itemVerifiedProfile = PrimaryDrawerItem().withIdentifier(Constants.ITEM_VERIFIED_PROFILE.toLong()).withName(R.string.verified_profile).withIcon(resources.getDrawable(R.drawable.ic_verified_user_black_24px)).withSelectable(false)
        itemUnverifiedProfile = PrimaryDrawerItem().withIdentifier(Constants.ITEM_UNVERIFIED_PROFILE.toLong()).withName(R.string.unverified_profile).withIcon(resources.getDrawable(R.drawable.ic_report_problem_black_24px))

        itemSignIn = PrimaryDrawerItem().withIdentifier(Constants.ITEM_SIGN_IN.toLong()).withName(R.string.menu_item_sign_in).withIcon(resources.getDrawable(R.drawable.ic_account_circle_black_24px)).withSelectable(false)
        itemSignOut = PrimaryDrawerItem().withIdentifier(Constants.ITEM_SIGN_OUT.toLong()).withName(R.string.menu_item_sign_out).withIcon(resources.getDrawable(R.drawable.ic_sign_out)).withSelectable(false)

        itemHome = PrimaryDrawerItem().withIdentifier(Constants.ITEM_HOME.toLong()).withName(R.string.menu_item_home).withIcon(resources.getDrawable(R.drawable.ic_home_black_24dp))
        itemChats = PrimaryDrawerItem().withIdentifier(Constants.ITEM_CHATS.toLong()).withName(R.string.menu_item_chats).withIcon(resources.getDrawable(R.drawable.ic_chat_bubble_outline_black_24dp))
        itemSubscriptions = PrimaryDrawerItem().withIdentifier(Constants.ITEM_SUB.toLong()).withName(R.string.menu_item_subs).withIcon(resources.getDrawable(R.drawable.ic_notifications_black_24dp))
    }

    private fun setupProfileDrawer() {
        profileDrawerItem = if (isUserSignedIn()) {
            ProfileDrawerItem()
                    .withName(firebaseAuth.currentUser?.displayName)
                    .withEmail(firebaseAuth.currentUser?.email)
                    .withIcon(resources.getDrawable(R.drawable.ic_account_circle_black_24px))
        } else {
            ProfileDrawerItem()
                    .withIcon(resources.getDrawable(R.drawable.ic_account_circle_black_24px))
        }
    }

    private fun setupAccountHeader(): AccountHeader {
        header = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profileDrawerItem)
                .withOnAccountHeaderListener { view, profile, currentProfile -> false }
                .withSelectionListEnabledForSingleProfile(false)
                .build()
        return header as AccountHeader
    }

    private fun setupNavigationDrawerWithHeader() {
        if (!isUserSignedIn()) {
            drawer = DrawerBuilder()
                    .withActivity(this)
                    .withAccountHeader(setupAccountHeader())
                    .withToolbar(toolbar)
                    .addDrawerItems(itemSignIn, DividerDrawerItem(), itemHome, itemChats, itemSubscriptions, DividerDrawerItem())
                    .withOnDrawerItemClickListener { _, _, drawerItem ->
                        onNavDrawerItemSelected(drawerItem.identifier.toInt())
                        true
                    }
                    .build()
        } else {
            currentProfile = checkCurrentProfileStatus()
            drawer = DrawerBuilder()
                    .withActivity(this)
                    .withAccountHeader(setupAccountHeader())
                    .withToolbar(toolbar)
                    .addDrawerItems(currentProfile, DividerDrawerItem(), itemHome, itemChats, itemSubscriptions, DividerDrawerItem(), itemSignOut)
                    .withOnDrawerItemClickListener { view, position, drawerItem ->
                        onNavDrawerItemSelected(drawerItem.identifier.toInt())
                        true
                    }
                    .build()
        }

        onNavDrawerItemSelected(Constants.ITEM_HOME)
        drawer?.setSelection(itemHome?.identifier!!)
        (drawer as Drawer).closeDrawer()
    }

    private fun onNavDrawerItemSelected(drawerItemIdentifier: Int) {
        when (drawerItemIdentifier) {
            Constants.ITEM_UNVERIFIED_PROFILE -> {
                //Send verification mail
            }
            Constants.ITEM_SIGN_IN -> {
                startAuthUIActivity()
            }
            Constants.ITEM_HOME -> {
                replaceFragment(HomeFragment())
            }
            Constants.ITEM_CHATS -> {
                replaceFragment(MyChats())
            }
            Constants.ITEM_SUB -> {
                replaceFragment(SubscriptionFragment())
            }
            Constants.ITEM_SIGN_OUT -> {
                signOutUser()
                replaceFragment(HomeFragment())
            }
        }

        if (drawer?.isDrawerOpen!!)
            drawer?.closeDrawer()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                //Successfully signed in
                Toast.makeText(this, R.string.sign_in_success, Toast.LENGTH_LONG).show()

//                val sp = this.getSharedPreferences("sp", 0)
//                val editor = sp!!.edit()
//                editor.putString("username", FirebaseAuth.getInstance().currentUser?.displayName)
//                editor.apply()

                updateUIAfterSignIn()
                FirebaseUtil.addCurrentUserToFirebaseDatabase()
                replaceFragment(HomeFragment())
                return
            } else {
                //User pressed back button
                if (response == null) {
                    Toast.makeText(this, R.string.sign_in_failed, Toast.LENGTH_LONG).show()
                    drawer?.deselect(itemSignIn?.identifier!!)
                    return
                }

                //No internet connection.
                if (response.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.no_connectivity, Toast.LENGTH_LONG).show()
                    return
                }

                //Unknown error
                if (response.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, R.string.sign_in_unknown_Error, Toast.LENGTH_LONG).show()
                    return
                }
            }

        }
    }

    private fun refreshMenuHeader() {
        drawer?.closeDrawer()
        header?.clear()
        setupProfileDrawer()
        setupAccountHeader()
        drawer?.header = header?.view!!
        drawer?.resetDrawerContent()
    }

    private fun updateUIAfterSignIn() {
        instantiateUser()
        if (!firebaseAuth.currentUser?.isEmailVerified!!) {
            //mFirebaseUser.sendEmailVerification();
        }
        currentProfile = checkCurrentProfileStatus()
        drawer?.updateItemAtPosition(currentProfile!!, 1)
        itemSignOut?.let { drawer?.addItemAtPosition(it, Constants.ITEM_SIGN_OUT) }
        drawer?.deselect(itemSignOut?.identifier!!)
        refreshMenuHeader()
    }

    private fun signOutUser() {
        //Sign out
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    Toast.makeText(this, "Signed out successfully ", Toast.LENGTH_SHORT).show()
                    Log.e("Auth : ", "Signed out successfully")

                    if (!isUserSignedIn()) {
                        itemSignIn?.let { drawer?.updateItemAtPosition(it, 1) }
                        drawer?.removeItem(itemSignOut?.identifier!!)
                        drawer?.deselect(itemSignIn?.identifier!!)
                        refreshMenuHeader()
                    } else {
                        //check if internet connectivity is there
                        Toast.makeText(this, "Sign out failed ", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    private fun startAuthUIActivity() {
        val providers: List<AuthUI.IdpConfig> = Arrays.asList(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
        )

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_launcher_foreground)
                .setAllowNewEmailAccounts(true)
                .setIsSmartLockEnabled(false)
                .build(), Constants.REQUEST_CODE_SIGN_IN)
    }
}
