package `in`.webdevlabs.campuscommerce.model

/**
 * Created by yolo on 7/4/18.
 */
data class Post(
        val pid: String,
        val name: String,
        val price: Int,
        val uid: String,
        val time: String,
        val type: PostType,
        val tags: List<String>
)

enum class PostType(val type: String) {
    SELL("sell"),
    RENT("rent")
}