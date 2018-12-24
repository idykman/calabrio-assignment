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

    @BeforeMethod
    public void setUp() {
        TestUtils.deleteTestPropFile();
        watcher = new FileWatcher(TestUtils.testFile, this);
    }

    @AfterMethod
    public void tearDown() {
        TestUtils.deleteTestPropFile();
    }

    @Test(timeOut = 1000)
    public void testStart() throws Exception {
        watcher.start();
        TestUtils.touchTestPropFile();
        synchronized (lock) {
            while (!changeDetected) {
                lock.wait();
            }
        }
        Assert.assertTrue(changeDetected);
    }

    public void onFileChange(File file) {
        System.out.println(file.getName() + " changed");
        synchronized (lock) {
            changeDetected = true;
            lock.notifyAll();
        }
    }

}