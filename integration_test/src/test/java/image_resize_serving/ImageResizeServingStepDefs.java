package image_resize_serving;

import common.http.RestApiHttpClient;
import common.util.FileOperations;
import common.util.ImageOperations;
import common.util.ResponseParser;
import common.util.UploadImageBinaryRequestCreation;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Assert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

public class ImageResizeServingStepDefs {

   String response = null;

   String url = null;

   BufferedImage bufferedImage = null;

   String width = null;

   String height = null;

   @Given("^I request the image to be resized to a specific dimension \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
   public void I_request_the_image_to_be_resized_to_a_specific_dimension(String arg1, String arg2, String arg3) throws Throwable {
     // url = arg1+"/"+arg2+"/"+arg3;
      url = arg1;
      width = arg2;
      height = arg3;
   }
   @When("^The image is resized and served$")
   public void The_image_is_resized_and_served() throws Throwable {
      bufferedImage = ImageOperations.fetchImage(url);
   }

   @Then("^I will see the image in the specified dimensions$")
   public void I_will_see_the_image_in_the_specified_dimensions() throws Exception
   {

      Assert.assertEquals(bufferedImage.getWidth(), Integer.parseInt(width));
      Assert.assertEquals(bufferedImage.getHeight(), Integer.parseInt(height));
   }
}

