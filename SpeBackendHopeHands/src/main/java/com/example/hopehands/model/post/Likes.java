package com.example.hopehands.model.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
@IdClass(Likes.class)
public class Likes implements Serializable {
    @Id
    private int pid;
    @Id
    private int uid;
}
