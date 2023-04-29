package com.example.hopehands.repo.org;

import com.example.hopehands.model.orgdetail.OrgDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepo extends JpaRepository<OrgDetails,Integer> {
    public OrgDetails findByOid(int oid);
}
