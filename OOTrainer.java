public class OOTrainer extends Teacher {

    public OOTrainer(String name, char gender, int age) {
        super(name, gender, age);
    }

    public boolean canTeach(Subject subject) {
        int s = subject.getSpecialism();
        return s == 1 || s == 2 || s == 3;
    }
}