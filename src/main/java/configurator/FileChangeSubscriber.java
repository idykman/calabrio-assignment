package configurator;

import java.io.File;

public interface FileChangeSubscriber {

  void onFileChange(File file);

}
