package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : ProvinceRepository
 */
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    @Query(value = "select p.* from province p where p.province_name = :provincename and p.deleted is false", nativeQuery = true)
    Optional<Province> findByProvinceName(@Param("provincename") String provinceName);

    @Query(value = "select p.* from province p where p.slugs = :provinceslugs and p.deleted is false", nativeQuery = true)
    Optional<Province> findByProvinceSlugs(@Param("provinceslugs") String provinceSlugs);

    @Query(value = "select p.* from province p limit 1", nativeQuery = true)
    List<Province> checkExistData();

    @Query(value = "select p.* from province p where p.province_code = :provincecode and p.deleted is false", nativeQuery = true)
    Optional<Province> findByProvinceCode(@Param("provincecode") String provinceCode);
}
