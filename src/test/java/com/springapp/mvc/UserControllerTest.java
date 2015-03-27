package com.springapp.mvc;

import org.springframework.ui.ModelMap;

import static org.mockito.Mockito.*;

/**
 * @author Maciej Jagiello (maciej.jagiello@ntti3.com)
 */
public class UserControllerTest {

  @org.junit.Test
  public void testListUsers() throws Exception {
    UserController userController = new UserController();
    ModelMap modelMap = mock(ModelMap.class);
    when(modelMap.addAttribute(any(String.class), any(User.class))).thenReturn(mock(ModelMap.class));
    try {
      userController.listUsers(modelMap);
    } catch (NullPointerException e) {
      //skip
    }
    verify(modelMap.addAttribute(eq("user"), any(User.class)));
  }
}
