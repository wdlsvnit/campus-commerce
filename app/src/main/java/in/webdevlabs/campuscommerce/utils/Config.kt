package `in`.webdevlabs.campuscommerce.utils

/**
 * Created by Shailesh351 on 8/4/18.
 */
object Config {

    // global topic to receive app wide push notifications
    const val TOPIC_GLOBAL = "global"

    // broadcast receiver intent filters
    const val REGISTRATION_COMPLETE = "registrationComplete"
    const val PUSH_NOTIFICATION = "pushNotification"

    // id to handle the notification in the notification tray
    const val NOTIFICATION_ID = 100

    const val SHARED_PREF = "ah_firebase"
}