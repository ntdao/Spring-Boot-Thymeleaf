package com.example.user;

import com.example.role.Role;
import com.example.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;

    public List<User> listAll(){
        return (List<User>) userRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException {
        Optional<User> result = userRepo.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID "+ id);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void delete(Integer id) throws UserNotFoundException {
        Integer count = userRepo.countById(id);
        if(count == null || count == 0){
            throw new UserNotFoundException("Could not find any users with ID "+ id);
        }
        userRepo.deleteById(id);
    }
}
