package com.example.hopehands.model.orgdetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orgdetail")
public class OrgDetails {
    @Id
    private int oid;
    private String name,email,mobile,address;
    @Column(unique = true)
    private String lic;
}
