package training.lepeskin.utility.loader;

import training.lepeskin.utility.logger.FilesLogger;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Loader<T> {
    private static final FilesLogger filesLogger = new FilesLogger(Loader.class.getName(), "loader-log.txt");

    public List<T> load(String pkgName, Class<?> parentClass) {
        List<String> classList = readClassNames(pkgName);

        return classList.stream().map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        String logString = String.format("Loadable candidate %s wasn't loaded.", className);
                        filesLogger.log(logString, e);
                    }
                    return null;
                }).filter(loadableClass -> loadableClass != null && !loadableClass.isInterface() &&
                        parentClass.isAssignableFrom(loadableClass))
                .map(loadableClass -> {
                    try {
                        return (T) loadableClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        String logString = String.format("Loadable candidate class object %s could not be instantiated.", loadableClass);
                        filesLogger.log(logString, e);
                    }
                    return null;
                }).filter(Objects::nonNull).toList();

    }

    private static List<String> readClassNames(String pkgName) {

        // Get a File object for the package
        File directory = null;
        String relPath = pkgName.replace('.', '/');

        URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);

        if (resource == null) {
            throw new RuntimeException("No resource for " + relPath);
        }

        try {
            directory = new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(pkgName + " (" + resource + ") does not appear to be a valid URL / URI. ", e);
        }

        List<String> classFiles = new ArrayList<String>();
        if (directory != null && directory.exists()) {
            String extention = ".class";
            // Get the list of the files contained in the package
            List<String> files = List.of(directory.list());
            classFiles = files.stream().filter(file -> file.endsWith(extention))
                    .map(file -> pkgName + '.' + file.substring(0, file.length() - extention.length())).toList();

        }
        return classFiles;
    }
}
