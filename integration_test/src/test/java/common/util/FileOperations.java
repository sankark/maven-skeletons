package common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: ADhanushko
 * Date: 10/20/13
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileOperations {

   public File createFileFromResource(String imageName) throws Exception {
      File file=null;
      try {
         file = new File(getClass().getClassLoader().getResource(imageName).toURI());
      } catch (URISyntaxException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (Exception e) {
         throw e;
      }
      return  file;
   }
}
