/**
 * @author: Created By nealkyliu
 * @date: 2018/7/15
 **/
open class KotlinTest {

    companion object {
        const val id = 10
        const val str = "haha$id"

        fun hello() {
        }
    }

    inline fun hello1() : Int {
        var longInt = 10;
        longInt++
        return longInt
    }

    fun fun2() : Int {
        return hello1()
    }
}