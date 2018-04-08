package `in`.webdevlabs.campuscommerce.model

/**
 * Created by yolo on 7/4/18.
 */
data class Post(
        val pid: String? = null,
        val name: String? = null,
        val price: Int? = null,
        val uid: String? = null,
        val time: String? = null,
        val type: PostType? = null,
        val tags: List<String>? = null
)

enum class PostType(val type: String) {
    SELL("SELL"),
    RENT("RENT")
}