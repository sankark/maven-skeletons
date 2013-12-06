package porthole;

import org.junit.Before;

import com.manheim.cloudimaging.restapi.jersey.Log4jInitializationListener;

public class GlobalHooks {
   private static boolean log4jInitialized;

   @Before
   public void beforeAll() {
      if(!log4jInitialized) {
         System.out.println("*************************");
         System.out.println("*************************");
         System.out.println("Initializing log4j");
         new Log4jInitializationListener().contextInitialized(null);
         System.out.println("*************************");
         System.out.println("*************************");
         log4jInitialized = true;
      }
   }
}