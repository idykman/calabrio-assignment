package configurator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SimpleConfiguratorTest {

  Map<String, String> testProps = new HashMap<String, String>();
  private final TestUtils testUtils = new TestUtils(this.getClass().getName());


  @BeforeMethod
  public void setUp() throws IOException {
    testUtils.deleteTestPropFile();
    testProps.put("key1", "value1");
    testProps.put("key2", "value2");
    testUtils.updateTestPropFile(testProps);
  }

  @AfterMethod
  public void tearDown() {
    testUtils.deleteTestPropFile();
  }

  @Test
  public void testGetConfig() throws IOException {
    System.out.println("test simpleconfig started");
    SimpleConfigurator configurator = new SimpleConfigurator(testUtils.testFile);
    Assert.assertEquals(configurator.getConfig("key1"), "value1");
    Assert.assertEquals(configurator.getConfig("key2"), "value2");
    Assert.assertEquals(configurator.getConfig("key3"), null);
    System.out.println("test simpleconfig finished");
  }


}