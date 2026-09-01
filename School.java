import java.util.ArrayList;

// The School class represents the environment where students study and instructors teach courses.
public class School {

    // Name of the school
    private String name;

    // Collections storing all entities managed by the school
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private ArrayList<Subject> subjects;
    private ArrayList<Course> courses;

    // Constructor that creates a school with a name and initializes all collections
    public School(String name) {
        this.name = name;
        students = new ArrayList<>();
        instructors = new ArrayList<>();
        subjects = new ArrayList<>();
        courses = new ArrayList<>();
    }

    // ===============================
    // Methods for managing subjects
    // ===============================

    // Adds a subject to the school
    public void add(Subject subject) {
        subjects.add(subject);
    }

    // Removes a subject from the school
    public void remove(Subject subject) {
        subjects.remove(subject);
    }

    // Returns the list of subjects offered by the school
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    // ===============================
    // Methods for managing instructors
    // ===============================

    // Adds an instructor to the school
    public void add(Instructor instructor) {
        instructors.add(instructor);
    }

    // Removes an instructor from the school
    public void remove(Instructor instructor) {
        instructors.remove(instructor);
    }

    // Returns the list of instructors
    public ArrayList<Instructor> getInstructors() {
        return instructors;
    }

    // ===============================
    // Methods for managing courses
    // ===============================

    // Adds a course to the school
    public void add(Course course) {
        courses.add(course);
    }

    // Removes a course from the school
    public void remove(Course course) {
        courses.remove(course);
    }

    // Returns the list of courses currently running
    public ArrayList<Course> getCourses() {
        return courses;
    }

    // ===============================
    // Methods for managing students
    // ===============================

    // Adds a student to the school
    public void add(Student student) {
        students.add(student);
    }

    // Removes a student from the school
    public void remove(Student student) {
        students.remove(student);
    }

    // Returns the list of students
    public ArrayList<Student> getStudents() {
        return students;
    }

    // ======================================
    // Returns a formatted string describing the current state of the school
    // ======================================
    public String toString() {

        String result = "School: " + name + "\n";

        // Print all subjects offered by the school
        result += "\nSubjects:\n";
        for (Subject s : subjects) {
            result += s.getID() + " - " + s.getDescription() + "\n";
        }

        // Print all courses with their student count and instructor status
        result += "\nCourses:\n";
        for (Course c : courses) {
            result += c.getSubject().getDescription() +
                    " | Students: " + c.getSize() +
                    " | Instructor: " +
                    (c.hasInstructor() ? "Assigned" : "None") +
                    "\n";
        }

        // Print students and the certificates they have obtained
        result += "\nStudents:\n";
        for (Student s : students) {
            result += s.getName() + " Certificates: " + s.getCertificates() + "\n";
        }

        // Print all instructors in the school
        result += "\nInstructors:\n";
        for (Instructor i : instructors) {
            result += i.getName() + "\n";
        }

        return result;
    }

    // ======================================
    // Simulates one day at the school
    // ======================================
    public void aDayAtSchool() {

        // STEP 1: Create new courses for subjects that currently do not have an active course
        for (Subject s : subjects) {

            boolean open = false;

            for (Course c : courses) {
                if (!c.isCancelled() && c.getSubject().getID() == s.getID() && c.getStatus() != 0) {
                    open = true;
                    break;
                }
            }

            // If no open course exists, create a new one that starts in 2 days
            if (!open) {
                courses.add(new Course(s, 2));
            }
        }

        // STEP 2: Assign instructors to courses that do not have one
        for (Course c : courses) {

            if (!c.hasInstructor()) {

                for (Instructor i : instructors) {

                    // Instructor must be free and able to teach the subject
                    if (i.getAssignedCourse() == null && i.canTeach(c.getSubject())) {

                        c.setInstructor(i);
                        break;
                    }
                }
            }
        }

        // STEP 3: Assign students to courses
        for (Student s : students) {

            for (Course c : courses) {

                // Student can join if:
                // course is not full
                // student does not already have the certificate
                // course has not started yet
                if (c.getSize() < 3 &&
                        !s.hasCertificate(c.getSubject()) &&
                        c.getStatus() < 0) {

                    if (c.enrolStudent(s)) {
                        break;
                    }
                }
            }
        }

        // STEP 4: Advance all courses by one day
        for (Course c : courses) {
            c.aDayPasses();
        }

        // STEP 5: Remove courses that are finished or cancelled
        courses.removeIf(c -> c.getStatus() == 0 || c.isCancelled());
    }
}