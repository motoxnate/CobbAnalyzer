/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.Paths.*;
import static java.nio.file.StandardCopyOption.*;

public class Datalog {
    private String name;
    private Date timestamp;
    private File log;

    public Datalog(String Name) {
        name = Name;
    }

    public void importCSV(String path) {
        //Copy file into local directory
        Path path1 = get(path);
        Path path2 = get(CONSTANTS.datalogPath);
        try {
            Files.copy(path1, path2, COPY_ATTRIBUTES);
        } catch(java.io.IOException E) {
            System.out.println("Error Copying " + E);
        }

        File temp = new File(path);
        name = temp.getName();
        log = new File(CONSTANTS.datalogPath+name);
        try {
            BasicFileAttributes attr = Files.readAttributes(get(CONSTANTS.datalogPath+name), BasicFileAttributes.class);
            timestamp = new Date((attr.creationTime()).toMillis());
        } catch(java.io.IOException E) {
            System.out.println("Exception Getting Attributes " + E);
        }
    }
}
