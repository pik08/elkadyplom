package elkadyplom.controller;

import elkadyplom.dto.TopicListDto;
import elkadyplom.service.TopicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequestMapping(value = "/protected/assignedTopic")
public class AssignedTopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageSource messageSource;

    @Value("1")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("assignedTopic");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        if (isStudent())
            return createListResponse(page, locale, false);          // wszystkie tematy
        return createListResponse(page, locale, true);
    }

    private ResponseEntity<?> createListResponse(int page, Locale locale, boolean all) {
        return createListResponse(page, locale, null, all);
    }

    private ResponseEntity<?> createListResponse(int page, Locale locale, String messageKey, boolean all) {
        TopicListDto topicListDto;
        if (all)
            topicListDto  = listAll(page);
        else
            topicListDto = listForStudent(page);

        addActionMessageToDto(topicListDto, locale, messageKey, null);

        return returnListToUser(topicListDto);
    }

    private TopicListDto listAll(int page) {
        return topicService.findAll(page, maxResults);
    }

    private TopicListDto listForStudent(int page) {
        String supervisorEmail = getCurrentUserEmail();
        return topicService.findByAssignedStudent(page, maxResults, supervisorEmail);

    }

    private TopicListDto addActionMessageToDto(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    private ResponseEntity<TopicListDto> returnListToUser(TopicListDto topicList) {
        return new ResponseEntity<TopicListDto>(topicList, HttpStatus.OK);
    }

    private String getCurrentUserEmail() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername() ;
    }

    private boolean isStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }
}
