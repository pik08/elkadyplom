package elkadyplom.controller;

import elkadyplom.dto.TopicDto;
import elkadyplom.model.Topic;
import elkadyplom.dto.TopicListDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import elkadyplom.service.TopicService;

import java.util.Locale;

@Controller
@RequestMapping(value = "/protected/topics")
public class TopicsController {

    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageSource messageSource;

    @Value("5")
    private int maxResults;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("topicsList");
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        return createListAllResponse(page, locale);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@ModelAttribute("topicDto") TopicDto topicDto,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        topicService.save(topicDto);

//        if (isSearchActivated(searchFor)) {
//            return search(searchFor, page, locale, "message.create.success");
//        }

        return createListAllResponse(page, locale, "message.create.success");
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
//    public ResponseEntity<?> update(@PathVariable("id") int topicId,
//                                    @RequestBody Topic topic,
//                                    @RequestParam(required = false) String searchFor,
//                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
//                                    Locale locale) {
//        if (topicId != topic.getId()) {
//            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
//        }
//
//        topicService.save(topic);
//
//        if (isSearchActivated(searchFor)) {
//            return search(searchFor, page, locale, "message.update.success");
//        }
//
//        return createListAllResponse(page, locale, "message.update.success");
//    }
//
//    @RequestMapping(value = "/{topicId}", method = RequestMethod.DELETE, produces = "application/json")
//    public ResponseEntity<?> delete(@PathVariable("topicId") int topicId,
//                                    @RequestParam(required = false) String searchFor,
//                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
//                                    Locale locale) {
//
//
//        try {
//            topicService.delete(topicId);
//        } catch (AccessDeniedException e) {
//            return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
//        }
//
//        if (isSearchActivated(searchFor)) {
//            return search(searchFor, page, locale, "message.delete.success");
//        }
//
//        return createListAllResponse(page, locale, "message.delete.success");
//    }
//
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
//    public ResponseEntity<?> search(@PathVariable("name") String name,
//                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
//                                    Locale locale) {
//        return search(name, page, locale, null);
//    }
//
//    private ResponseEntity<?> search(String name, int page, Locale locale, String actionMessageKey) {
//        TopicListDto topicListDto = topicService.findByNameLike(page, maxResults, name);
//
//        if (!StringUtils.isEmpty(actionMessageKey)) {
//            addActionMessageToVO(topicListDto, locale, actionMessageKey, null);
//        }
//
//        Object[] args = {name};
//
//        addSearchMessageToVO(topicListDto, locale, "message.search.for.active", args);
//
//        return new ResponseEntity<TopicListDto>(topicListDto, HttpStatus.OK);
//    }

    private TopicListDto listAll(int page) {
        return topicService.findAll(page, maxResults);
    }

    private ResponseEntity<TopicListDto> returnListToUser(TopicListDto topicList) {
        return new ResponseEntity<TopicListDto>(topicList, HttpStatus.OK);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale) {
        return createListAllResponse(page, locale, null);
    }

    private ResponseEntity<?> createListAllResponse(int page, Locale locale, String messageKey) {
        TopicListDto topicListDto = listAll(page);

        addActionMessageToVO(topicListDto, locale, messageKey, null);

        return returnListToUser(topicListDto);
    }

    private TopicListDto addActionMessageToVO(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    private TopicListDto addSearchMessageToVO(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }
}