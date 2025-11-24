package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<String> enrolledCourseIds = new ArrayList<>();
    //private List<String> progressLessonIds = new ArrayList<>();
    private Map<String, List<String>> progressByCourse = new HashMap<>();
    private Map<String, QuizProgress> quizProgress = new HashMap<>();      // nasser
<<<<<<< HEAD
    //private List<Certificate> certificates = new ArrayList<>();
=======

>>>>>>> c06528a1cbd71f2ac1faaea7c021ac8f000e95b2

    //map
    public Map<String, List<String>> getProgressByCourse() {
        return progressByCourse;
    }

    //map
    public void setProgressByCourse(Map<String, List<String>> progressByCourse) {
        this.progressByCourse = progressByCourse;
    }

    //map
    public boolean addProgressLesson(String courseId, String lessonId) {
        //String uniqueId = courseId+"-"+lessonId;
        progressByCourse.putIfAbsent(courseId, new ArrayList<>());
        List<String> lessons = progressByCourse.get(courseId);
        if (!lessons.contains(lessonId)) {
            lessons.add(lessonId);
            return true;
        }
        return false;
    }

    //map
    public boolean hasCompletedLesson(String courseId, String lessonId) {
        //String uniqueId = courseId+"-"+lessonId;
        return progressByCourse.containsKey(courseId) &&
                progressByCourse.get(courseId).contains(lessonId);
    }


    public Student(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "STUDENT");
    }

    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "STUDENT", true);
    }

    public Student() {
        super();
        this.role = "STUDENT";
    }

    public List<String> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public void setEnrolledCourseIds(List<String> enrolledCourseIds) {
        this.enrolledCourseIds = enrolledCourseIds;
    }

    public boolean enrollInCourse(String courseId) {
        if (!enrolledCourseIds.contains(courseId)) {
            enrolledCourseIds.add(courseId);
            return true;
        }
        return false;
    }

//    public List<String> getProgressLessonIds() {
//        return progressLessonIds;
//    }
//
//    public void setProgressLessonIds(List<String> progressLessonIds) {
//        this.progressLessonIds = progressLessonIds;
//    }
//
//    public boolean addProgressLesson(String lessonId) {
//        if (!progressLessonIds.contains(lessonId)) {
//            progressLessonIds.add(lessonId);
//            return true;
//        }
//        return false;
//    }

<<<<<<< HEAD

=======
>>>>>>> c06528a1cbd71f2ac1faaea7c021ac8f000e95b2
    public Map<String, QuizProgress> getQuizProgress() {  // we return the whole map
        return quizProgress;
    }

    public QuizProgress getQuizProgressForLesson(String lessonId) {  //we return the quiz progress object for that lesson
        return quizProgress.get(lessonId);
    }

    public void saveQuizProgress(String lessonId, QuizProgress progress) {  // when a student finished a quiz, this method must get called
        quizProgress.put(lessonId, progress);
    }

<<<<<<< HEAD
    /*public List<Certificate> getCertificates(){
        return this.certificates;
    }

    public void addCertificate(Certificate certificate){
        certificates.add(certificate);
    }

     */
=======
>>>>>>> c06528a1cbd71f2ac1faaea7c021ac8f000e95b2
}