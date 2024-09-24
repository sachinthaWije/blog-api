package dev.sachi.blogsystem.repository;

import dev.sachi.blogsystem.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
    Boolean existsByUsername(String username);

}
