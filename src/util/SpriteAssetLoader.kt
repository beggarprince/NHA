//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import java.nio.file.Paths
//import kotlin.io.path.readText
//
//data class Sprite(val name: String, val path: String)
//

// TODO implement later, Gradle is not in project and this adds no added functionality just increases modularity

//fun spriteLoader(filePath: String) {
//    try {
//        val path = Paths.get(filePath)
//        val jsonContent = path.readText()
//
//        val gson = Gson()
//        val spriteListType = object : TypeToken<List<Sprite>>() {}.type
//        val sprites: List<Sprite> = gson.fromJson(jsonContent, spriteListType)
//
//        sprites.forEach { sprite ->
//            loadImage(sprite.path)
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}
//
//fun loadImage(imagePath: String) {
//    // Assuming ImgLoader is a Kotlin object that handles image loading
//    val image = ImgLoader.getImageResource(imagePath)
//    println("Loaded image: $imagePath")
//}
//
//object ImgLoader {
//    fun getImageResource(path: String): Any {
//        // Dummy implementation for demonstration
//        return Object() // Replace with actual image loading logic
//    }
//}
//
//fun main() {
//    loadSprites("path/to/your/sprites.json")
//}
