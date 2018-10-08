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
        String pictureFileName = input.nextLine();
        File pictureFile = new File(pictureFileName);
        String pictureName = pictureFile.getName().replaceFirst("[.][^.]+$", "");
        try {
            picture = ImageIO.read(pictureFile);
        } catch (IOException e) {
            System.out.println("Error - File not found");
            e.printStackTrace();
        }
        if (picture.getWidth() != picture.getHeight()) {
            System.out.println("Error - Picture is not a square");
            System.exit(0);
        }

        //TODO: Add support for non-square images and walls
        System.out.print("Side Length: ");
        int emojiLength = input.nextInt();
        int actualLength = picture.getWidth() / emojiLength;
        emojis = new BufferedImage[emojiLength][emojiLength];
        for (int r = 0; r < emojis.length; r++)
            for (int c = 0; c < emojis[r].length; c++)
                emojis[c][r] = picture.getSubimage(actualLength * r, actualLength * c, actualLength, actualLength);

        if (!(new File(pictureName + "/")).mkdirs()) {
            System.out.println("Error - Directory creation failed");
            System.exit(0);
        }

        for (int r = 0; r < emojis.length; r++) {
            for (int c = 0; c < emojis[r].length; c++) {
                try {
                    ImageIO.write(emojis[r][c], "png", new File(pictureName + "/" + pictureName + "[" + (r + 1) + "]" + "[" + (c + 1) + "]" + ".png"));
                } catch (IOException e) {
                    System.out.println("Error - Unable to write file");
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Emojis output in format " + pictureName + "/" + pictureName + "[row][column].png");
    }

}
