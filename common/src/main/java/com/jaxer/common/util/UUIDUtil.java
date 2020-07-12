package com.jaxer.common.util;

import java.util.UUID;

/**
 * @author jaxer
 * @date 2020/6/29 4:36 PM
 */
public class UUIDUtil {
    private static char[] charDigits = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * 生成10位UUID
     */
    public static String getID() {
        UUID uuid = UUID.randomUUID();

        // 改变uuid的生成规则
        return convertToHashStr(uuid.getMostSignificantBits(), 5) +
                convertToHashStr(uuid.getLeastSignificantBits(), 5);
    }

    /**
     * 转换目前32位UUID为10位UUID
     */
    public static String convertID(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        // 改变uuid的生成规则
        return convertToHashStr(uuid.getMostSignificantBits(), 5)
                + convertToHashStr(uuid.getLeastSignificantBits(), 5);
    }

    private static String convertToHashStr(long hid, int len) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char c = charDigits[(int) ((hid & 0xff) % charDigits.length)];
            sb.append(c);
            hid = hid >> 6;
        }

        return sb.toString();
    }
}
