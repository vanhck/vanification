package de.vanhck.restservice;

import de.vanhck.VanificationApplication;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.nio.file.StandardCopyOption;

/**
 * This class should save our loaded files in a directory,
 * so we can load it after restarting the stuff
 *
 * Hacky but should work
 * Created by Lotti on 6/23/2017.
 */
public class FileSaver {


    public void saveFile(InputStream in) throws IOException {
        File dir = new File(VanificationApplication.PATH_OF_FILE_DIR);
        dir.mkdir();
        File targetFile = new File(dir.getAbsolutePath().toString(), String.format("driverVale_%s",System.currentTimeMillis()));
        java.nio.file.Files.copy(
                in,
                targetFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(in);
    }


}
