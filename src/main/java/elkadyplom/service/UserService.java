package elkadyplom.service;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import elkadyplom.model.User;
import elkadyplom.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<BasicUserDto> getSupervisorList() {
        List<User> list = userRepository.getUserListByRole(Role.ROLE_SUPERVISOR);
        List<BasicUserDto> supervisorList = new ArrayList<BasicUserDto>();

        for (User u : list)
            supervisorList.add(new BasicUserDto(u));

        return supervisorList;
    }


    public List<BasicUserDto> getStudentList() {
        List<User> list = userRepository.getUserListByRole(Role.ROLE_STUDENT);
        List<BasicUserDto> studentList = new ArrayList<BasicUserDto>();

        for (User u : list)
            studentList.add(new BasicUserDto(u));

        return studentList;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
