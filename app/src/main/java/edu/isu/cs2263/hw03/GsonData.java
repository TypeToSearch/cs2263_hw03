package edu.isu.cs2263.hw03;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Takes care of IO with gson
 * {@inheritDoc}
 * @author Ben Thomas
 */
public class GsonData implements Data {

    /**
     * Uses gson to save data
     * {@inheritDoc}
     */
    public void save(LinkedList<Course> courses, File file) throws IOException {
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);

        for (var course : courses) {
            Gson gson = new Gson();
            String json = gson.toJson(course);
            fileWriter.write(json + "\n");
        }
        fileWriter.close();
    }

    /**
     * Uses gson to load data
     * {@inheritDoc}
     */
    public LinkedList<Course> load(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        LinkedList<Course> courses = new LinkedList<>();
        while (scanner.hasNextLine()) {
            Gson gson = new Gson();
            Course course = gson.fromJson(scanner.nextLine(), Course.class);
            courses.add(course);
        }
        scanner.close();
        return courses;
    }
}
