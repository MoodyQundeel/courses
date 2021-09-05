import edu.duke.*;
import java.io.*;

public class ImageProcessor {
    public ImageResource convertGrey(ImageResource in) {
        ImageResource out = new ImageResource(in.getWidth(), in.getHeight());
        for (Pixel pixel : out.pixels()) {
            Pixel inPixel = in.getPixel(pixel.getX(), pixel.getY());
            int average = (inPixel.getRed() + inPixel.getGreen() + inPixel.getBlue()) / 3;
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        return out;
    }
    
    public void convertManyGrey() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = convertGrey(inImage);
            String fname = inImage.getFileName();
            String newFileName = "grey-" + fname;
            outImage.setFileName(newFileName);
            outImage.save();
        }
    }
    
    public ImageResource Invert(ImageResource in) {
        ImageResource out = new ImageResource(in.getWidth(), in.getHeight());
        for (Pixel pixel : out.pixels()) {
            Pixel inPixel = in.getPixel(pixel.getX(), pixel.getY());
            int outRed = 255 - inPixel.getRed();
            int outGreen = 255 - inPixel.getGreen();
            int outBlue = 255 - inPixel.getBlue();
            pixel.setRed(outRed);
            pixel.setGreen(outGreen);
            pixel.setBlue(outBlue);
        }
        return out;
    }
    
    public void InvertMany() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            ImageResource outImage = Invert(inImage);
            String fname = inImage.getFileName();
            String newFileName = "inverted-" + fname;
            outImage.setFileName(newFileName);
            outImage.save();
        }
    }
    
    public void test() {
        convertManyGrey();
        InvertMany();
    }
}
