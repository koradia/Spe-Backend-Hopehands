package com.example.hopehands.controller.post;

import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.model.post.Likes;
import com.example.hopehands.model.post.Post;
import com.example.hopehands.model.userdetail.UserDetails;
import com.example.hopehands.repo.auth.CredsRepo;
import com.example.hopehands.repo.org.OrgRepo;
import com.example.hopehands.repo.post.LikeRepo;
import com.example.hopehands.repo.post.PostRepo;
import com.example.hopehands.repo.user.UserRepo;
import com.example.hopehands.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class PostController {
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

    @PostMapping("/upload/post")
    public Post addpostbyorg(@RequestHeader("Authorization") String token, @RequestBody Post post) throws Exception{
        try {
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if(c.getRole()==1 && c.getId() == post.getOid()){
                post.setPostdate(new Date());
                return  postRepo.save(post);
            } else throw new Exception("Not Authorized");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/get/likeduser/{postid}")
    public List<UserDetails> getlikedusers(@RequestHeader("Authorization") String token,@PathVariable int postid) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if((c.getRole()==1)&& postRepo.findByPid(postid).getOid() == c.getId()){
                List<UserDetails> t= new ArrayList<UserDetails>();
                List<Likes> l=likeRepo.xyzpid(postid);
                for(Likes a:l){
                    t.add(userRepo.getByUid(a.getUid()));
                }
                return t;
            }
            else throw new Exception("Unauthorized");
        }catch (Exception e){
            throw e;
        }
    }

//    @PostMapping("/like/user/{uid}/{pid}")
//    public ResponseEntity<Boolean> likeByUser(@RequestHeader("Authorization") String token, @PathVariable int uid,@PathVariable int pid){
//        try {
//            token = token.substring(7);
//            String uname = jwtUtil.extractUsername(token);
//            Creds c = credsRepo.findByUsername(uname);
//            List<Likes> l=likeRepo.xyzuid(uid);
//            for(
//        }
//        catch (Exception e){
//            throw e;
//        }
//    }
    @PostMapping("/like/{pid}")
    public Likes xyzxyz(@RequestHeader("Authorization") String token, @PathVariable int pid) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if(c.getRole()==2){
                int a=postRepo.findByPid(pid).getLikes();
                postRepo.findByPid(pid).setLikes(a+1);
                return likeRepo.save(new Likes(pid,c.getId()));

            }else throw new Exception("Not User");
        }catch (Exception e){
            throw e;
        }
    }
    @PostMapping("/unlike/{pid}")
    public String xyzxyzxyz(@RequestHeader("Authorization") String token, @PathVariable int pid) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);

            if(c.getRole()==2){
                int a=postRepo.findByPid(pid).getLikes();
                postRepo.findByPid(pid).setLikes(a-1);
                Likes temp = new Likes(pid,c.getId());
                likeRepo.delete(temp);
                return "UNLIKED";
            }else throw new Exception("Not User");
        }catch (Exception e){
            throw e;
        }
    }
}
