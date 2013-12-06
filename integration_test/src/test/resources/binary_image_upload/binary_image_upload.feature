Feature:Process binary image upload into AWS S3

  Scenario: binary image upload into AWS S3
  Given the REST API is available for binary upload "http://10.142.2.207:8080/rest_api-0.0.1-SNAPSHOT/rest/files"
  When API has processed the binary image for S3 upload
  Then I should see the response with fileurl and status and fileid

  Scenario: invalid binary image upload into AWS S3
    Given the REST API is available for binary upload "http://10.142.2.207:8080/rest_api-0.0.1-SNAPSHOT/rest/files"
  When API has processed the invalid binary image for S3 upload
  Then I should see the failure message back to application

  Scenario: binary image upload with metadata into AWS S3
    Given the REST API is available for binary upload "http://10.142.2.207:8080/rest_api-0.0.1-SNAPSHOT/rest/files"
  When API has processed the binary image for S3 upload
  Then I am able to query metadata with fileid

  Scenario: verify binary image upload into AWS S3
    Given the REST API is available for binary upload "http://10.142.2.207:8080/rest_api-0.0.1-SNAPSHOT/rest/files"
  When API has processed the binary image for S3 upload
  Then I am able to validate the existence of the image in AWS S3 through image service