package com.example.hopehands.controller.user;

//import com.example.noidea2.controller.org.OrgDetailResponce;
import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.model.post.Post;
import com.example.hopehands.model.userdetail.UserDetails;
import com.example.hopehands.repo.auth.CredsRepo;
import com.example.hopehands.repo.org.OrgRepo;
import com.example.hopehands.repo.post.LikeRepo;
import com.example.hopehands.repo.post.PostRepo;
import com.example.hopehands.repo.user.UserRepo;
import com.example.hopehands.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrgRepo orgRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CredsRepo credsRepo;

    @PostMapping("/user/savedetail")
    public Integer saveuserdetail(@RequestBody UserDetails userDetails) throws Exception{
        return userRepo.save(userDetails).getUid();
    }

    @PostMapping("/get/userdetail/{uid}")
    public UserDetailResponce getoradgdetail(@RequestHeader("Authorization") String token, @PathVariable Integer uid) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if(c.getRole()==0){
                return new UserDetailResponce(userRepo.getByUid(uid),likeRepo.xyzuid(uid).size());
            }
            else throw new Exception("Not Authorized");
        }catch (Exception e){
            throw e;
        }
    }
    @PostMapping("/like/user")
    public Boolean getoradgdetail(@RequestHeader("Authorization") String token, @RequestBody RequestLikeObject requestLikeObject) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if(c.getRole()==2 && c.getId()== requestLikeObject.getUid()){
                return likeRepo.existsByPidAndUid(requestLikeObject.getPid(), requestLikeObject.getUid());
            }
            else throw new Exception("Not Authorized");
        }catch (Exception e){
            throw e;
        }
    }
    @PostMapping("/getall/posts")
    public List<Post> getallposts(){
        return postRepo.findAll();
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class UserDetailResponce{
    private UserDetails userDetails;
    private int likes;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class RequestLikeObject{
    private int uid;
    private int pid;
}
