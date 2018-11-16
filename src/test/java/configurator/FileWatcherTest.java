package configurator;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;

public class FileWatcherTest {

  private static final String testFile = "config.properties";
  private FileWatcher watcher;
  private static boolean changeDetected = false;

  @org.testng.annotations.BeforeClass
  public void setUp() {
    watcher = new FileWatcher(testFile, new TestSub());
  }

  @org.testng.annotations.AfterClass
  public void tearDown() {
  }

  @org.testng.annotations.Test
  public void testStart() throws Exception {
    watcher.start();
    FileUtils.touch(new File(testFile));
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