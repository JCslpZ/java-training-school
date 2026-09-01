import java.util.ArrayList;
import java.util.Random;
import java.io.*;

// The Administrator manages the simulation of the school
public class Administrator {

    private School school;
    private Random random;

    // Constructor
    public Administrator(School school) {
        this.school = school;
        this.random = new Random();
    }

    // Load school helper method
    public static School loadSchool(String filename) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        School school = null;

        while ((line = reader.readLine()) != null) {

            String[] parts = line.split(":");
            if (parts.length < 2) continue;  // skip invalid lines

            String type = parts[0].trim();
            String data = parts[1].trim();

            // Create school
            if (type.equals("school")) {
                school = new School(data);
            }

            // Create subject
            else if (type.equals("subject")) {
                String[] s = data.split(",");
                String description = s[0];
                int id = Integer.parseInt(s[1]);
                int specialism = Integer.parseInt(s[2]);
                int duration = Integer.parseInt(s[3]);
                school.add(new Subject(id, specialism, duration, description));
            }

            // Create student
            else if (type.equals("student")) {
                String[] s = data.split(",");
                String name = s[0];
                char gender = s[1].charAt(0);
                int age = Integer.parseInt(s[2]);
                school.add(new Student(name, gender, age));
            }

            // Create instructors
            else if (type.equals("Teacher") || type.equals("Demonstrator") ||
                    type.equals("OOTrainer") || type.equals("GUITrainer")) {

                String[] s = data.split(",");
                String name = s[0];
                char gender = s[1].charAt(0);
                int age = Integer.parseInt(s[2]);

                if (type.equals("Teacher")) {
                    school.add(new Teacher(name, gender, age));
                } else if (type.equals("Demonstrator")) {
                    school.add(new Demonstrator(name, gender, age));
                } else if (type.equals("OOTrainer")) {
                    school.add(new OOTrainer(name, gender, age));
                } else if (type.equals("GUITrainer")) {
                    school.add(new GUITrainer(name, gender, age));
                }
            }
        }

        reader.close();
        return school;
    }

    public static void main(String[] args) throws Exception {

        // Check that user provided 2 arguments
        if (args.length < 2) {
            System.out.println("Usage: java Administrator <configFile> <daysToSimulate>");
            return;
        }

        // Get arguments
        String fileName = args[0];                  // e.g., "mySchool.txt"
        int days = Integer.parseInt(args[1]);       // e.g., 10, 200

        // Load the school from the configuration file
        School school = loadSchool(fileName);

        // Create Administrator
        Administrator admin = new Administrator(school);

        // Run the simulation for the specified number of days
        admin.run(days);

        String baseName = fileName;

        if (baseName.endsWith(".save.txt")) {
            baseName = baseName.substring(0, baseName.length() - 9);
        } else if (baseName.endsWith(".txt")) {
            baseName = baseName.substring(0, baseName.length() - 4);
        }

        // Save using same base name
        admin.saveSimulation(baseName);
    }

    // Runs the simulation indefinitely
    public void run() {
        while (true) {
            simulateDay();
        }
    }

    // Runs the simulation for a fixed number of days
    public void run(int days) {

        for (int day = 1; day <= days; day++) {

            System.out.println("===== Day " + day + " =====");

            simulateDay();

            // Print state of school after the day
            System.out.println(school);
        }
    }

    // Simulates one day in the school
    private void simulateDay() {

        // ------------------------------
        // STEP 1: Admit new students up to 2 students join randomly
        // ------------------------------
        int newStudents = random.nextInt(3); // 0,1,2

        for (int i = 0; i < newStudents; i++) {

            String name = "Student" + random.nextInt(10000);
            char gender = random.nextBoolean() ? 'M' : 'F';
            int age = 18 + random.nextInt(10);

            school.add(new Student(name, gender, age));
        }

        // ------------------------------
        // STEP 2: Admit new instructors based on given probabilities
        // ------------------------------
        int chance = random.nextInt(100);

        String name = "Instructor" + random.nextInt(10000);
        char gender = random.nextBoolean() ? 'M' : 'F';
        int age = 25 + random.nextInt(20);

        if (chance < 20) {
            school.add(new Teacher(name, gender, age));
        }
        else if (chance < 30) {
            school.add(new Demonstrator(name, gender, age));
        }
        else if (chance < 35) {
            school.add(new OOTrainer(name, gender, age));
        }
        else if (chance < 40) {
            school.add(new GUITrainer(name, gender, age));
        }

        // ------------------------------
        // STEP 3: Run one day at school
        // ------------------------------
        school.aDayAtSchool();

        // ------------------------------
        // STEP 4: End-of-day updates
        // ------------------------------

        // Instructors leaving
        ArrayList<Instructor> instructors = new ArrayList<>(school.getInstructors());

        for (Instructor i : instructors) {

            if (i.getAssignedCourse() == null) {

                if (random.nextInt(100) < 20) { // 20% chance
                    school.remove(i);
                }
            }
        }

        // Students leaving
        ArrayList<Student> students = new ArrayList<>(school.getStudents());

        for (Student s : students) {

            // If student has all certificates they leave
            if (s.getCertificates().size() == school.getSubjects().size()) {
                school.remove(s);
            }
            else {

                // Free students have 5% chance of leaving
                boolean enrolled = false;

                for (Course c : school.getCourses()) {
                    for (Student st : c.getStudents()) {
                        if (st == s) {
                            enrolled = true;
                        }
                    }
                }

                if (!enrolled && random.nextInt(100) < 5) {
                    school.remove(s);
                }
            }
        }
    }
    public void saveSimulation(String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename + ".save.txt");

            // Save Subjects
            writer.println("# Subjects");
            for (Subject s : school.getSubjects()) {
                writer.println(s.getID() + "," + s.getSpecialism() + "," +
                        s.getDuration() + "," + s.getDescription());
            }

            // Save Students
            writer.println("# Students");
            for (Student s : school.getStudents()) {
                String certs = "";
                for (Integer subId : s.getCertificates()) {
                    certs += subId + "|";
                }
                // REMOVE trailing |
                if (certs.endsWith("|")) {
                    certs = certs.substring(0, certs.length() - 1);
                }
                writer.println(s.getName() + "," + s.getGender() + "," +
                        s.getAge() + "," + certs);
            }

            // Save Instructors
            writer.println("# Instructors");
            for (Instructor i : school.getInstructors()) {
                String course = (i.getAssignedCourse() != null)
                        ? i.getAssignedCourse().getSubject().getID() + ""
                        : "null";
                writer.println(i.getName() + "," + i.getGender() + "," +
                        i.getAge() + "," + course);
            }

            // Save Courses
            writer.println("# Courses");
            for (Course c : school.getCourses()) {
                String students = "";
                for (Student s : c.getStudents()) {
                    students += s.getName() + "|";
                }
                // REMOVE trailing |
                if (students.endsWith("|")) {
                    students = students.substring(0, students.length() - 1);
                }
                writer.println(
                        c.getSubject().getID() + "," +
                                c.getDaysUntilStarts() + "," +
                                c.getDaysToRun() + "," +
                                (c.getInstructor() != null ? c.getInstructor().getName() : "null") + "," +
                                students
                );
            }

            writer.close();
            System.out.println("Simulation saved to " + filename + ".save.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}