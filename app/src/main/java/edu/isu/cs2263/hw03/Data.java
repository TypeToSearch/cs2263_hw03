package edu.isu.cs2263.hw03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * An interface for dealing with IO related to Course objects
 * @author Ben Thomas
 */
public interface Data {
    /**
     * Saves json data to the specified file
     * @param courses  to save
     * @param file  to save to
     * @throws IOException if and input error occurs
     */
    void save(LinkedList<Course> courses, File file) throws IOException;

    /**
     * Loads Courses from a json file
     * @param file  to load from
     * @return LinkedList of courses from file
     * @throws FileNotFoundException if an input error occurs
     */
    LinkedList<Course> load(File file) throws FileNotFoundException;
}
