package configurator;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class AutoReloadConfiguratorTest {

  Map<String, String> testProps1 = new HashMap<String, String>();
  Map<String, String> testProps2 = new HashMap<String, String>();
  private final TestUtils testUtils = new TestUtils(this.getClass().getName());



  @BeforeMethod
  public void setUp() {
    testUtils.deleteTestPropFile();
    testProps1.put("key1", "value1");
    testProps1.put("key2", "value2");

    testProps2.put("key1", "new_value1");
    testProps2.put("key2", "new_value2");

  }

  @AfterMethod
  public void tearDown() {
    testUtils.deleteTestPropFile();
  }

  @Test
  public void testGetConfig() throws Exception {
    System.out.println("test config started");
    testUtils.updateTestPropFile(testProps1);
    AutoReloadConfigurator configurator = new AutoReloadConfigurator(testUtils.testFile);

    Assert.assertEquals(configurator.getConfig("key1"), "value1");
    Assert.assertEquals(configurator.getConfig("key2"), "value2");

    testUtils.updateTestPropFile(testProps2);
    for(int i = 0; i< 10; i++){
      if(configurator.getConfig("key1").equals("new_value1"))
        break;
      System.out.println("wating for change...");
      Thread.sleep(500);
    }
    Assert.assertEquals(configurator.getConfig("key1"), "new_value1");
    Assert.assertEquals(configurator.getConfig("key2"), "new_value2");

    System.out.println("test config finished");

  }
}