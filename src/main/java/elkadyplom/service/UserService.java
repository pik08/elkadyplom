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

/**
 * Warstwa serwisowa, zajmuje się obsługą użytkowników.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Metoda wyszukująca użytkownika po adresie email (loginie).
     * @param email adres email użytkownika
     * @return użytkownik
     */
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Metoda wyszukujaca wszystkich promotorów w systemie i tworząca listę dto z informacjami o nic.
     * @return lista dto z informacjami o promotorach
     */
    public List<BasicUserDto> getSupervisorList() {
        List<User> list = userRepository.getUserListByRole(Role.ROLE_SUPERVISOR);
        List<BasicUserDto> supervisorList = new ArrayList<BasicUserDto>();

        for (User u : list)
            supervisorList.add(new BasicUserDto(u));

        return supervisorList;
    }

    /**
     * Metoda wyszukujaca wszystkich studentów w systemie i tworząca listę dto z informacjami o nic.
     * @return lista dto z informacjami o studentach
     */
    public List<BasicUserDto> getStudentList() {
        List<User> list = userRepository.getUserListByRole(Role.ROLE_STUDENT);
        List<BasicUserDto> studentList = new ArrayList<BasicUserDto>();

        for (User u : list)
            studentList.add(new BasicUserDto(u));

        return studentList;
    }

    /**
     * Metoda zapisująca użytkownika w bazie.
     * @param user uzytkownik do zapisania
     */
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
