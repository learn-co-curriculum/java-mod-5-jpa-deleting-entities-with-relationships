package org.example;

import org.example.model.Student;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

public class JpaQuery {

    public static void main(String[] args) {
        // create EntityManager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Create a Query object to get student based on dob.  Need to cast query result to Student
        Query query1 = entityManager.createQuery("SELECT s FROM Student s WHERE s.dob='2000-01-01'");
        //Need to cast result to Student
        Student student1 =  (Student) query1.getSingleResult();
        System.out.println(student1);

        //Create a TypedQuery object.
        TypedQuery<Student> query2 = entityManager.createQuery("SELECT s FROM Student s WHERE s.dob=:dob", Student.class);
        //set the :dob placeholder in the query
        query2.setParameter("dob", LocalDate.of(1999,01,01));
        //no need to cast result since Student.class was passed to `createQuery` method
        Student student2 =  query2.getSingleResult();
        System.out.println(student2);

        //Create a TypeQuery object and get a list of Student entities as a result
        TypedQuery<Student> query3 = entityManager.createQuery("select s FROM Student s WHERE s.studentGroup IN ('DAISY', 'ROSE')", Student.class);
        List<Student> students  =  query3.getResultList();
        System.out.println(students);

        // close entity manager and factory
        entityManager.close();
        entityManagerFactory.close();
    }

}
