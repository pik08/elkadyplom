package elkadyplom.controller;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.dto.TopicDto;
import elkadyplom.dto.TopicListDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import elkadyplom.service.TopicService;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(value = "/protected/topics")
public class TopicsController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageSource messageSource;

    @Value("10")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("topicsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        if (isAdmin())
            return createListResponse(page, locale, true);      // wszystkie tematy
        else if (isSupervisor())
            return createListResponse(page, locale, false);        // tylko tematy do niego przypisane
        else
            return getBadRequest();
    }

    @RequestMapping(value="/supervisors", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getSupervisors(Locale locale) {
        List<BasicUserDto> supervisorList = topicService.getSupervisorList();

        return new ResponseEntity<List<BasicUserDto>>(supervisorList, HttpStatus.OK);
    }

    @RequestMapping(value="/students", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getStudents(Locale locale) {
        List<BasicUserDto> studentList = topicService.getStudentList();

        return new ResponseEntity<List<BasicUserDto>>(studentList, HttpStatus.OK);
    }


    @RequestMapping(value="/isAdmin", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> isAdmin(Locale locale) {
        return new ResponseEntity<Boolean>(isAdmin(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@ModelAttribute("topic") TopicDto topicDto,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        boolean all = true;
        if (isAdmin()) {
            if (!topicService.save(topicDto)) {
                return getBadRequest();
            }
        } else if (isSupervisor()) {
            all = false;
            if (!topicService.saveAsSupervisor(topicDto, getCurrentUserEmail())) {
                return getBadRequest();
            }
        } else {
            return getBadRequest();
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListResponse(page, locale, "message.create.success", all);
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int topicId,
                                    @RequestBody TopicDto topicDto,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        boolean all = true;
        if (isAdmin()) {
            if (!topicService.update(topicDto)) {
                return getBadRequest();
            }
        } else if (isSupervisor()) {
            all = false;
            if (!topicService.updateBySupervisor(topicDto, getCurrentUserEmail())) {
                return getBadRequest();
            }
        } else {
            return getBadRequest();
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }

        return createListResponse(page, locale, "message.update.success", all);
    }

    @RequestMapping(value = "/{topicId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("topicId") int topicId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        boolean all = true;
        if (isAdmin()) {
            try {
                topicService.delete(topicId);
            } catch (AccessDeniedException e) {
                return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
            }
        } else if (isSupervisor()) {
            all = false;
            if (!topicService.deleteBySupervisor(topicId, getCurrentUserEmail()))
                return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        } else {
            new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }

        return createListResponse(page, locale, "message.delete.success", all);
    }

    @RequestMapping(value = "/{keyword}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("keyword") String keyword,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(keyword, page, locale, null);
    }

    private ResponseEntity<?> search(String keyword, int page, Locale locale, String actionMessageKey) {
        TopicListDto topicListDto;

        if (isAdmin())
            topicListDto = topicService.findByKeyword(page, maxResults, keyword);
        else if (isSupervisor())
            topicListDto = topicService.findByKeywordForSupervisor(page, maxResults, keyword, getCurrentUserEmail());
        else
            return getBadRequest();

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToDto(topicListDto, locale, actionMessageKey, null);
        }

        Object[] args = {keyword};

        addSearchMessageToDto(topicListDto, locale, "message.search.for.active", args);

        return new ResponseEntity<TopicListDto>(topicListDto, HttpStatus.OK);
    }

    private TopicListDto listAll(int page) {
        return topicService.findAll(page, maxResults);
    }

    private TopicListDto listForSupervisor(int page) {
        String supervisorEmail = getCurrentUserEmail();
        return topicService.findBySupervisor(page, maxResults, supervisorEmail);

    }

    private ResponseEntity<TopicListDto> returnListToUser(TopicListDto topicList) {
        return new ResponseEntity<TopicListDto>(topicList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListResponse(int page, Locale locale, boolean all) {
        return createListResponse(page, locale, null, all);
    }

    private ResponseEntity<?> createListResponse(int page, Locale locale, String messageKey, boolean all) {
        TopicListDto topicListDto;
        if (all)
           topicListDto  = listAll(page);
        else
            topicListDto = listForSupervisor(page);

        addActionMessageToDto(topicListDto, locale, messageKey, null);

        return returnListToUser(topicListDto);
    }

    private TopicListDto addActionMessageToDto(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    private TopicListDto addSearchMessageToDto(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }

    private ResponseEntity<?> getBadRequest() {
        return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private boolean isSupervisor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
    }

    private String getCurrentUserEmail() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername() ;
    }
}