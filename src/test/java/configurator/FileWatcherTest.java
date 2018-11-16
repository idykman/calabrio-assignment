package configurator;

import java.io.File;
import org.testng.Assert;

public class FileWatcherTest implements FileChangeSubscriber {

  private FileWatcher watcher;
  private boolean changeDetected = false;

  @org.testng.annotations.BeforeClass
  public void setUp() {
    watcher = new FileWatcher(TestUtils.testFile, this);
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

  public void onFileChange(File file) {
    System.out.println(file.getName() + " changed");
    changeDetected = true;
  }

}