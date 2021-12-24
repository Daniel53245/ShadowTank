import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//方法二代码参考 https://blog.csdn.net/jhope/article/details/80830598
//方法二在将图像灰度化是更加明亮，保留了更多地光影，明暗表现。
//方法二RGB加权参数 R*0.3  G*0.59  B*.011

public class greyImage {
    //在网上找到的将 ARGB 替换为RGB对象的方法
    private static int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }

    //cool
    public static void generateGreyImage(String imagePath) throws IOException {
        //open the image as a file object
        File coloredImage = new File(imagePath);

        BufferedImage image = ImageIO.read(coloredImage);
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();

        BufferedImage grayImage = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage grayImage2 = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_BYTE_GRAY);

        for(int x = 0; x<imageWidth;x++){
            for (int y = 0;y<imageHeight;y++){
                int RGB = image.getRGB(x,y);
                //方法一处理图像
                grayImage.setRGB(x,y,RGB);

                //方法二处理图像
                int color = image.getRGB(x,y);
                //这一段是黑盒子 不知道是怎么处理的
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;
                int gray = ( int) ( 0.3 * r + 0.59 * g + 0.11 * b);
                int newPixel = colorToRGB( 255, gray, gray, gray);
                grayImage.setRGB(x, y, newPixel);
                grayImage2.setRGB(x,y,RGB);
            }
        }
        File outPutPicture = new File("greyScalePictureGenrate/greyPinture"+ "/method1.png");
        ImageIO.write(grayImage, "png", outPutPicture);

        File outPutPicture2 = new File("greyScalePictureGenrate/greyPinture"+ "/method2.png");
        ImageIO.write(grayImage, "png", outPutPicture2);
    }


    public static void main(String[] args) throws IOException {
        generateGreyImage("greyScalePictureGenrate/coloredPngImage.png");
    }
}
