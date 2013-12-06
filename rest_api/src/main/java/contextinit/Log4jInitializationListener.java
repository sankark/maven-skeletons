package contextinit;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public final class Log4jInitializationListener implements
		ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try (InputStream propertiesStream = getClass().getClassLoader()
				.getResource("log4j.properties").openStream()) {
			Properties props = new Properties();
			String logHome = System.getProperty("log.home",
					System.getProperty("user.home") + "/log");
			props.setProperty("log.home", logHome);
			System.out.println("Initilizing log4j with logs dir: " + logHome);

			props.load(propertiesStream);
			PropertyConfigurator.configure(props);
		} catch (Exception e) {
			BasicConfigurator.configure();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
