package org.example.lab5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(max = 255, message = "Course name must be 255 characters or fewer")
    private String name;

    @NotNull(message = "Instructor is required")
    private Long instructor;

    @NotNull(message = "Course size is required")
    @Min(value = 1, message = "Course size must be at least 1")
    private Integer maxSize;

    @NotBlank(message = "Room is required")
    @Size(max = 255, message = "Room must be 255 characters or fewer")
    private String room;

    @ManyToMany
    @JoinTable(
            name = "course_roster",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> roster = new HashSet<>();

    public Course() {
    }

    public Course(Long id, String name, Long instructor, Integer maxSize, String room, Set<Student> roster) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.maxSize = maxSize;
        this.room = room;
        this.roster = roster;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getInstructor() {
        return instructor;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public String getRoom() {
        return room;
    }

    public Set<Student> getRoster() {
        return roster;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructor(Long instructor) {
        this.instructor = instructor;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setRoster(Set<Student> roster) {
        this.roster = roster;
    }

    public int getEnrollmentCount() {
        return roster == null ? 0 : roster.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}