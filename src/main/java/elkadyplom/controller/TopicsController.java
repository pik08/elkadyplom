package elkadyplom.controller;

import elkadyplom.dto.BasicUserDto;
import elkadyplom.dto.DeclarationDto;
import elkadyplom.dto.TopicDto;
import elkadyplom.dto.TopicListDto;
import elkadyplom.model.Declaration;
import elkadyplom.service.DeclarationService;
import elkadyplom.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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

import javax.annotation.processing.SupportedSourceVersion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Kontroler zarządzający widokiem listy tematów, również podłączonym tam składaniem deklaracji przez studentów.
 */

@Controller
@RequestMapping(value = "/protected/topics")
public class TopicsController {

    /**
     * Numer strony, pokazywanej użytkownikowi, jeśli żadna konkretna strona nie została wybrana.
     */
    private static final String DEFAULT_PAGE_DISPLAYED_TO_USER = "0";

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeclarationService declarationService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Maksymalna liczba wyników na stronie.
     */
    @Value("10")
    private int maxResults;

    /**
     * Metoda zwracająca podstawowy widok listy tematów.
     * @return podstawowy widok listy tematów
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView welcome() {
        return new ModelAndView("topicsList");
    }

    /**
     * Metoda zwracająca listę tematów z danej strony. Zwraca tematy odpowiednie dla typu użytkownika (inne dla admina, inne dla promotora, inne dla studenta).
     * @param page numer strony
     * @param locale informacja o języku
     * @return ResponseEntity z listą tematów
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listAll(@RequestParam int page, Locale locale) {
            return createListResponse(page, locale);
    }

    /**
     * Metoda pobierająca wcześniej utworzone deklaracje danego studenta.
     * @param locale informacja o języku
     * @return ResponseEntity zawierające listę deklaracji danego studenta (w formie dto)
     */
    @Secured("ROLE_STUDENT")
    @RequestMapping(value="/declare", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getDeclarations(Locale locale) {
        List<DeclarationDto> declarationDtos = declarationService.getDeclarationDtos(getCurrentUserEmail());
        if (declarationDtos == null)
            declarationDtos = new ArrayList<DeclarationDto>();
        return new ResponseEntity<List<DeclarationDto>>(declarationDtos, HttpStatus.OK);
    }

    /**
     * Metoda zapisująca deklaracje danego studenta
     * @param declarations tablica z dto opisującymi deklaracje danego studenta (lista nie działa w tym miejscu)
     * @param locale informacja o języku
     * @return ResponseEntity zawierające komunikat o powodzeniu lub błędzie
     */
    @Secured("ROLE_STUDENT")
    @RequestMapping(value="/declare", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveDeclarations(@RequestBody DeclarationDto[] declarations,
                                              Locale locale) {
        if (!declarationService.saveDeclarations(Arrays.asList(declarations), getCurrentUserEmail())) {
            return getBadRequest();
        }

        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    /**
     * Metoda tworząca nowy temat. Dostępna tylko administratorowi (może stworzyć temat o dowolnych paramterach) i promotorowi (może stworzyć niezatwierdzony, swój temat).
     * @param topicDto dto zawierające dane o temacie
     * @param searchFor opcjonalne słowo kluczowe, po którym jest przeprowadzone wyszukiwanie obecnie
     * @param page numer strony obecnie oglądanej
     * @param locale informacja o języku
     * @return ResponseEntity zawierające listę tematów po dodaniu nowego tematu (być może z uwzględnionym wyszukiwaniem) lub zawierające komunikat http bad request
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> create(@ModelAttribute("topic") TopicDto topicDto,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (isAdmin()) {
            if (!topicService.save(topicDto)) {
                return getBadRequest();
            }
        } else if (isSupervisor()) {
            if (!topicService.saveAsSupervisor(topicDto, getCurrentUserEmail())) {
                return getBadRequest();
            }
        } else {
            return getBadRequest();
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.create.success");
        }

        return createListResponse(page, locale, "message.create.success");
    }

    /**
     * Metoda edytująca istniejący temat. Dostępna tylko administratorowi (może edytować dowolny temat) i promotorowi (może edytować swój niezatwierdzony temat).
     * @param topicDto dto zawierające dane o temacie (w tym id!)
     * @param searchFor opcjonalne słowo kluczowe, po którym jest przeprowadzone wyszukiwanie obecnie
     * @param page numer strony obecnie oglądanej
     * @param locale informacja o języku
     * @return ResponseEntity zawierające listę tematów po edycji tematu (być może z uwzględnionym wyszukiwaniem) lub zawierające komunikat http bad request
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> update(@PathVariable("id") int topicId,
                                    @RequestBody TopicDto topicDto,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (isAdmin()) {
            if (!topicService.update(topicDto)) {
                return getBadRequest();
            }
        } else if (isSupervisor()) {
            if (!topicService.updateBySupervisor(topicDto, getCurrentUserEmail())) {
                return getBadRequest();
            }
        } else {
            return getBadRequest();
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.update.success");
        }

        return createListResponse(page, locale, "message.update.success");
    }

    /**
     * Metoda tworząca usuwająca temat. Dostępna tylko administratorowi (może usunąć dowolny temat) i promotorowi (może usunąć niezatwierdzony, swój temat).
     * @param topicId id tematu do usunięcia
     * @param searchFor opcjonalne słowo kluczowe, po którym jest przeprowadzone wyszukiwanie obecnie
     * @param page numer strony obecnie oglądanej
     * @param locale informacja o języku
     * @return ResponseEntity zawierające listę tematów po usunięciu tematu (być może z uwzględnionym wyszukiwaniem) lub zawierające komunikat http bad request
     */
    @RequestMapping(value = "/{topicId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@PathVariable("topicId") int topicId,
                                    @RequestParam(required = false) String searchFor,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        if (isAdmin()) {
            try {
                topicService.delete(topicId);
            } catch (AccessDeniedException e) {
                return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
            }
        } else if (isSupervisor()) {
            if (!topicService.deleteBySupervisor(topicId, getCurrentUserEmail()))
                return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        } else {
            new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
        }

        if (isSearchActivated(searchFor)) {
            return search(searchFor, page, locale, "message.delete.success");
        }

        return createListResponse(page, locale, "message.delete.success");
    }

    /**
     * Metoda realizująca wyszukiwanie po słowie kluczowym (w temacie).
     * @param keyword słowo kluczowe
     * @param page numer obecnie wyswietlanej strony
     * @param locale informacja o języku
     * @return ResponseEntity zawierające listę wyszukanych tematów
     */
    @RequestMapping(value = "/{keyword}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@PathVariable("keyword") String keyword,
                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE_DISPLAYED_TO_USER) int page,
                                    Locale locale) {
        return search(keyword, page, locale, null);
    }

    /**
     * Metoda realizująca wyszukiwanie po słowie kluczowym (w temacie).
     * @param keyword słowo kluczowe
     * @param page numer obecnie wyswietlanej strony
     * @param locale informacja o języku
     * @param actionMessageKey kod wiadomości do przekazania
     * @return ResponseEntity zawierające listę wyszukanych tematów
     */
    private ResponseEntity<?> search(String keyword, int page, Locale locale, String actionMessageKey) {
        TopicListDto topicListDto;

        if (isAdmin())
            topicListDto = topicService.findByKeyword(page, maxResults, keyword);
        else if (isSupervisor())
            topicListDto = topicService.findByKeywordForSupervisor(page, maxResults, keyword, getCurrentUserEmail());
        else
            topicListDto = topicService.findByKeywordForStudents(page, maxResults, keyword);

        if (!StringUtils.isEmpty(actionMessageKey)) {
            addActionMessageToDto(topicListDto, locale, actionMessageKey, null);
        }

        Object[] args = {keyword};

        addSearchMessageToDto(topicListDto, locale, "message.search.for.active", args);
        addUserListsToDto(topicListDto);

        return returnListToUser(topicListDto);
    }

    /**
     * Metoda tworząca dto z listą wszystkich tematów w systemie (konkretnie z obecną stroną), uzupełnioną listami studentów i promotorów.
     * @param page numer obecnie wyświetlana strona
     * @return dto zawierajace tematy, promotorów i studentów
     */
    private TopicListDto listAll(int page) {
        TopicListDto dto =  topicService.findAll(page, maxResults);
        dto.setStudents(userService.getStudentList());
        dto.setSupervisors(userService.getSupervisorList());
        return dto;
    }

    /**
     * Metoda tworząca dto ze stroną z listy tematów dla promotora (tylko tematy do niego przypisane), uzupełnione listą studentów.
     * @param page numer strony
     * @return dto zawierające tematy i listę studentów
     */
    private TopicListDto listForSupervisor(int page) {
        TopicListDto dto = topicService.findBySupervisor(page, maxResults, getCurrentUserEmail());
        dto.setStudents(userService.getStudentList());
        return dto;
    }

    /**
     * Metoda tworząca dto ze stroną tematów dla studenta (tylko tematy zatwierdzone i nieprzypisane do nikogo).
     * @param page numer strony
     * @return dto zawierające tematy
     */
    private TopicListDto listForStudent(int page) {
        return topicService.findForStudents(page, maxResults);
    }

    /**
     * Metoda owijająca dto z listą tematów w ResponseEntity.
     * @param topicList dto z listą tematów
     * @return ResponseEntity zawierające listę tematów
     */
    private ResponseEntity<TopicListDto> returnListToUser(TopicListDto topicList) {
        return new ResponseEntity<TopicListDto>(topicList, HttpStatus.OK);
    }

    /**
     * Metoda wyszukująca listę tematów i tworząca ResponseEntity z dto z listą tematów.
     * @param page numer strony obecnie wyświetlanej
     * @param locale informacja o języku
     * @return ResponseEntity z listą tematów, gotowe do zwrócenia klientowi
     */
    private ResponseEntity<?> createListResponse(int page, Locale locale) {
        return createListResponse(page, locale, null);
    }

    /**
     * Metoda wyszukująca listę tematów i tworząca ResponseEntity z dto z listą tematów.
     * @param page numer strony obecnie wyświetlanej
     * @param locale informacja o języku
     * @param messageKey kod wiadomości
     * @return ResponseEntity z listą tematów, gotowe do zwrócenia klientowi
     */
    private ResponseEntity<?> createListResponse(int page, Locale locale, String messageKey) {
        TopicListDto topicListDto;
        if (isAdmin())
            topicListDto  = listAll(page);
        else if (isSupervisor())
            topicListDto = listForSupervisor(page);
        else
            topicListDto = listForStudent(page);

        addActionMessageToDto(topicListDto, locale, messageKey, null);

        return returnListToUser(topicListDto);
    }

    /**
     * Metoda dodająca wiadomość o działaniu do dto z listą tematów.
     * @param topicListDto dto z listą tematów
     * @param locale informacja o języku
     * @param actionMessageKey kod wiadomości
     * @param args parametry wiadomości
     * @return dto z tematami uzupełnione o wiadomość
     */
    private TopicListDto addActionMessageToDto(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setActionMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    /**
     * Metoda dodająca wiadomość o wyszukiwaniu do dto z listą tematów.
     * @param topicListDto dto z listą tematów
     * @param locale informacja o języku
     * @param actionMessageKey kod wiadomości
     * @param args parametry wiadomości
     * @return dto z tematami uzupełnione o wiadomość
     */
    private TopicListDto addSearchMessageToDto(TopicListDto topicListDto, Locale locale, String actionMessageKey, Object[] args) {
        if (StringUtils.isEmpty(actionMessageKey)) {
            return topicListDto;
        }

        topicListDto.setSearchMessage(messageSource.getMessage(actionMessageKey, args, null, locale));

        return topicListDto;
    }

    /**
     * Metoda sprawdzająca, czy w danym momencie jest uruchomione wyszukiwanie.
     * @param searchFor słowo kluczowe
     * @return true, jeśli jest włączone wyszukiwanie
     */
    private boolean isSearchActivated(String searchFor) {
        return !StringUtils.isEmpty(searchFor);
    }

    /**
     * Metoda tworząca ResponseEntity z informacją o Bad Request.
     * @return ResponseEntity ze statusem http Bad Request
     */
    private ResponseEntity<?> getBadRequest() {
        return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Metoda sprawdzająca, czy obecny użytkownik jest administratorem.
     * @return true, jeśli użytkownik jest administratorem
     */
    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    /**
     * Metoda sprawdzająca, czy obecny użytkownik jest promotorem.
     * @return true, jeśli użytkownik jest promotorem
     */
    private boolean isSupervisor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
    }

    /**
     * Metoda zwracająca email (login) obecnego użytkownika.
     * @return email obecnego użytkownika
     */
    private String getCurrentUserEmail() {
        return ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getUsername() ;
    }

    /**
     * Metoda uzupełniająca dto listą studentów i ew. listą promotorów (dla admina).
     * @param topicListDto dto z listą tematów
     */
    private void addUserListsToDto(TopicListDto topicListDto) {
        if (isAdmin()) {
            topicListDto.setSupervisors(userService.getSupervisorList());
            topicListDto.setStudents(userService.getStudentList());
        } else if (isAdmin()) {
            topicListDto.setStudents(userService.getStudentList());
        }
    }
}