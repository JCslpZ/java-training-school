import java.util.ArrayList;

public class Student extends Person {

    private ArrayList<Integer> certificates;

    // Constructor
    public Student(String name, char gender, int age) {
        super(name, gender, age);   // calls Person constructor
        certificates = new ArrayList<>();
    }

    // Adds subject ID to certificates
    public void graduate(Subject subject) {
        certificates.add(subject.getID());
    }

    // Returns certificates list
    public ArrayList<Integer> getCertificates() {
        return certificates;
    }

    // Checks if student has certificate
    public boolean hasCertificate(Subject subject) {
        return certificates.contains(subject.getID());
    }
}
