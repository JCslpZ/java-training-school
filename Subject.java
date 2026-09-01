public class Subject {

    private int id;
    private int specialism;
    private int duration;
    private String description;

    // Constructor
    public Subject(int id, int specialism, int duration, String description) {
        this.id = id;
        this.specialism = specialism;
        this.duration = duration;
        this.description = description;
    }

    // Accessor methods
    public int getID() {
        return id;
    }

    public int getSpecialism() {
        return specialism;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    // Mutator method
    public void setDescription(String description) {
        this.description = description;
    }
}
