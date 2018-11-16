package configurator;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class AutoReloadConfigurator implements IConfigurator, IFileChangeSubscriber {

  private AtomicReference<IConfigurator> currentConfiguration = new AtomicReference<IConfigurator>();

  public AutoReloadConfigurator(String propFilePath) throws Exception {
    FileWatcher watcher = new FileWatcher(propFilePath, this);
    currentConfiguration.set(new Configurator(propFilePath));
    watcher.start();
  }

  public String getConfig(String configName) {
    return currentConfiguration.get().getConfig(configName);
  }

  public void onFileChange(File file) {
    try {
      currentConfiguration.set(new Configurator(file.getAbsolutePath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
