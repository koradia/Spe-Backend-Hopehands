package com.example.hopehands.repo.user;

import com.example.hopehands.model.userdetail.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserDetails,Integer> {
    public UserDetails getByUid(int uid);
}
