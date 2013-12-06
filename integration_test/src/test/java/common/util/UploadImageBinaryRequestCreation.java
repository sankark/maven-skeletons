package common.util;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: ADhanushko
 * Date: 10/20/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadImageBinaryRequestCreation {

   public HttpPost createHttpRequestWithInputs(HttpPost postRequest, File file, String metadata) throws Exception {
      try{
         MultipartEntity multiPartEntity = new MultipartEntity () ;
         FileBody fileBody = new FileBody(file) ;
         StringBody meta = new StringBody(metadata);
         multiPartEntity.addPart("file", fileBody) ;
         multiPartEntity.addPart("metadata", meta) ;
         postRequest.setEntity(multiPartEntity) ;
      } catch (UnsupportedEncodingException ex){
           throw ex;
      } catch(Exception e) {
           throw e;
      }
     return  postRequest;
   }


}
