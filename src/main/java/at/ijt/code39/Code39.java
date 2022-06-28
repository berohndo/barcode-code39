package at.ijt.code39;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Code39 {
    public static Map<Character, String> CHARS = new HashMap<>();

    static {
        // every character starts with black
        // 1px space (white) between characters
        // (N)arrow = 1px
        // (W)ide = 2px
        CHARS.put('*', "NWNNWNWNN");
        CHARS.put('0', "NNNWWNWNN");
        CHARS.put('1', "WNNWNNNNW");
        CHARS.put('2', "NNWWNNNNW");
        CHARS.put('3', "WNWWNNNNN");
        CHARS.put('4', "NNNWWNNNW");
        CHARS.put('5', "WNNWWNNNN");
        CHARS.put('6', "NNWWWNNNN");
        CHARS.put('7', "NNNWNNWNW");
        CHARS.put('8', "WNNWNNWNN");
        CHARS.put('9', "NNWWNNWNN");
        CHARS.put('A', "WNNNNWNNW");
        CHARS.put('B', "NNWNNWNNW");
        CHARS.put('C', "WNWNNWNNN");
        CHARS.put('D', "NNNNWWNNW");
        CHARS.put('E', "WNNNWWNNN");
        CHARS.put('F', "NNWNWWNNN");
        CHARS.put('G', "NNNNNWWNW");
        CHARS.put('H', "WNNNNWWNN");
        CHARS.put('I', "NNWNNWWNN");
        CHARS.put('J', "NNNNWWWNN");
        CHARS.put('K', "WNNNNNNWW");
        CHARS.put('L', "NNWNNNNWW");
        CHARS.put('M', "WNWNNNNWN");
        CHARS.put('N', "NNNNWNNWW");
        CHARS.put('O', "WNNNWNNWN");
        CHARS.put('P', "NNWNWNNWN");
        CHARS.put('Q', "NNNNNNWWW");
        CHARS.put('R', "WNNNNNWWN");
        CHARS.put('S', "NNWNNNWWN");
        CHARS.put('T', "NNNNWNWWN");
        CHARS.put('U', "WWNNNNNNW");
        CHARS.put('V', "NWWNNNNNW");
        CHARS.put('W', "WWWNNNNNN");
        CHARS.put('X', "NWNNWNNNW");
        CHARS.put('Y', "WWNNWNNNN");
        CHARS.put('Z', "NWWNWNNNN");
        CHARS.put('–', "NWNNNNWNW");
        CHARS.put('.', "WWNNNNWNN");
        CHARS.put(' ', "NWWNNNWNN");
        CHARS.put('$', "NWNWNWNNN");
        CHARS.put('/', "NWNWNNNWN");
        CHARS.put('+', "NWNNNWNWN");
        CHARS.put('%', "NNNWNWNWN");
    }

    public static BufferedImage createBarcodeImage(String input) {
        String encodedText = encode(input);
        int codeLength = encodedText.length();

        final int SPACE_LEFT_RIGHT = 50;
        final int PX_PER_CHAR = 12 + 1;

        final int WIDTH = 2 * SPACE_LEFT_RIGHT + (input.length() + 2) * PX_PER_CHAR;
        final int HEIGHT = WIDTH / 2;

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                image.setRGB(x, y, Color.WHITE.getRGB());
            }
        }

        for (int y = 0; y < HEIGHT; y++) {
            int x = SPACE_LEFT_RIGHT;
            boolean black = true;
            for (int i = 0; i < codeLength; i++) {
                if (i != 0 && i % 9 == 0) {
                    x++;
                    black = true;
                }

                int width = encodedText.charAt(i) == 'W' ? 2 : 1;
                for (int w = 0; w < width; w++) {
                    x++;
                    if (black) {
                        image.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
                black = !black;
            }
        }

        return image;
    }

    private static String encode(String input) {
        String encoded = star();

        for (int i = 0; i < input.length(); i++) {
            encoded += character(input.charAt(i));
        }

        encoded += star();

        return encoded;
    }

    private static String star() {
        return Code39.CHARS.get('*');
    }

    private static String character(char s) {
        String enc = Code39.CHARS.get(s);

        if (enc == null) {
            throw new IllegalArgumentException("Unsupported character: " + s);
        }

        return enc;
    }
}
