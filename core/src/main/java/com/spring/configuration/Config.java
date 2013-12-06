package com.spring.configuration;

public interface Config {
   /**
    * Get the value of a property in the configuration.
    *
    * @param name The parameter name. Cannot be {@code null}.
    *
    * @return The parameter value, or {@code null} if there is no value for the property.
    */
   String getProperty(String name);

   /**
    * Get the value of a property in the configuration, or a supplied default if it does not exist.
    *
    * @param name The parameter name. Cannot be {@code null}.
    *
    * @return The parameter value, or {@code defaultValue} if there is no value for the property.
    */
   String getProperty(String name, String defaultValue);
}
