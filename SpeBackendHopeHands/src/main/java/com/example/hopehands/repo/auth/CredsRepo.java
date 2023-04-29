package com.example.hopehands.repo.auth;

import com.example.hopehands.model.auth.Creds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredsRepo extends JpaRepository<Creds,Integer> {

    Creds findByUsername(String username);


    Creds findById(int id);

}
