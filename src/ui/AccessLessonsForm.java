package ui;

import controller.CoursesController;
import controller.QuizController;
import controller.StudentController;
import database.CoursesDatabase;
import database.Database;
import models.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccessLessonsForm extends JFrame {

    private JPanel accessLessonForm;
    private JTable table1;
    private JScrollPane scroller;

    CoursesDatabase coursesDB = new CoursesDatabase();
    Database usersDB = new Database();
    CoursesController coursesController = new CoursesController(coursesDB, usersDB);
    StudentController controller = new StudentController(coursesController, coursesDB, usersDB);
    private QuizController quizController ;
    private Map<String, QuizController> quizControllers = new HashMap<>();

    public AccessLessonsForm(String courseId, String currentUserId) {

        setContentPane(accessLessonForm);
        setTitle("Access Lessons");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controller.setCurrentStudent(currentUserId);

        String[] columns = {"Lesson ID", "Title", "Content", "Progress"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return Boolean.class; // Progress as checkbox
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Make progress column editable only
            }
        };

        table1.setModel(model);

        loadLessons(model, currentUserId, courseId);

        setupProgressCheckbox(courseId, currentUserId);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                UnenrollForm unenrollform = new UnenrollForm();
                unenrollform.setVisible(true);
                dispose();
            }
        });
    }


    private void loadLessons(DefaultTableModel model, String currentUserId, String courseId) {

        Student student = controller.getStudentById(currentUserId);

        if (student == null) {
            JOptionPane.showMessageDialog(this,
                    "Error loading student data!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Lesson> lessons = new ArrayList<>(controller.getLessonsByStudentInCourse(currentUserId, courseId));

        for (Lesson l : lessons) {

            //boolean isDone = student.getProgressLessonIds().contains(l.getLessonId());
            String lessonId = l.getLessonId();

            boolean isDone = student.getProgressByCourse()
                    .getOrDefault(courseId, new ArrayList<>())
                    .contains(lessonId);

            model.addRow(new Object[]{
                    l.getLessonId(),
                    l.getTitle(),
                    l.getContent(),
                    isDone
            });
        }
    }


    private void setupProgressCheckbox(String courseId, String currentUserId) {

        table1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        table1.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {

                JCheckBox check = new JCheckBox();
                check.setSelected(Boolean.TRUE.equals(value));
                check.setHorizontalAlignment(SwingConstants.CENTER);
                return check;
            }
        });

        table1.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {


                if (e.getType() != TableModelEvent.UPDATE) return;

                int row = e.getFirstRow();
                int col = e.getColumn();

                if (col != 3) return;

                boolean newValue = (boolean) table1.getValueAt(row, col);
                String lessonId = table1.getValueAt(row, 0).toString();
                quizController = quizControllers.computeIfAbsent(lessonId, id -> new QuizController(id));


                if (newValue) {
                    //controller.addLessonProgress(currentUserId, lessonId, courseId);
                    {
                        Student student = controller.getStudentById(currentUserId);
                        boolean alreadyDone = student.getProgressByCourse() // byroh ygeb el map bta3t el course da w b3den y3ml check lw el lesson kan mkhlsha el student
                                .getOrDefault(courseId, new ArrayList<>())
                                .contains(lessonId);

                        boolean canRetry = quizController.canStartQuiz();


                        if (!alreadyDone) {
                        int choice = JOptionPane.showConfirmDialog(AccessLessonsForm.this,
                                "Before finishing this lesson you must access it's quiz ,Do you want to start the quiz",
                                "Quiz is Required", JOptionPane.YES_NO_OPTION
                        );
                        if (choice == JOptionPane.YES_OPTION) {

                            if (!canRetry) { // lw 3adet el number of attempts
                                JOptionPane.showMessageDialog(AccessLessonsForm.this, "You finished allowed number of attempts for this quiz");
                                return;
                            }

                            QuizForm quizForm = new QuizForm(courseId, lessonId, currentUserId, quizController);
//                            quizForm.setVisible(true);
//                            dispose();
//                            dispose();
                            setVisible(false);
                            JDialog dialog = new JDialog(AccessLessonsForm.this, "Quiz", true);
                            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog.setContentPane(quizForm.getContentPane());
                            dialog.pack();
                            dialog.setLocationRelativeTo(AccessLessonsForm.this);
                            dialog.setVisible(true); // blocks accesslessonform until quiz is taken
                            setVisible(true);

                            //boolean x = quizForm.passedQuiz();
                            boolean passed = quizForm.passedQuiz();

                            if (passed) { // yes is chosen and quiz is passed
                                controller.addLessonProgress(currentUserId, lessonId, courseId);
                                //loadLessons();
                                table1.setValueAt(true, row, col);
                            } else { // yes is chosen and quiz is failed
                                JOptionPane.showMessageDialog(AccessLessonsForm.this,
                                        "You must pass the quiz to finish this lesson",
                                        "Quiz not passed", JOptionPane.WARNING_MESSAGE);
                                table1.setValueAt(false, row, col);
                            }
                        } else { // no is chosen

                            table1.setValueAt(false, row, col);
                        }
                    }
//                    else{
//                            JOptionPane.showMessageDialog(AccessLessonsForm.this,"You already passed this quiz before");
//                        }
                    }
                }
//                else {
//                    int choice2 = JOptionPane.showConfirmDialog(AccessLessonsForm.this,
//                            "Do you want to remove this finished lesson ? , note: your previous recorded quiz will be removed"
//                            ,"Remove lesson",
//                            JOptionPane.YES_NO_OPTION);
//                    if(choice2==JOptionPane.YES_OPTION) {
//                        controller.setCurrentStudent(currentUserId);
//                        controller.removeLessonProgress(currentUserId, lessonId, courseId);
//                    }
//                    else{
//                        return;
//                    }
//                }
            }
        });
    }
}
