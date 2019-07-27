/*
 * Copyright Nathaniel Fanning (c) 2019. All rights reserved.
 */
import javax.swing.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.Paths.*;
import static java.nio.file.StandardCopyOption.*;

public class Datalog {
    private String name, filename;
    private Date timestamp;
    private File log;

    public Datalog(String Name) {
        name = Name;
        filename = name + ".dat";
        try {
            getAttributes();    // Just in case the log file already exists
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public boolean importCSV(String path) throws FileAlreadyExistsException {
        //Copy file into local directory
        Path path1 = get(path);
        Path path2 = get(CONSTANTS.datalogPath + filename);

        try {
            Files.copy(path1, path2, COPY_ATTRIBUTES);
        } catch(java.nio.file.NoSuchFileException F) {
            MainWindow.CreateApplicationSupportFolder();
                try {
                    Files.copy(path1, path2, COPY_ATTRIBUTES);
                } catch(java.io.IOException E) {
                    System.err.println("Created Application Support folder, but could not import datalog");
                    E.printStackTrace();
                    return (false);
                }
        } catch(java.nio.file.FileAlreadyExistsException E) {
            System.err.println("File Already Exists");
            throw E;
            //TODO Add Replace Option?


        } catch(java.io.IOException E) {
            System.err.println("Error Copying");
            E.printStackTrace();
            return(false);
        }

        log = new File(CONSTANTS.datalogPath + filename);
        getAttributes();
        return(true);
    }

    public void getAttributes() {
        try {
            BasicFileAttributes attr = Files.readAttributes(get(CONSTANTS.datalogPath + filename), BasicFileAttributes.class);
            timestamp = new Date((attr.creationTime()).toMillis());
        } catch(java.io.IOException E) {
            System.err.println("Exception Getting Attributes");
            E.printStackTrace();
            //TODO If file doesn't exist, would you like to import it?


        }
    }

    public void saveData(Datalog log) {


    }

    public String getName() {
        return name;
    }
    public Date getTimestamp() {
        return timestamp;
    }

}
