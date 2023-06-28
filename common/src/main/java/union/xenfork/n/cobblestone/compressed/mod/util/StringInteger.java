package union.xenfork.n.cobblestone.compressed.mod.util;

public class StringInteger {
    public static String addOne(String integer) {
        return add(integer, 1);
    }

    public static String minusOne(String integer) {
        StringBuilder temp = new StringBuilder();
        int minus = 1;
        int borrow = 0;
        for (int length = integer.length() - 1; length > 0; length--) {
            try {
                Integer.parseInt(String.valueOf(integer.charAt(length)));
                var i = Integer.parseInt(String.valueOf(integer.charAt(length)));
                if (i == 0) {
                    temp.insert(0, (i - borrow + 10 - minus));
                    borrow = 1;
                    minus = 0;
                } else {
                    temp.insert(0, (i - borrow - minus));
                    borrow = 0;
                    minus = 0;
                }

            } catch (NumberFormatException ignored) {}
        }
        return temp.toString();
    }
    public static String add(String integer, int add) {
        StringBuilder temp = new StringBuilder();
        int math = add;
        for (int length = integer.length() - 1; length >= 0; length--) {
            try {
                Integer.parseInt(String.valueOf(integer.charAt(length)));
                int i = Integer.parseInt(String.valueOf(integer.charAt(length)));
                temp.insert(0, (i + math) % 10);
                math = (i + math) / 10;
            } catch (NumberFormatException ignored) {}
        }
        return temp.toString();
    }
}
