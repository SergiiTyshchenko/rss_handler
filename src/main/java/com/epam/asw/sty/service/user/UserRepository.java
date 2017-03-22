package com.epam.asw.sty.service.user;

import java.util.List;

import com.epam.asw.sty.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByLastName(String lastName);
}