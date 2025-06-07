package org.example.capstoneproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private String description;
    private Status  status;
    private Date dueDate;
    private User executor;

}
