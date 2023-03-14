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