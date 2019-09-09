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
import com.opencsv.*;

public class Datalog {
    private String name, filename;
    private Date timestamp;
    private File log;
    private List<String[]> data;

    public Datalog(String Name) {
        name = Name;
        filename = name + ".dat";
        try {
            getAttributes();    // Just in case the log file already exists
            log = new File(CONSTANTS.datalogPath + filename);
//            loadData();
        } catch (Exception E) {
            System.err.println("Datalog " + name + " does not exist");
            E.printStackTrace();
        }
    }

    /** Allow the user to import the datalog CSV file into the working folder **/
    public boolean importCSV(String path) {
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
            //TODO Add Replace Option?


        } catch(java.io.IOException E) {
            System.err.println("Error Copying");
            E.printStackTrace();
            return(false);
        }

        log = new File(CONSTANTS.datalogPath + filename);
        getAttributes();

//        loadData();
        return(true);
    }

    /** Load data from the already imported CSV file into the running application **/
    public Object[][][] loadData() {
        try {
            Reader reader = new FileReader(log);
            CSVReader csvreader = new CSVReader(reader);
            data = csvreader.readAll();
        } catch(java.io.FileNotFoundException f) {
            System.err.println("File Not Found");
            f.printStackTrace();
        } catch(java.io.IOException e) {
            System.err.println("Error reading data");
            e.printStackTrace();
        }
//        for (String[] data : data) {
//            System.out.println("Data : " + data[0]);    //[0] is first column to print each item
//        }

        Object[][][] dataSet = prepareTableData();
        return dataSet;
    }

    private void loadDataintoChart() {

    }

    private Object[][][] prepareTableData() {
        Object[][][] out = new Object[2][data.size()][];
        Object[] columns = data.get(0);
        Object[][] rows = new Object[data.size()-1][];
        for(int i=1; i<data.size()-1; i++) {
            rows[i] = data.get(i);
        }
//        System.out.println(rows[0][0]);
        out[0][0] = columns;
        out[1] = rows;
        return out;
    }

    /** Get the extended attributes from the local imported CSV file **/
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

    /** Public interfaces **/
    public String getName() {
        return name;
    }
    public Date getTimestamp() {
        return timestamp;
    }

}
