package configurator;

import java.io.File;

public interface IFileChangeSubscriber {

  void onFileChange(File file);

}
