public class TestSchool {
    public static void main(String[] args) {
        School school = new School("Java Training School");

        school.add(new Subject(1,1,5,"Basics"));

        school.add(new Student("Tom",'M',20));
        school.add(new Student("Anna",'F',21));

        school.add(new Teacher("John",'M',40));

        school.aDayAtSchool();

        System.out.println(school);
    }
}
