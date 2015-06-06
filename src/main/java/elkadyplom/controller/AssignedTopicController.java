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

/**
 * Kontroler obsługujący stronę z przypisanymi tematami.
 */

@Controller
@RequestMapping(value = "/protected/assignedTopic")
public class AssignedTopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Liczba winików na stronie.
     */
    @Value("1")
    private int maxResults;

    /**
     * Metoda zwracająca widok tej strony.
     * @return widok
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("assignedTopic");
    }

    /**
     * Metoda pobierająca listę przypisanych tematów.
     * @param page numer strony
     * @param locale informacja o jezyku
     * @return ResponseEntity z listą tematów
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
        if (isStudent())
            return createListResponse(page, locale, false);
        return createListResponse(page, locale, true);
    }

    /**
     * Metoda tworząca ResponseEntity z listą tematów.
     * @param page numer strony
     * @param locale informacja o języku
     * @param all czy wybrać wszystkie tematy
     * @return ResponseEntity z listą tematów
     */
    private ResponseEntity<?> createListResponse(int page, Locale locale, boolean all) {
        return createListResponse(page, locale, null, all);
    }

    /**
     * Metoda tworząca ResponseEntity z listą tematów.
     * @param page numer strony
     * @param locale informacja o języku
     * @param all czy wybrać wszystkie tematy
     * @param messageKey kod wiadomości
     * @return ResponseEntity z listą tematów
     */
    private ResponseEntity<?> createListResponse(int page, Locale locale, String messageKey, boolean all) {
        TopicListDto topicListDto;
        if (all)
            topicListDto  = listAll(page);
        else
            topicListDto = listForStudent(page);

        addActionMessageToDto(topicListDto, locale, messageKey, null);

        return returnListToUser(topicListDto);
    }

    /**
     * Metoda zbierająca wszystkie tematy.
     * @param page numer strony
     * @return dto z listą tematów
     */
    private TopicListDto listAll(int page) {
        return topicService.findAll(page, maxResults);
    }

    /**
     * Metoda zbierająca wszystkie tematy dla studenta.
     * @param page numer strony
     * @return dto z listą tematów
     */
    private TopicListDto listForStudent(int page) {
        String supervisorEmail = getCurrentUserEmail();
        return topicService.findByAssignedStudent(page, maxResults, supervisorEmail);

    }

    /**
     * Metoda dołączająca wiadomość do dto.
     * @param topicListDto dto
     * @param locale informacja o języku
     * @param actionMessageKey kod wiadomości
     * @param args argumenty wiadomości
     * @return dto z wiadomością
     */
    private TopicListDto addActionMessageToDto(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    /**
     * Metoda otaczająca dto w ResponseEntity
     * @param topicList dto
     * @return ResponseEntity z dto
     */
    private ResponseEntity<TopicListDto> returnListToUser(TopicListDto topicList) {
        return new ResponseEntity<TopicListDto>(topicList, HttpStatus.OK);
    }

    /**
     * Metoda pobierająca login obecnego użytkownika.
     * @return email
     */
    private String getCurrentUserEmail() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername() ;
    }

    /**
     * Metoda sprawdzająca, czy użytkownik jest studentem.
     * @return true jeśli użytkownik jest studentem
     */
    private boolean isStudent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }
}
