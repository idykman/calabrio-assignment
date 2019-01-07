package configurator;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class FileWatcherTest implements FileChangeSubscriber {

    private FileWatcher watcher;
    private volatile boolean changeDetected = false;
    private final Object lock = new Object();
    private final TestUtils testUtils = new TestUtils(this.getClass().getName());

    @BeforeMethod
    public void setUp() {
        testUtils.deleteTestPropFile();
        watcher = new FileWatcher(testUtils.testFile, this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        watcher.stop();
        testUtils.deleteTestPropFile();
    }

    @Test(timeOut = 1000)
    public void testStart() throws Exception {
        System.out.println("test filewatcher started");
        watcher.start();
        testUtils.touchTestPropFile();
        synchronized (lock) {
            while (!changeDetected) {
                lock.wait();
            }
        }
        Assert.assertTrue(changeDetected);
        System.out.println("test filewatcher finished");
    }

    public void onFileChange(File file) {
        System.out.println(file.getName() + " changed");
        synchronized (lock) {
            changeDetected = true;
            lock.notifyAll();
        }
    }

}