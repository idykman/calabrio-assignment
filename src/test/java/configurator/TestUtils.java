package configurator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

public class TestUtils {

  public static final String testFile = "config.properties";


  public static void updateTestPropFile(Map<String, String> propMap) throws IOException {
    Properties prop = new Properties();
    OutputStream output = new FileOutputStream(testFile);
    for (Map.Entry<String, String> entry : propMap.entrySet()) {
      prop.setProperty(entry.getKey(), entry.getValue());
    }
    prop.store(output, "Test");
  }

  public static void touchTestPropFile() throws IOException {
    FileUtils.touch(new File(testFile));
  }
}


