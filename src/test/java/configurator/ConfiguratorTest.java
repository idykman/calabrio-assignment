package configurator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ConfiguratorTest {

  Map<String, String> testProps = new HashMap<String, String>();

  @BeforeMethod
  public void setUp() throws IOException {
    testProps.put("key1", "value1");
    testProps.put("key2", "value2");
    TestUtils.updateTestPropFile(testProps);
  }

  @AfterMethod
  public void tearDown() {
  }

  @Test
  public void testGetConfig() throws IOException {
    Configurator configurator = new Configurator(TestUtils.testFile);
    Assert.assertEquals(configurator.getConfig("key1"), "value1");
    Assert.assertEquals(configurator.getConfig("key2"), "value2");
    Assert.assertEquals(configurator.getConfig("key3"), null);
  }


}