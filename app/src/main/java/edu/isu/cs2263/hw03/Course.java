package edu.isu.cs2263.hw03;

/**
 * Holds information related to college courses
 * @author Ben Thomas
 */
public class Course {
    private static final String[] depCodes = {"CS", "MATH", "CHEM", "PHYS", "BIOL", "EE"};

    String depCode;
    int number;
    String id;
    String name;
    int credits;

    /**
     * Constructs a new course with the given data
     * @param depCode  for the appropriate department
     * @param number  of the course
     * @param name  of the course
     * @param credits  for the course
     */
    public Course(String depCode, int number, String name, int credits) {
        this.depCode = depCode;
        this.number = number;
        this.id = depCode + " " + number;
        this.name = name;
        this.credits = credits;
    }

    /**
     * Gets the department code associated with the given department
     * @param department  to get the code of
     * @return the department code of the given department
     */
    public static String getDepCode(String department) {
        return switch (department) {
            case "Computer Science" -> depCodes[0];
            case "Mathematics" -> depCodes[1];
            case "Chemistry" -> depCodes[2];
            case "Physics" -> depCodes[3];
            case "Biology" -> depCodes[4];
            case "Electrical Engineering" -> depCodes[5];
            default -> null;
        };
    }

    /**
     * Gets the static department code array
     * @return static department code array
     */
    public static String[] getDepCodes() {
        return depCodes;
    }

    /**
     * Gets the id of the course
     * @return the id of the course
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the course
     * @return the name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of credits for the course
     * @return the number of credits for the course
     */
    public int getCredits() {
        return credits;
    }
}
