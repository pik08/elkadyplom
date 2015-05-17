package elkadyplom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import elkadyplom.model.User;
import elkadyplom.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
