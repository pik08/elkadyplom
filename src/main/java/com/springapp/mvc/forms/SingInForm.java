package com.springapp.mvc.forms;

/**
 * Created by PK on 4/8/2015.
 */
public class SingInForm
{
    public enum Who {Promoter, Admin, Student};
    private String username;
    private String password;
    private boolean staySigned = false;
    private Who who = Who.Promoter;

    public Who getWho()
    {
        return who;
    }

    public void setWho(Who who)
    {
        this.who = who;
    }

    public boolean isStaySigned()
    {

        return staySigned;
    }

    public void setStaySigned(boolean staySigned)
    {
        this.staySigned = staySigned;
    }

    public String getPassword()
    {

        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {

        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
