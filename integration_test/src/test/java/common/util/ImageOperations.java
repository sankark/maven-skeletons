package common.util;


import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Created with IntelliJ IDEA.
 * User: ADhanushko
 * Date: 10/21/13
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageOperations {

   public static BufferedImage fetchImage(String binaryImageEndPoint) throws Exception {
      BufferedImage image = null;
      try {
         URL url = new URL(binaryImageEndPoint);
         image = ImageIO.read(url);

      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return  image;
   }
}
