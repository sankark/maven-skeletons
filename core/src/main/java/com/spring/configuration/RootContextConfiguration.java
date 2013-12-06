package com.spring.configuration;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

@Configuration
public class RootContextConfiguration {

	@Bean(name = "defaultResourceLoader")
	public DefaultResourceLoader defaultResourceLoader() {
		return new DefaultResourceLoader();
	}

	@Bean(name = "applicationConfig")
	   public Config applicationConfig() {
	      DefaultResourceLoader resourceLoader = defaultResourceLoader();

	      String environment = firstNonNull(sysProperty("system.environment"), "test");
	      Iterable<Properties> properties = filter(newArrayList(
	            loadProperties("classpath:conf/default.properties"),
	            loadProperties("classpath:conf/" + environment + ".properties")),
	            Predicates.notNull());
	      if (Iterables.isEmpty(properties)) {
	         throw new FatalBeanException("Could not create config. No properties found.");
	      }
	      return new MappedConfig(properties);
	   }

	private Properties loadProperties(String location) {
		try {
			PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
			factoryBean.setLocation(defaultResourceLoader().getResource(
					location));
			factoryBean.afterPropertiesSet();
			return factoryBean.getObject();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Get the value of a property defined as an environment variable. If not
	 * present, falls back to check for a system property.
	 */
	private String sysProperty(String name) {
		String result = System.getenv(name);
		if (result == null) {
			result = System.getProperty(name);
		}
		return result;
	}

	@Bean(name = "awsCredentials")
	   public AWSCredentials awsCredentials() {
	      Config config = applicationConfig();

	      String swfAccessId = config.getProperty(ConfigKeys.AWS_ACCESS_ID_KEY);
	      String swfSecretKey = config.getProperty(ConfigKeys.AWS_SECRET_KEY_KEY);
	      return new BasicAWSCredentials(swfAccessId, swfSecretKey);
	 }

	@Bean(name = "amazonS3Client")
	public AmazonS3Client amazonS3Client() {
		return new AmazonS3Client(awsCredentials());
	}
	@Bean(name = "amazonEC2Client")
	public AmazonEC2Client amazonEC2Client() {
		return new AmazonEC2Client(awsCredentials());
	}
	@Bean(name = "simpleWorkflow")
	public AmazonSimpleWorkflow amazonSimpleWorkflow() {
		/*
		 * int socketTimeout; Iterable<Properties> config = applicationConfig();
		 * try { socketTimeout =
		 * Integer.parseInt(config.getProperty(ConfigKeys.SWF_SOCKET_TIMOUT_KEY
		 * )); } catch(NumberFormatException e) { socketTimeout = 30 * 1000; }
		 * ClientConfiguration clientConfiguration = new
		 * ClientConfiguration().withSocketTimeout(socketTimeout);
		 * AmazonSimpleWorkflow client = new
		 * AmazonSimpleWorkflowClient(awsCredentials(), clientConfiguration);
		 * client
		 * .setEndpoint(config.getProperty(ConfigKeys.SWF_SERVICE_URL_KEY));
		 */
		return null;
	}
}
