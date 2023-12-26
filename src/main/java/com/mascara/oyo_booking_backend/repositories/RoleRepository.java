package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Role;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : RoleRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT r.* FROM role r WHERE r.role_name = :roleName and r.deleted is false", nativeQuery = true)
    Optional<Role> findByRoleName(@Param("roleName") String name);
}
