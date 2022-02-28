package edu.isu.cs2263.hw03;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

/**
 * The main class for dealing with the application's UI
 * @author Ben Thomas
 */
public class CourseProcessor extends Application {
    private LinkedList<Course> courses = new LinkedList<>();
    private final Data data = new GsonData();

    /**
     * Runs the UI for the application
     * @param stage the stage on which the UI is set
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        // New course text
        Text text = new Text();
        text.setText("New Course");
        text.setX(120);
        text.setY(70);
        text.setFont(Font.font("Verdana", 20));
        // Department dropdown
        ComboBox<String> department = new ComboBox<>();
        department.getItems().addAll("Computer Science", "Mathematics", "Chemistry",
                "Physics", "Biology", "Electrical Engineering");
        department.setPromptText("Department");
        // Course number
        TextField number = new TextField();
        number.setPromptText("Course Number");
        // Course name
        TextField name = new TextField();
        name.setPromptText("Course Name");
        // Credit
        TextField credits = new TextField();
        credits.setPromptText("Credits");
        // Enter button
        Button enter = new Button();
        enter.setText("Enter");
        // Add VBox
        VBox addCourse = new VBox(20);
        addCourse.getChildren().addAll(text, department, number, name, credits, enter);

        Button disAll = new Button();
        disAll.setText("Display All");
        ComboBox<String> depDis = new ComboBox<>();
        depDis.getItems().addAll("Computer Science", "Mathematics", "Chemistry",
                "Physics", "Biology", "Electrical Engineering");
        depDis.setPromptText("Display (Dept.)");
        // Add HBox
        HBox topMenu = new HBox(10);
        topMenu.getChildren().addAll(disAll, depDis);

        TableView<Course> table = new TableView<>();
        // Column 1
        TableColumn<Course, String> col1 = new TableColumn<>("ID");
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col1.setResizable(false);
        col1.setMinWidth(100);
        // Column 2
        TableColumn<Course, String> col2 = new TableColumn<>("Name");
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setResizable(false);
        col2.setMinWidth(300);
        // Column 3
        TableColumn<Course, String> col3 = new TableColumn<>("Credits");
        col3.setCellValueFactory(new PropertyValueFactory<>("credits"));
        col3.setResizable(false);
        col3.setMinWidth(80);
        // Table
        table.setMinWidth(500);
        table.getColumns().addAll(col1, col2, col3);

        Button save = new Button();
        save.setText("Save");
        Button load = new Button();
        load.setText("Load");
        Button exit = new Button();
        exit.setText("Exit");
        HBox utility = new HBox(10);
        utility.setAlignment(Pos.CENTER_RIGHT);
        utility.getChildren().addAll(save, load, exit);

        VBox display = new VBox(10);
        display.getChildren().addAll(topMenu, table, utility);

        HBox main = new HBox(20);
        main.setPadding(new Insets(20));
        main.getChildren().addAll(addCourse, display);

        Scene scene = new Scene(main);
        stage.setTitle("Course Machine");
        stage.setScene(scene);
        stage.show();

        // Functionality
        enter.setOnAction(value -> {
            try {
                String depCode = Course.getDepCode(department.getValue());
                int num = Integer.parseInt(number.getText());
                String courseName = name.getText();
                int numCredits = Integer.parseInt(credits.getText());
                if (depCode != null && courseName.length() > 0) {
                    Course newCourse = new Course(depCode, num, courseName, numCredits);
                    courses.add(newCourse);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Course Added");
                alert.setHeaderText(null);
                alert.setContentText("The course was successfully added!");

                alert.showAndWait();
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("There was a problem with the input data");
                alert.setContentText("Please double check your values and try again.");

                alert.showAndWait();
            }
        });

        disAll.setOnAction(value -> {
            table.getItems().clear();
            table.getItems().addAll(courses);
            table.refresh();
        });

        depDis.setOnAction(value -> {
            table.getItems().clear();
            for (var course : courses) {
                if (course.depCode.equals(Course.getDepCode(depDis.getValue()))) {
                    table.getItems().add(course);
                }
            }
            table.refresh();
        });

        exit.setOnAction(value -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("You are about to exit the program");
            alert.setContentText("Are you sure you want to do this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                stage.close();
            }
        });

        save.setOnAction(value -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                this.save(fileChooser.showSaveDialog(stage));
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Save Error");
                alert.setHeaderText("There was an issue saving");
                alert.showAndWait();
            }
        });

        load.setOnAction(value -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                this.load(fileChooser.showOpenDialog(stage));
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load Error");
                alert.setHeaderText("There was an issue loading");
                alert.showAndWait();
            }
        });
    }

    /**
     * Loads course data using the Data object
     * {@link edu.isu.cs2263.hw03.Data#load(File)}
     */
    private void load(File file) throws FileNotFoundException {
        courses = data.load(file);
    }

    /**
     * Saves course data using the Data object
     * {{@link edu.isu.cs2263.hw03.Data#save(LinkedList, File)}}
     */
    private void save(File file) throws IOException {
        data.save(courses, file);
    }

    /**
     * Main method of the application
     * @param args  from the command line
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
