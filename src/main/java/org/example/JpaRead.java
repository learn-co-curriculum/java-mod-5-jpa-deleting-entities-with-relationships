package org.example;

import org.example.model.IdCard;
import org.example.model.Project;
import org.example.model.Student;
import org.example.model.Subject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JpaRead {
    public static void main(String[] args) {
        // create EntityManager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // get student data using primary key id=1
        Student student1 = entityManager.find(Student.class, 1);
        System.out.println(student1);
        // get the student's id card
        IdCard card1 = student1.getCard();
        System.out.println(card1);
        // get the student's projects
        List<Project> projects = student1.getProjects();
        System.out.println(projects);
        // get the subject's students
        Subject subject = entityManager.find(Subject.class, 11);
        System.out.println(subject.getStudents());
        // get the student's subjects
        System.out.println(student1.getSubjects());

        // close entity manager and factory
        entityManager.close();
        entityManagerFactory.close();
    }
}