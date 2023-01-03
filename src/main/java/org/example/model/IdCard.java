package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "ID_CARD")
public class IdCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean isActive;

    @OneToOne(mappedBy = "card")
    private Student student;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "IdCard{" +
                "id=" + id +
                ", isActive=" + isActive +
                '}';
    }
}
