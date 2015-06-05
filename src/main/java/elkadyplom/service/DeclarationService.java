package elkadyplom.service;

import elkadyplom.dto.DeclarationDto;
import elkadyplom.model.Declaration;
import elkadyplom.model.Topic;
import elkadyplom.model.User;
import elkadyplom.repository.DeclarationRepository;
import elkadyplom.repository.TopicsRepository;
import elkadyplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DeclarationService {

    @Autowired
    private TopicsRepository topicsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeclarationRepository declarationRepository;


    @Transactional
    public boolean saveDeclarations(List<DeclarationDto> declarationDtoList, String studentEmail) {
        if (declarationDtoList == null)
            return false;

        User student = userRepository.findByEmail(studentEmail);
        if (student == null || !student.isStudent())
            return false;

        Topic topic;

        declarationRepository.deleteByStudent(student);

        for (DeclarationDto dto : declarationDtoList) {
            topic = topicsRepository.findOne(dto.getTopicId());
            if (topic == null)
                return false;
            Declaration d = new Declaration(student, topic, dto.getRank());
            declarationRepository.save(d);
        }

        return true;
    }

    public List<DeclarationDto> getDeclarationDtos(String currentUserEmail) {
        if (currentUserEmail == null)
            return null;

        User student = userRepository.findByEmail(currentUserEmail);
        if (student == null || !student.isStudent())
            return null;

        List<Declaration> declarations = declarationRepository.findByStudent(student);
        if (declarations == null)
            return null;

        List<DeclarationDto> dtoList = new ArrayList<DeclarationDto>();
        for (Declaration d : declarations)
            dtoList.add(new DeclarationDto(d));

        return dtoList;
    }

}
