package com.springapp.mvc.grains;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by PK on 4/19/2015.
 */
public class Basket implements Serializable
{
    int id;
    String name;
    Date dateCreate;
    Date dateEdit;
    int contain;

    public int getContain()
    {
        return contain;
    }

    public void setContain(int contain)
    {
        this.contain = contain;
    }

    public Date getDateEdit()
    {

        return dateEdit;
    }

    public void setDateEdit(Date dateEdit)
    {
        this.dateEdit = dateEdit;
    }

    public Date getDateCreate()
    {

        return dateCreate;
    }

    public void setDateCreate(Date dateCreate)
    {
        this.dateCreate = dateCreate;
    }

    public String getName()
    {

        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {

        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
