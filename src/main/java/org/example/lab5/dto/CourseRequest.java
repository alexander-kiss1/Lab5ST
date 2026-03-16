package org.example.lab5.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CourseRequest {

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

    private Set<Long> roster;

    public CourseRequest() {
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

    public Set<Long> getRoster() {
        return roster;
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

    public void setRoster(Set<Long> roster) {
        this.roster = roster;
    }
}