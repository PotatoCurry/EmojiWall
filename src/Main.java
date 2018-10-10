import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static final String OUTPUT_TEMPLATE = "%1$s_%2$s_%3$s.png";

    private static BufferedImage readImage(String path) {
        BufferedImage image = null;
        try {
            File input = new File(path);
            image = ImageIO.read(input);
        } catch (IOException e) {
            System.err.println("Error reading file.");
            System.exit(0);
        }
        return image;
    }

    private static void verifyArgs(String... args) {
        boolean lenCheck = args.length == 2;
        boolean existCheck = new File(args[0]).isFile();
        boolean sizeCheck = args[1].matches("[-]?[0-9]*");
        if (lenCheck && existCheck && sizeCheck) return;
        if (!lenCheck)
            System.err.println("Usage: ./EmojiWall.jar <file_path> <grid_len>");
        if (!existCheck)
            System.err.println("Invalid file path.");
        if (!sizeCheck)
            System.err.println("Invalid grid size.");
        System.exit(0);
    }

    private static BufferedImage centerImage(BufferedImage image, int gridSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        int centerSize = ((Math.max(width, height) / gridSize) + 2) * gridSize;
        int centerX = (centerSize - width) >> 1;
        int centerY = (centerSize - height) >> 1;
        BufferedImage centeredImage = new BufferedImage(centerSize, centerSize, BufferedImage.TYPE_INT_ARGB_PRE);
        centeredImage.createGraphics().drawImage(image, centerX, centerY, null);
        return centeredImage;
    }

    private static void writeImages(BufferedImage image, String imageName, int gridSize) {
        int rowLen = image.getWidth() / gridSize;
        int colLen = image.getHeight() / gridSize;
        for (int n = 0, row = 0, col = 0; n <= Math.pow(gridSize, 2); row = n / gridSize, col = n % gridSize, n++) {
            BufferedImage output = image.getSubimage(row * rowLen, col * colLen, rowLen, colLen);
            File outputFile = new File(String.format(OUTPUT_TEMPLATE, imageName, col, row));
            try {
                ImageIO.write(output, "png", outputFile);
            } catch (IOException e) {
                System.err.printf("Unable to write file: %1$s\n", outputFile);
            }
        }
    }

    public static void main(String[] args) {
        verifyArgs(args);
        BufferedImage image = readImage(args[0]);
        int gridSize = Integer.parseInt(args[1]);
        String imageName = args[0].replaceAll("([.]+[\\w]*)", "");
        if (image.getWidth() % gridSize <= 0 && image.getHeight() % gridSize <= 0)
            image = centerImage(image, gridSize);
        writeImages(image, imageName, gridSize);
        System.out.println("Finished splitting image.");
    }
}
