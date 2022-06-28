package at.ijt.code39;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {
        
        if (args.length != 2) {
            System.err.println("error: No output file specified!\n");
            System.err.println("usage:\n\tjava at.ijt.code39.App <inputtext> <outputfile>");
            System.err.println("example:\n\tjava at.ijt.code39.App BARCODE123 /tmp/barcode.png");

            System.exit(1);
        }
        
        BufferedImage barcodeImg = Code39.createBarcodeImage(args[0]);

        File outputfile = new File(args[1]);
        String formatName = detExtension(args[1]);
        
        System.out.println(formatName);

        ImageIO.write(barcodeImg, formatName, outputfile);
    }

    private static String detExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "png"; // default
        }
        return name.substring(lastIndexOf + 1);
    }
}
