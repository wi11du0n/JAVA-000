package Week_01;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
             Class c = new HelloClassLoader().findClass("jvm.Hello");
             Method d = c.getMethod("hello");
             d.invoke(c.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // File f = new File("Week_01/Hello2/Hello.xlass");
            byte[] bytes = Files.readAllBytes(Paths.get("/Users/wi11du0n/Sources/Github/JAVA-000/Week_01/Hello2/Hello.xlass"));
            return defineClass(null, decode(bytes), 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private byte[] decode(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }
        return bytes;
    }
}
