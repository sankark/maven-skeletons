package common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;

import common.util.ResponseParser;

public class RestApiHttpClient {
	
    public Object createHttpObject(String url, String type) throws  Exception {
    	Object httpObject = null;
    	if(type.equals("GET")) {
    		httpObject = new HttpGet (url) ;
    	} else if(type.equals("PUT")) {
    		httpObject = new HttpPut (url) ;
    	} else if(type.equals("DELETE")) {
    		httpObject = new HttpDelete (url) ;
    	} else if(type.equals("POST")) {
    		httpObject = new HttpPost (url) ;
    	}
    	return httpObject;
    }
    
    
	public static HttpClient createDefaultHttpClient() throws Exception {
		HttpClient client = new DefaultHttpClient() ;
		return client;
	}
    
    
    
    public static String executeRequest(HttpRequestBase requestBase, HttpClient client) throws Exception {
        String responseString = "" ;
 
        InputStream responseStream = null ;
        try{
            HttpResponse response = client.execute(requestBase) ;
            if (response != null){
                HttpEntity responseEntity = response.getEntity() ;
 
                if (responseEntity != null){
                    responseStream = responseEntity.getContent() ;
                    if (responseStream != null){
                        BufferedReader br = new BufferedReader (new InputStreamReader (responseStream)) ;
                        String responseLine = br.readLine() ;
                        String tempResponseString = "" ;
                        while (responseLine != null){
                            //tempResponseString = tempResponseString + responseLine + "\n" ;
                           tempResponseString = tempResponseString + responseLine;
                            responseLine = br.readLine() ;
                        }
                        br.close() ;
                        if (tempResponseString.length() > 0){
                            responseString = tempResponseString ;
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (ClientProtocolException e) {
           throw e;
        } catch (IllegalStateException e) {
           throw e;
        } catch (IOException e) {
           throw e;
        }finally{
            if (responseStream != null){
                try {
                    responseStream.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       // client.getConnectionManager().shutdown() ;
 
        return responseString ;
    }


}
