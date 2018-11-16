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

  public AutoReloadConfigurator(File file) throws Exception {
    FileWatcher watcher = new FileWatcher(file, this);
    currentConfiguration.set(new SimpleConfigurator(file));
    watcher.start();
  }

  public String getConfig(String configName) {
    return currentConfiguration.get().getConfig(configName);
  }

  public void onFileChange(File file) {
    try {
      currentConfiguration.set(new SimpleConfigurator(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
