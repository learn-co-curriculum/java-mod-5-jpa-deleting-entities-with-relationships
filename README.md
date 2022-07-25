# Deleting Entities with Relationships

## Learning Goals

- Explain cascade deletion.
- Define cascade behavior of model.

## Introduction

The model relationships currently look like this:

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3a286a6f-e03f-48c1-90dc-f90bd62b8bc0/Untitled.png)

If we remove a student record from the `STUDENT_DATA` table it is not
immediately clear what will happen to the records that reference the deleted
student record. In this lesson, we will look at how to control deletion
behavior.

## Cascade Deletion

A record can be deleted if relationships involve a separate table (many-to-many
relationship) or does not involve deleting related data. For tables that have
foreign keys (one-to-many) such as the `PROJECTS` table, we need to explicitly
tell JPA that we want them to be removed if a student record is deleted.

We can use the `cascade = CascadeType.REMOVE` with the `@OneToMany` annotation
to define this behavior. Cascade means to pass on a behavior down the
relationship chain. When a student record is deleted, all of its associated
projects will also be removed.

```java
// Student.java

@Entity
@Table(name = "STUDENT_DATA")
public class Student {
    ...

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Project> projects = new ArrayList<>();

		...
}
```

The only thing we changed here is that we added the `cascade` property to the
`@OneToMany` annotation. You can test out the behavior by running the
`JpaDelete` class (set `hibernate.hbm2ddl.auto` to `none`). Check out
[the docs for more cascade types](https://docs.oracle.com/javaee/7/api/javax/persistence/CascadeType.html).

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

## Conclusion

In this lesson, we learned how to handle deletion of related data. It is
important to explicitly define deletion behavior to avoid errors during runtime.
