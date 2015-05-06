package com.springapp.mvc.grains;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by User on 2015-05-02.
 */
@Component
@Scope("session")
public class UserSession
{
    String roleTmp;

    public String getRoleTmp() {
        return roleTmp;
    }

    public void setRoleTmp(String roleTmp) {
        this.roleTmp = roleTmp;
    }
}
