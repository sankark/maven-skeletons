Feature:Image resize and serving

  Scenario: Verify the image resize for specific dimensions
  Given I request the image to be resized to a specific dimension "https://s3.amazonaws.com/manheim-development/acura2.jpg" "544" "408"
  When The image is resized and served
  Then I will see the image in the specified dimensions


