package configurator;

import java.io.File;
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


  public SimpleConfigurator(File file) throws IOException {
    properties = readPropFile(file);
  }

  public String getConfig(String configName) {
    return properties.getProperty(configName);
  }

  private static Properties readPropFile(File file) throws IOException {
    InputStream is = null;
    Properties properties = new Properties();
    try {
      is = new FileInputStream(file);
      properties.load(is);
    } finally {
      if (null != is) {
        is.close();
      }
    }
    return properties;
  }
}
