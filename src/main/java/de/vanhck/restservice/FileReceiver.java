package de.vanhck.restservice;

import de.vanhck.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
@RestController
public class FileReceiver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final static String prefix = "[File Receiver] ";
    @Autowired
    private KeyNameValueDAO keyNameValueDAO;
    @Autowired
    private DrivingResultDAO drivingResultDAO;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DrivingKeyValueDAO drivingKeyValueDAO;

    private static void printLine(String toPrint) {
        System.out.println(prefix + toPrint);
    }

    @PostMapping("/file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                try {
                    new FileParser(keyNameValueDAO, drivingResultDAO, drivingKeyValueDAO, userDAO).createDrivingResultFromXML(bis); //FIXME
                } catch (Exception e) {//FIXME, bad bad
                    log.error("Couldn't read file.", e);
                }
                log.debug("received file: " + file.getOriginalFilename());
                log.debug("file has " + bytes.length + " bytes");
                return "success";

            } catch (IOException e) {
                return "illegal file.";
            }
        } else

        {
            return "file is empty";
        }
    }
}
