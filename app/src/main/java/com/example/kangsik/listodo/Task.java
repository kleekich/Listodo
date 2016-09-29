package com.example.kangsik.listodo;

import java.util.*;
/**
 * Created by Kangsik on 9/28/16.
 */

public class Task {
    private String title;
    private String description;
    private Date dueDate;

    public Task(String t, String d, Date dd){
        this.title = t;
        this.description = d;
        this.dueDate = dd;
    }

}
