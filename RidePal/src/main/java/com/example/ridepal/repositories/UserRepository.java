package com.example.ridepal.repositories;

import com.example.ridepal.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Page<User> findAll(Specification<User> filters, Pageable pageable);

    User findById(int id);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    User findByUsername(String username);

    @Query(value = "select count(*) from users", nativeQuery = true)
    int getUsersCount();

    List<User> findListByUsername(String username);

    List<User> findListByEmail(String email);
}
