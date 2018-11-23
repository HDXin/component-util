//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.atstudy.component.util;

import java.util.Random;

public class RandomUtil {
    private static final Integer DEFAULT_SIZE = 10;

    public RandomUtil() {
    }

    public static String randomString(Integer length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        if (length == null) {
            length = DEFAULT_SIZE;
        }

        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }
}
