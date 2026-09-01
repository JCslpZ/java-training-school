public class TestAdministrator {
    public static void main(String[] args) {

        School school = new School("Java Training School");

                // Add subjects
        school.add(new Subject(1,1,5,"Basics"));
        school.add(new Subject(2,2,2,"Lab 1"));
        school.add(new Subject(3,1,3,"Arrays"));

        Administrator admin = new Administrator(school);

        admin.run(5); // run simulation for 5 days
    }
}
