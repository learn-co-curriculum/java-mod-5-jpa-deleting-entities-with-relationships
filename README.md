# Deleting Entities with Relationships

## Learning Goals

- Explain cascade deletion.
- Define cascade behavior of model.

## Introduction

The model relationships currently look like this:

![Many to many relationship table](https://curriculum-content.s3.amazonaws.com/6036/java-mod-5-jpa-manytomany/student_subject_nojointable.png)

If we remove a student from the database, it is not
immediately clear what will happen to the rows that reference the deleted
student row (i.e. associated projects, id cards, and subjects)
In this lesson, we will look at how to control deletion behavior.

## Cascade Deletion

A row can be deleted if relationships involve a separate table (many-to-many
relationship) or does not involve deleting related data. For tables that have
foreign keys (one-to-many) such as the `PROJECTS` table or (one-to-one) such as
the `ID_CARD` table, we need to explicitly
tell JPA that we want them to be removed if an associated student is deleted.

We can use the `cascade = CascadeType.REMOVE` with the `@OneToOne`
and `@OneToMany` annotations  to define this behavior.
Cascade means to pass on a behavior down the
relationship chain. When a student is deleted, all of its associated
projects will also be removed, as well as the associated id card.

Edit the `Student` class to add the property `cascade = CascadeType.REMOVE`
to the `@OneToOne` and `@OneToMany` annotations:

```java
// Student.java

@Entity
public class Student {
    ...

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private IdCard card;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();
    
    ...
}
```

Note that we don't want to cascade student deletions to the subject table,
since the relationship is many-to-many and other students may take the subject.
However, JPA knows to remove rows associated with the deleted student
from the many-to-many join table.

1. Edit `persistence.xml` and set `hibernate.hbm2ddl.auto` to `none`.
2. Run the `JpaDelete.main` method to delete the student with id `1`. 


```java
package org.example;

import org.example.models.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaDelete {
    public static void main(String[] args) {
        // create EntityManager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // get record
        Student student1 = entityManager.find(Student.class, 1);

        // access transaction object
        EntityTransaction transaction = entityManager.getTransaction();

        // create and use transaction to save updated value
        transaction.begin();
        entityManager.remove(student1);
        transaction.commit();

        // close entity manager
        entityManager.close();
        entityManagerFactory.close();
    }
}
```

Use **pgAdmin** to confirm the student is deleted:

`SELECT * FROM STUDENT;`

| ID  | DOB        | NAME | STUDENTGROUP |
|-----|------------|------|--------------|
| 2   | 1999-01-01 | Lee  | DAISY        |
| 3   | 1980-01-01 | Amal | LOTUS        |


Confirm the deletion of the student caused their projects to be deleted:

`SELECT * FROM PROJECT;`

| ID   | SCORE  | SUBMISSIONDATE  | TITLE                        | STUDENT_ID   |
|------|--------|-----------------|------------------------------|--------------|
| 9    | 87     | 2022-07-30      | Mars Rover Presentation      | 2            |


Confirm the deletion of the student caused their id card to be deleted:

`SELECT * FROM ID_CARD;`

| ID  | ISACTIVE  | 
|-----|-----------|
| 5   | false     |
| 6   | false     |

Confirm the deletion of the student removed rows associated with
the subjects they were taking:

`SELECT * FROM STUDENT_SUBJECT;`

| STUDENTS_ID | SUBJECTS_ID | 
|-------------|-------------|
| 2           | 11          |
| 2           | 12          |


Confirm the associated subjects did not get deleted:


`SELECT * FROM SUBJECT;`

| ID  | TITLE           | 
|-----|-----------------|
| 10  | Arts and Crafts |
| 11  | Reading         |
| 12  | Math            |


## Code Check

The final version of `Student` should look like:

```java
package org.example.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private StudentGroup studentGroup;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private IdCard card;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();

    @ManyToMany
    private List<Subject> subjects = new ArrayList<>();

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setStudent(this);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public IdCard getCard() {
        return card;
    }

    public void setCard(IdCard card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", studentGroup=" + studentGroup +
                '}';
    }
}
```

## Conclusion

In this lesson, we learned how to handle deletion of related data. It is
important to explicitly define deletion behavior to avoid data inconsistency,
particularly with one-to-one and one-to-many relationships.

You can [fork and clone](https://github.com/learn-co-curriculum/java-mod-5-jpa-deleting-entities-with-relationships) the final version of code.


## Resources

[javax.persistence.CascadeType](https://docs.oracle.com/javaee/7/api/javax/persistence/CascadeType.html)