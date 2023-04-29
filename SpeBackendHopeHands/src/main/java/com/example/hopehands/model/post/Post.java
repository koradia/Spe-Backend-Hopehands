package com.example.hopehands.model.post;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    @NotNull
    private int oid;
    private int likes;
    @NotNull
    private String title,url,detail;
    @Temporal(TemporalType.DATE)
    private Date postdate;
}
