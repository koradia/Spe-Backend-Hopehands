package com.example.hopehands.repo.post;

import com.example.hopehands.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    public List<Post> findAllByOid(int oid);

    public  Post findByPid(int pid);
}
