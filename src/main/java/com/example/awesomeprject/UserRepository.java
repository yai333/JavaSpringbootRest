package com.example.awesomeprject;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.lastName like CONCAT('%',:name,'%')")
    List<User> findByLastName(@Param("name") String name);

    @Query("select u from User u")
    List<User> findAll();

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.firstName = ?1 and u.lastName = ?2")
    public Boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
