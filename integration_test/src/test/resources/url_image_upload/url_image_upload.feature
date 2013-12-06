Feature:Process URL image upload into AWS S3

  Scenario: Verify the API response for valid URL image upload
  Given the REST API is available for URL upload "http://10.142.2.207:8080/callbackurl/rest/download"
  When API process the URL upload request "high" "https://s3.amazonaws.com/cloudimaging-dev/15225988-73e0-430b-85ef-beacfdd516f" "http://10.142.2.207:8080/callbackurl/rest/status/query" "upload" "5" "Image/jpeg"
  Then I should see the response with JobId and FileURL

  Scenario: Verify the API response for invalid URL image upload
  Given the REST API is available for URL upload "http://10.142.2.207:8080/callbackurl/rest/download"
  When API process the URL upload request "high" "https://s3xxxxxxxx.amazonaws.com/cloudimaging-dev/15225988-73e0-430b-85ef-beacfdd516f" "http://10.142.2.207:8080/callbackurl/rest/status/query" "upload" "5" "Image/jpeg"
  Then I should see the response with JobId and FileURL

  Scenario: Verify the API response for valid URL through callback URL
  Given the REST API is available for URL upload "http://10.142.2.207:8080/callbackurl/rest/download"
  When API process the URL upload request "high" "https://s3.amazonaws.com/cloudimaging-dev/15225988-73e0-430b-85ef-beacfdd516f" "http://10.142.2.207:8080/callbackurl/rest/status/query" "upload" "5" "Image/jpeg"
  Then I should see the notification success in the callback url

  Scenario: Verify the API response for invalid URL through callback URL
  Given the REST API is available for URL upload "http://10.142.2.207:8080/callbackurl/rest/download"
  When API process the URL upload request "high" "https://s3xxxxxxxxx.amazonaws.com/cloudimaging-dev/15225988-73e0-430b-85ef-beacfdd516f" "http://10.142.2.207:8080/callbackurl/rest/status/query" "upload" "5" "Image/jpeg"
  Then I should see the notification failure in the callback url

