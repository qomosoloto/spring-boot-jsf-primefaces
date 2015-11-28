package com.shenbian.admin.util.base;

import java.util.UUID;

/**
 * Created by qomo on 15-10-13.
 */
public class CodeSnGenerator {
    private static final String[] SALT_SOURCE_ARRAY = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     *
     * @return
     */
    public static String codeSnGenerator() {
        StringBuffer shortBuffer = new StringBuffer();

        int j = 0;
        while (j < 3) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            System.out.println(uuid);
            for (int i = 0; i < 8; i++) {
                String str = uuid.substring(i * 4, i * 4 + 4);
                int x = Integer.parseInt(str, 16);
                shortBuffer.append(SALT_SOURCE_ARRAY[x % 0x3E]);
            }
            j++;
        }
        return shortBuffer.toString().substring(0, 20);
    }
}
