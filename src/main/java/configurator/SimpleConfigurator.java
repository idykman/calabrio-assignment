package configurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Example representation of "old" configurator. it will be reused in AutoReloadConfigurator via
 * aggregation
 */

public class SimpleConfigurator implements Configurator {

  private final Properties properties;


  public SimpleConfigurator(String propFilePath) throws IOException {
    properties = readPropFile(propFilePath);
  }

  public String getConfig(String configName) {
    return properties.getProperty(configName);
  }

  private static Properties readPropFile(String propFilePath) throws IOException {
    InputStream is = null;
    Properties properties = new Properties();
    try {
      is = new FileInputStream(propFilePath);
      properties.load(new FileInputStream(propFilePath));
    } finally {
      if (null != is) {
        is.close();
      }
    }
    return properties;
  }
}
