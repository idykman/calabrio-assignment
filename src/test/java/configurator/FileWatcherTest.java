package configurator;

import java.io.File;
import org.testng.Assert;

public class FileWatcherTest implements FileChangeSubscriber {

  private FileWatcher watcher;
  private volatile boolean changeDetected = false;

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
    System.out.println("changed before:" + changeDetected);
    Thread.sleep(1000);
    System.out.println("changed:" + changeDetected);
    Assert.assertTrue(changeDetected);
  }

  public void onFileChange(File file) {
    System.out.println(file.getName() + " changed");
    changeDetected = true;
  }

}