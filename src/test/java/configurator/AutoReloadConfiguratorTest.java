package configurator;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutoReloadConfiguratorTest {

  Map<String, String> testProps1 = new HashMap<String, String>();
  Map<String, String> testProps2 = new HashMap<String, String>();


  @BeforeMethod
  public void setUp() {
    testProps1.put("key1", "value1");
    testProps1.put("key2", "value2");

    testProps2.put("key1", "new_value1");
    testProps2.put("key2", "new_value2");
  }

  @AfterMethod
  public void tearDown() {
  }

  @Test
  public void testGetConfig() throws Exception {
    TestUtils.updateTestPropFile(testProps1);
    AutoReloadConfigurator configurator = new AutoReloadConfigurator(TestUtils.testFile);

    Assert.assertEquals(configurator.getConfig("key1"), "value1");
    Assert.assertEquals(configurator.getConfig("key2"), "value2");

    TestUtils.updateTestPropFile(testProps2);
    Thread.sleep(1000);
    Assert.assertEquals(configurator.getConfig("key1"), "new_value1");
    Assert.assertEquals(configurator.getConfig("key2"), "new_value2");


  }
}