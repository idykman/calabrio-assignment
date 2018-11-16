package configurator;


import java.io.File;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * Notifies subscriber about file changes. Uses Apache commons.io library. Java WatchService API
 * would be a better choice, but not available for Java 5
 */
public class FileWatcher {

  private static final long POLL_INTERVAL_MS = 1000L;

  private final String filePath;
  private final FileChangeSubscriber subscriber;
  private final FileAlterationMonitor monitor = new FileAlterationMonitor(POLL_INTERVAL_MS);

  public FileWatcher(String filePath, FileChangeSubscriber subscriber) {
    this.filePath = filePath;
    this.subscriber = subscriber;
  }

  public void start() throws Exception {
    File file = new File(filePath);
    IOFileFilter filter = FileFilterUtils.nameFileFilter(file.getName());
    FileAlterationObserver observer = new FileAlterationObserver(
        file.getAbsoluteFile().getParentFile(), filter);
    FileAlterationListener listener = new FileAlterationListenerAdaptor() {
      @Override
      public void onFileCreate(File file) {
        subscriber.onFileChange(file);
      }

      @Override
      public void onFileChange(File file) {
        subscriber.onFileChange(file);
      }
    };
    observer.addListener(listener);
    monitor.addObserver(observer);
    monitor.start();
  }


}
