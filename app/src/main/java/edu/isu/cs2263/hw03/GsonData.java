/**
 * MIT License
 *
 * Copyright (c) 2022 Ben Thomas
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
