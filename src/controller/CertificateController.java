package controller;

import database.Database;
import models.Certificate;
import models.Course;
import models.QuizProgress;
import models.Student;

public class CertificateController {
    private CoursesController coursesController;
    private StudentController studentController;
    private Database usersDB;
    private CertificateJsonGenerator certificateGenerator;

    public CertificateController(CoursesController coursesController,
                                 StudentController studentController,
                                 Database usersDB) {
        this.coursesController = coursesController;
        this.studentController = studentController;
        this.usersDB = usersDB;
        this.certificateGenerator = new CertificateJsonGenerator();
    }


    public boolean isCourseCompleted(Student student, Course course) {
        if (student == null || course == null) return false;

        // Must be enrolled
        if (!student.getEnrolledCourseIds().contains(course.getCourseId())) return false;

        // Check every lesson
        for (String lessonId : course.getLessonsIds()) {
            QuizProgress progress = student.getQuizProgress().get(lessonId);
            if (progress == null || !progress.isPassed()) {
                return false;
            }
        }
        return true;
    }


    public Certificate generateCertificate(String studentId, String courseId) {
        Student student = studentController.getStudentById(studentId);
        Course course = coursesController.getCourseById(courseId);

        if (!isCourseCompleted(student, course)) {
            throw new IllegalArgumentException("Student has not completed the course.");
        }

        // Avoid duplicates
        if (hasCertificate(studentId, courseId)) {
            return getCertificate(studentId, courseId);
        }

        Certificate newCertificate = new Certificate(studentId, courseId);
        student.addCertificate(newCertificate);

        // Save certificate JSON file
        certificateGenerator.generateJsonCertificate(newCertificate);

        // Persist student update
        usersDB.saveUsers();

        return newCertificate;
    }


    public boolean hasCertificate(String studentId, String courseId) {
        Student student = studentController.getStudentById(studentId);
        for (Certificate certificate : student.getCertificates()) {
            if (certificate.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }


    public Certificate getCertificate(String studentId, String courseId) {
        Student student = studentController.getStudentById(studentId);
        for (Certificate certificate : student.getCertificates()) {
            if (certificate.getCourseId().equals(courseId)) {
                return certificate;
            }
        }
        return null;
    }


    public void checkToGenerateCertificate(String studentId, String courseId) {
        Student student = studentController.getStudentById(studentId);
        Course course = coursesController.getCourseById(courseId);

        if (isCourseCompleted(student, course) && !hasCertificate(studentId, courseId)) {
            generateCertificate(studentId, courseId);
        }
    }
}