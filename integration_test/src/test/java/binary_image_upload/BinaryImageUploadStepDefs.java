package binary_image_upload;

import common.http.RestApiHttpClient;
import common.util.FileOperations;
import common.util.ResponseParser;
import common.util.UploadImageBinaryRequestCreation;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Assert;
import java.io.File;

import java.util.Map;

public class BinaryImageUploadStepDefs {

   public RestApiHttpClient restApiclient = new RestApiHttpClient();

   HttpClient client = null;

   HttpPost httpPost = null;

   String response = null;

   String callbackUrl = null;

   HttpGet httpGet = null;

   private static final Log log = LogFactory.getLog(BinaryImageUploadStepDefs.class);

   @Given("^the REST API is available for binary upload \"([^\"]*)\"$")
   public void the_REST_API_is_available_for_binary_upload(String arg1) throws Throwable {
      client = restApiclient.createDefaultHttpClient();
      httpPost = (HttpPost) restApiclient.createHttpObject(arg1,"POST");
      log.info("Given REST API is available for binary upload called successfully");
   }
   @When("^API has processed the binary image for S3 upload$")
   public void API_has_processed_the_binary_image_for_S3_upload() throws Throwable {
      FileOperations fileOperations = new FileOperations();
      File file = fileOperations.createFileFromResource("acura.jpg");
      String metadata ="{\"test\":\"test\",\"test2\":\"test2\"}";

      UploadImageBinaryRequestCreation uploadImageBinaryRequestCreation = new UploadImageBinaryRequestCreation();
      uploadImageBinaryRequestCreation.createHttpRequestWithInputs(httpPost, file, metadata);

      response = restApiclient.executeRequest(httpPost, client);
      log.info("API has processed the binary image for S3 upload Done:"+response);
      // throw new cucumber.runtime.PendingException();
   }

   @Then("^I should see the response with fileurl and status and fileid$")
   public void I_should_see_the_response_with_fileurl_and_status_and_fileid() throws Exception
   {
      Map<String, String> jsonvalueMap = ResponseParser.parseJson(response);
      String status = jsonvalueMap.get("status");
      String fileId = jsonvalueMap.get("fileId");
      String fileUrl = jsonvalueMap.get("url");
      Assert.assertEquals("success", status);
      Assert.assertNotNull(fileId);
      Assert.assertNotNull(fileUrl);
      log.info("I Should see the response with fileurl "+fileUrl);
      log.info("I Should see the response with  status "+status);
      log.info("I Should see the response with  fileId:"+fileId);
   }

   @When("^API has processed the invalid binary image for S3 upload$")
   public void API_has_processed_the_invalid_binary_image_for_S3_upload() throws Throwable {
      FileOperations fileOperations = new FileOperations();
      File file = fileOperations.createFileFromResource("emptyfile.jpg");
      String metadata ="{\"test\":\"test\",\"test2\":\"test2\"}";

      UploadImageBinaryRequestCreation uploadImageBinaryRequestCreation = new UploadImageBinaryRequestCreation();
      uploadImageBinaryRequestCreation.createHttpRequestWithInputs(httpPost, file, metadata);

      response = restApiclient.executeRequest(httpPost, client);
      log.info("Invalid Binary Image Response::"+response);
      // throw new cucumber.runtime.PendingException();
   }


   @Then("^I should see the failure message back to application$")
   public void I_should_see_the_failure_message_back_to_application() throws Exception
   {
   Map<String, String> jsonvalueMap = ResponseParser.parseJson(response);

   String failureMessage = jsonvalueMap.get("message");

   log.info("I should see the failure message back to application:"+failureMessage);
   Assert.assertNotNull(failureMessage);
   Assert.assertEquals("No file content included in request", failureMessage);
   }
   @Then("^I am able to query metadata with fileid$")
   public void I_am_able_to_query_metadata_with_fileid() throws Exception
   {
      /*Logic to be implement once the query metadata service is implemented */
   }
   @Then("^I am able to validate the existence of the image in AWS S3 through image service$")
   public void I_am_able_to_validate_the_existence_of_the_image_in_AWS_S3_through_image_service() throws Exception
   {
      /*Logic to be implement once the image serving service is implemented */

   }


}