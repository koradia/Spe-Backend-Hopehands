package com.example.hopehands.repo.post;

import com.example.hopehands.model.post.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepo extends JpaRepository<Likes,Likes> {

    @Query("select l from Likes l where l.pid = ?1")
    public List<Likes> xyzpid(int pid);

    @Query("select l from Likes l where l.uid = ?1")
    public List<Likes> xyzuid(int uid);

    public boolean existsByPidAndUid(int pid,int uid);
}
