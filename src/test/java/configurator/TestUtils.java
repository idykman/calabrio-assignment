package configurator;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

public class TestUtils {

    public static final File testFile = new File("config.properties");


    public static void updateTestPropFile(Map<String, String> propMap) throws IOException {
        Properties prop = new Properties();
        OutputStream output = new FileOutputStream(testFile);
        for (Map.Entry<String, String> entry : propMap.entrySet()) {
            prop.setProperty(entry.getKey(), entry.getValue());
        }
        prop.store(output, "Test");
    }

    public static void touchTestPropFile() throws IOException {
        FileUtils.touch(testFile);
    }

    public static void deleteTestPropFile() {
        FileUtils.deleteQuietly(testFile);
    }
}


