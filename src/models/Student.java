package models;

public class Student extends User {

    public Student(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "STUDENT"); // role set here
    }


}
