package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certificate {
    private String certificateId;
    private String courseId;
    private String studentId;
    private LocalDate issueDate;

    public Certificate (String studentId ,String courseId){
        this.studentId = studentId;
        this.courseId = courseId;
        this.certificateId = "Cer"+"-"+studentId+"-"+courseId;
        this.issueDate = LocalDate.now();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCertificateId() {
        return certificateId;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getDisplayString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Course: %s - Issued: %s", courseId, issueDate.format(formatter));
    }

    public String getDetailedInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format(
                "Certificate ID: %s\nStudent ID: %s\nCourse ID: %s\nIssue Date: %s",
                certificateId, studentId, courseId, issueDate.format(formatter)
        );
    }

}
