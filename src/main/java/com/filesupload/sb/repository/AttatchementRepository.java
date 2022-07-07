package com.filesupload.sb.repository;

import com.filesupload.sb.entity.Attachement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttatchementRepository extends JpaRepository<Attachement,Long> {
}
