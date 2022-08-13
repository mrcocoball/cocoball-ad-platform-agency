package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUserRepository extends JpaRepository<ClientUser, String> {
}
