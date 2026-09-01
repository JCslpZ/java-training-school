public abstract class Instructor extends Person {

    private Course assignedCourse;

    // Constructor
    public Instructor(String name, char gender, int age) {
        super(name, gender, age);
        assignedCourse = null;
    }

    // Assign a course
    public void assignCourse(Course course) {
        assignedCourse = course;
    }

    // Remove assigned course
    public void unassignCourse() {
        assignedCourse = null;
    }

    // Return assigned course
    public Course getAssignedCourse() {
        return assignedCourse;
    }

    // Abstract method
    public abstract boolean canTeach(Subject subject);
}
