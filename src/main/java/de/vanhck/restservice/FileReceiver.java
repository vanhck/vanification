package de.vanhck.restservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by Jonas on 23.06.2017.
 *
 * @author Jonas Kett
 * @version 1.0
 */
@RestController
public class FileReceiver {
    private final static String prefix = "[File Receiver] ";

    private static void printLine(String toPrint) {
        System.out.println(prefix + toPrint);
    }

    @PostMapping("/file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            printLine("received file: " + file.getOriginalFilename());
            printLine("file has " + bytes.length + " bytes");
            return "success";
        } else {
            return "file is empty";
        }
    }
}
