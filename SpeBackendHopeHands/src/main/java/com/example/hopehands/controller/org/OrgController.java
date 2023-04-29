package com.example.hopehands.controller.org;

import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.model.orgdetail.OrgDetails;
import com.example.hopehands.model.post.Post;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class OrgController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private OrgRepo orgRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CredsRepo credsRepo;

    @PostMapping("/org/savedetail")
    public String adddetail(@RequestHeader("Authorization") String token, @RequestBody OrgDetails orgDetails) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if (c.getRole() != 0) throw new Exception("Chal na admin ko bhej");
            orgRepo.save(orgDetails);
            return "added org successfully";
        }catch (Exception e){
        throw e;
        }
    }

    @PostMapping("/get/orgdetail/{oid}")
    public  OrgDetailResponce getorgdetail(@RequestHeader("Authorization") String token, @PathVariable Integer oid) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            System.out.println(c.getId() +" "+c.getRole());
            if( (c.getRole()==0) || (c.getRole()==1 && c.getId()==oid) ){
                ArrayList<Integer> listofpost= new ArrayList<Integer>();
                List<Post> listp= postRepo.findAllByOid(oid);
                for(Post a: listp){
                    listofpost.add(a.getPid());
                }
                int count=0;
                for(int a:listofpost){
                    count= count + likeRepo.xyzpid(a).size();
                }
                System.out.println("heehehhehe");
                return new OrgDetailResponce(orgRepo.findByOid(oid),count,postRepo.findAllByOid(oid).size());
            }
            else throw new Exception("Not Authorized");
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/org/getallposts/{oid}")
    public List<Post> xyzty(@RequestHeader("Authorization") String token,@PathVariable int oid) throws Exception {
        try {
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if((c.getRole() == 0) || (c.getRole()==1 && c.getId() == oid)){
//                int oid=c.getId();
                List<Post> listp = postRepo.findAllByOid(oid);
                return listp;
            } else throw new Exception("Not Authorized");
        } catch (Exception e) {
            throw e;
        }
    }
}
@NoArgsConstructor
@AllArgsConstructor
@Data
class OrgDetailResponce{
    private OrgDetails orgDetails;
    private int likes,posts;
}
