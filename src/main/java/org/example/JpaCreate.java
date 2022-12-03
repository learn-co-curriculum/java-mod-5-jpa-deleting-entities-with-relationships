package org.example;

import org.example.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class JpaCreate {
    public static void main(String[] args) {
        // create a new student instance
        Student student1 = new Student();
        student1.setName("Jack");
        student1.setDob(LocalDate.of(2000,1,1));
        student1.setStudentGroup(StudentGroup.ROSE);

        // create a new student instance
        Student student2 = new Student();
        student2.setName("Lee");
        student2.setDob(LocalDate.of(1999,1,1));
        student2.setStudentGroup(StudentGroup.DAISY);

        // create a new student instance
        Student student3 = new Student();
        student3.setName("Amal");
        student3.setDob(LocalDate.of(1980,1,1));
        student3.setStudentGroup(StudentGroup.LOTUS);


        // create id cards
        IdCard card1 = new IdCard();
        card1.setActive(true);
        IdCard card2 = new IdCard();
        IdCard card3 = new IdCard();

        // create projects
        Project project1 = new Project();
        project1.setTitle("Ant Hill Diorama");
        project1.setSubmissionDate(LocalDate.of(2022, 7, 20));
        project1.setScore(85);

        Project project2 = new Project();
        project2.setTitle("Saturn V Poster Presentation");
        project2.setSubmissionDate(LocalDate.of(2022, 7, 25));
        project2.setScore(90);

        Project project3 = new Project();
        project3.setTitle("Mars Rover Presentation");
        project3.setSubmissionDate(LocalDate.of(2022, 7, 30));
        project3.setScore(87);


        // create student-card associations
        student1.setCard(card1);
        student2.setCard(card2);
        student3.setCard(card3);

        // create student -< projects associations
        project1.setStudent(student1);
        project2.setStudent(student1);
        project3.setStudent(student2);

        student1.addProject(project1);
        student1.addProject(project2);
        student2.addProject(project3);

        // create subjects
        Subject subject1 = new Subject();
        subject1.setTitle("Arts and Crafts");
        Subject subject2 = new Subject();
        subject2.setTitle("Reading");
        Subject subject3 = new Subject();
        subject3.setTitle("Math");

        // create students >-< subjects association
        student1.addSubject(subject1);
        student1.addSubject(subject2);

        student2.addSubject(subject2);
        student2.addSubject(subject3);

        // create EntityManager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // access transaction object
        EntityTransaction transaction = entityManager.getTransaction();

        // create and use transactions
        transaction.begin();

        //persist the students
        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(student3);

        //persist the id cards
        entityManager.persist(card1);
        entityManager.persist(card2);
        entityManager.persist(card3);

        //persist the projects
        entityManager.persist(project1);
        entityManager.persist(project2);
        entityManager.persist(project3);

        //persist the subjects
        entityManager.persist(subject1);
        entityManager.persist(subject2);
        entityManager.persist(subject3);

        transaction.commit();

        //close entity manager and factory
        entityManager.close();
        entityManagerFactory.close();
    }
}
