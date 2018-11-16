package configurator;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AutoReloadConfigurator supports reloading properties. It uses AtomicReference - lock-free
 * thread-safe mechanism of "swapping" configurations on update.
 */

public class AutoReloadConfigurator implements Configurator, FileChangeSubscriber {

  private AtomicReference<Configurator> currentConfiguration = new AtomicReference<Configurator>();

  public AutoReloadConfigurator(String propFilePath) throws Exception {
    FileWatcher watcher = new FileWatcher(propFilePath, this);
    currentConfiguration.set(new SimpleConfigurator(propFilePath));
    watcher.start();
  }

  public String getConfig(String configName) {
    return currentConfiguration.get().getConfig(configName);
  }

  public void onFileChange(File file) {
    try {
      currentConfiguration.set(new SimpleConfigurator(file.getAbsolutePath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
