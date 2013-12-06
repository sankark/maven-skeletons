package url_image_upload;

import com.amazonaws.util.json.JSONObject;
import common.http.RestApiHttpClient;
import common.util.ResponseParser;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Map;

public class UrlImageUploadStepDefs {

   public RestApiHttpClient restApiclient = new RestApiHttpClient();
    
	HttpClient client = null;
	
	HttpPost httpPost = null;
	
	String response = null;

   String callbackUrl = null;

   HttpGet httpGet = null;


   @Given("^the REST API is available for URL upload \"([^\"]*)\"$")
   public void the_REST_API_is_available_for_URL_upload(String arg1) throws Throwable {
      client = restApiclient.createDefaultHttpClient();
      httpPost = (HttpPost) restApiclient.createHttpObject(arg1,"POST");
   }


   @When("^API process the URL upload request \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
   public void API_process_the_URL_upload_request(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {

      callbackUrl = arg3;
      ArrayList formparams = new ArrayList();
      formparams.add(new BasicNameValuePair("priority", arg1));
      formparams.add(new BasicNameValuePair("downloadurl", arg2));
      formparams.add(new BasicNameValuePair("callbackurl", arg3));
      formparams.add(new BasicNameValuePair("jobtype", arg4));
      formparams.add(new BasicNameValuePair("expiration", arg5));
      formparams.add(new BasicNameValuePair("MIME", arg6));
      formparams.add(new BasicNameValuePair("metadata","{\"test\":\"test\",\"test2\":\"test2\"}"));
      UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
      httpPost.setEntity(entity);

      response = restApiclient.executeRequest(httpPost, client);
   }

   @Then("^I should see the response with JobId and FileURL$")
   public void I_should_see_the_response_with_JobId_and_FileURL() throws Exception
   {
	   Map<String, String> jsonvalueMap = ResponseParser.parseJson(response);		
	   String runId = jsonvalueMap.get("jobId");
	   String fileUrl = jsonvalueMap.get("fileUrl");
      Assert.assertNotNull(runId);
      Assert.assertNotNull(fileUrl);
   }

   @Then("^I should see the notification success in the callback url$")
   public void I_should_see_the_notification_success_in_the_callback_url() throws Exception
   {

      Map<String, String> jsonvalueMap = ResponseParser.parseJson(response);
      String runId = jsonvalueMap.get("jobId");
      String fileUrl = jsonvalueMap.get("fileUrl");
    //Logic to get the status
      httpGet = (HttpGet) restApiclient.createHttpObject(callbackUrl+"?fileUrl="+fileUrl,"GET");

      if(!callbackUrl.equals("")) {
         response = restApiclient.executeRequest(httpGet, client);
         //Get the callback url response
         Map<String, String> callbackurlMap = ResponseParser.parseJson(response);
         String status = callbackurlMap.get("status");
         Assert.assertEquals("success", status);
      } else {
         Assert.fail();
      }
   }

   @Then("^I should see the notification failure in the callback url$")
   public void I_should_see_the_notification_failure_in_the_callback_url() throws Exception
   {

      Map<String, String> jsonvalueMap = ResponseParser.parseJson(response);
      String runId = jsonvalueMap.get("jobId");
      String fileUrl = jsonvalueMap.get("fileUrl");

      //HttpGet object creation for callback server
      httpGet = (HttpGet) restApiclient.createHttpObject(callbackUrl+"?fileUrl="+fileUrl,"GET");

      //Logic to get the status
      if(!callbackUrl.equals("")) {

         response = restApiclient.executeRequest(httpGet, client);

         Assert.assertEquals("failure", response);

      } else {
         Assert.fail();
      }


   }
   
}