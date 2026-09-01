public class Person {

    private String name;
    private char gender;
    private int age;

    // Constructor
    public Person(String name, char gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    // Returns the name
    public String getName() {
        return name;
    }

    // Returns the gender
    public char getGender() {
        return gender;
    }

    // Returns the age
    public int getAge() {
        return age;
    }

    // Updates the age
    public void setAge(int age) {
        this.age = age;
    }
}
