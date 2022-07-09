package com.drm.mitra.authentication.repository;

import com.drm.mitra.authentication.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDataRepository extends JpaRepository<UserData,UUID> {

    @Query(value = "SELECT * FROM user_data a WHERE a.username=?1",nativeQuery = true)
    UserData findByUserName(String userName);
}
