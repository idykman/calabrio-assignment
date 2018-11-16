package configurator;

import java.io.File;
import org.testng.Assert;

public class FileWatcherTest {

  private FileWatcher watcher;
  private static boolean changeDetected = false;

  @org.testng.annotations.BeforeClass
  public void setUp() {
    watcher = new FileWatcher(TestUtils.testFile, new TestSub());
  }

  @org.testng.annotations.AfterClass
  public void tearDown() {
  }

  @org.testng.annotations.Test
  public void testStart() throws Exception {
    watcher.start();
    TestUtils.touchTestPropFile();
    Thread.sleep(1000);
    Assert.assertTrue(changeDetected);
  }

  static public class TestSub implements IFileChangeSubscriber {

    public void onFileChange(File file) {
      System.out.println(file.getName() + " changed");
      FileWatcherTest.changeDetected = true;
    }
  }
}