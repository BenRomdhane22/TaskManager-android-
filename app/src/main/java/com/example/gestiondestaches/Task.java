package com.example.gestiondestaches;

public class Task {
    private int id;
    private String title;
    private String description;
    private String date;
    private String status;

    // Constructeur
    public Task(String title, String description, String date, String status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public Task(int id, String title, String description, String date, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    // Getters et Setters

    public String getTitle() {
        return title;
    }
    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}

