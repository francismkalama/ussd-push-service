package com.kcbgroup.repositories;

import com.kcbgroup.models.USSDPushrequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NIUSSDPushRepository extends JpaRepository<USSDPushrequest, Long> {
}
