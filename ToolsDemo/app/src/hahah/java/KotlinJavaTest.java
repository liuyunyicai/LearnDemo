import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author: Created By nealkyliu
 * @date: 2018/7/15
 **/
public class KotlinJavaTest {
    public interface TestInterface {
        public void test1();
    }

    public static void doSomething(TestInterface test) {
        test.test1();
    }

    public static void main(String[] args) {
        doSomething(() -> System.out.println("HelloWorld"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MethodHandle getToStringMH() {
        MethodHandle mh = null;
        MethodType mt = MethodType.methodType(String.class);
        MethodHandles.Lookup lk = MethodHandles.lookup();

        try {
            mh = lk.findVirtual(getClass(), "toString", mt);
        } catch (NoSuchMethodException | IllegalAccessException mhx) {
            throw (AssertionError)new AssertionError().initCause(mhx);
        }

        return mh;
    }
}
