import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static BufferedImage picture;
    private static BufferedImage[][] emojis;

    //TODO: Add ability to use args
    public static void main(String[] args) {
        System.out.println("EmojiWall by PotatoCurry");

        Scanner input = new Scanner(System.in);
        System.out.print("Picture File: ");
        String pictureFile = input.nextLine();
        try {
            picture = ImageIO.read(new File(pictureFile));
        } catch (IOException e) {
            System.out.println("Error - File not found");
            e.printStackTrace();
        }
        if (picture.getWidth() != picture.getHeight()) {
            System.out.println("Error - Picture is not a square");
            System.exit(0);
        }

        //TODO: Add support for non-square imgaes and walls
        System.out.print("Side Length: ");
        int emojiLength = input.nextInt();
        int actualLength = picture.getWidth() / emojiLength;
        emojis = new BufferedImage[emojiLength][emojiLength];
        for (int r = 0; r < emojis.length; r++)
            for (int c = 0; c < emojis[r].length; c++)
                emojis[c][r] = picture.getSubimage(actualLength * r, actualLength * c, actualLength, actualLength);
        //output image
        //create folder
//        boolean success = (new File("potentially/outputFile")).mkdirs();
//        if (!success) {
//            System.out.println();
//        }

        for (int r = 0; r < emojis.length; r++) {
            for (int c = 0; c < emojis[r].length; c++) {
                try {
                    File outputFile = new File(pictureFile.replaceFirst("[.][^.]+$", "") + "[" + (r + 1) + "]" + "[" + (c + 1) + "]" + ".png");
                    ImageIO.write(emojis[r][c], "png", outputFile);
                } catch (IOException e) {
                    System.out.println("Error - Unable to write file");
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Emojis output in format " + pictureFile.replaceFirst("[.][^.]+$", "") + "[row][column].png");
    }

}
