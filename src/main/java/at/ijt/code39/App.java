package at.ijt.code39;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.BufferedImageLuminanceSource;
import com.google.zxing.Code39Reader;
import com.google.zxing.HybridBinarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;

public class App {
	public static void main(String[] args) throws Exception {

		if (args.length != 2) {
			System.err.println("error: No output file specified!\n");
			System.err.println("usage:\n\tjava at.ijt.code39.App <inputtext> <outputfile>");
			System.err.println("example:\n\tjava at.ijt.code39.App BARCODE123 /tmp/barcode.png");

			System.exit(1);
		}
		
		String inputText = args[0];
		String outputFile = args[1];

		write(inputText, outputFile);

		String textRead = read(outputFile);
		
		if (textRead.equals(inputText)) {
			System.out.println("The barcode " + textRead + " was written and read correctly.");
		}
	}

	private static String read(String inputFile) throws Exception {
		Path imagePath = Paths.get(inputFile);

		BufferedImage image = ImageIO.read(imagePath.toFile());

		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		Code39Reader reader = new Code39Reader();

		Result result = reader.decode(bitmap);

		return result.getText();
	}

	private static void write(String inputText, String outputFile) throws IOException {
		BufferedImage barcodeImg = Code39.createBarcodeImage(inputText);

		File outputfile = new File(outputFile);
		String formatName = detExtension(outputFile);

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
