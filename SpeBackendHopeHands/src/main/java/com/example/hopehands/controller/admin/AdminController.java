package com.example.hopehands.controller.admin;

import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.model.orgdetail.OrgDetails;
import com.example.hopehands.model.userdetail.UserDetails;
import com.example.hopehands.repo.auth.CredsRepo;
import com.example.hopehands.repo.org.OrgRepo;
import com.example.hopehands.repo.post.LikeRepo;
import com.example.hopehands.repo.post.PostRepo;
import com.example.hopehands.repo.user.UserRepo;
import com.example.hopehands.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrgRepo orgRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private PostRepo postRepo;

    @PostMapping("/org/getall")
    public List<OrgDetails> getallorg(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=0) throw new Exception("Not admin");
            return orgRepo.findAll();
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/users/getall")
    public List<UserDetails> getalluser(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=0) throw new Exception("Not admin");
            return userRepo.findAll();
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/posts/getcount")
    public Integer getpostcount(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=0) throw new Exception("Not admin");
            return postRepo.findAll().size();
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/likes/getcount")
    public Integer getlikescount(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=0) throw new Exception("Not admin");
            return likeRepo.findAll().size();
        }catch (Exception e){
            throw e;
        }
    }
}
