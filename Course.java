import java.util.ArrayList;

public class Course {

    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;
    private ArrayList<Student> students;
    private Instructor instructor;
    private boolean cancelled;

    // Constructor
    public Course(Subject subject, int daysUntilStarts) {
        this.subject = subject;
        this.daysUntilStarts = daysUntilStarts;
        this.daysToRun = subject.getDuration();
        this.students = new ArrayList<>();
        instructor = null;
        cancelled = false;
    }

    // Returns the subject
    public Subject getSubject() {
        return subject;
    }

    // Returns course status
    public int getStatus() {

        if (cancelled) {
            return 0;
        }

        if (daysUntilStarts > 0) {
            return -daysUntilStarts;
        }
        else if (daysToRun > 0) {
            return daysToRun;
        }
        else {
            return 0;
        }
    }

    // Enrol a student
    public boolean enrolStudent(Student student) {

        if (students.size() >= 3 || daysUntilStarts <= 0) {
            return false;
        }

        students.add(student);
        return true;
    }

    // Number of students
    public int getSize() {
        return students.size();
    }

    // Returns students as array
    public Student[] getStudents() {
        return students.toArray(new Student[0]);
    }

    public boolean setInstructor(Instructor instructor) {

        if (this.instructor != null) {
            return false;
        }

        if (instructor.getAssignedCourse() != null) {
            return false;
        }

        if (!instructor.canTeach(subject)) {
            return false;
        }

        this.instructor = instructor;
        instructor.assignCourse(this);

        return true;
    }

    public boolean hasInstructor() {
        return instructor != null;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    // Simulate one day passing
    public void aDayPasses() {

        if (cancelled) {
            return;
        }

        if (daysUntilStarts > 0) {
            daysUntilStarts--;

            if (daysUntilStarts == 0) {
                if (students.size() == 0 || instructor == null) {
                    cancelled = true;
                    return;
                }
            }
        } else if (daysToRun > 0) {

            daysToRun--;

            if (daysToRun == 0) {

                for (Student s : students) {
                    s.graduate(subject);
                }

                if (instructor != null) {
                    instructor.unassignCourse();
                }
            }
        }
    }
    public int getDaysUntilStarts() {
        return daysUntilStarts;
    }

    public int getDaysToRun() {
        return daysToRun;
    }

    public Instructor getInstructor() {
        return instructor;
    }
}