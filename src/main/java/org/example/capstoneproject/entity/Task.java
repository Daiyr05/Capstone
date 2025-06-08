package org.example.capstoneproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer id;
    private String title;
    private String description;
    private Status  status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private User executor;

}
