package com.springapp.mvc.grains;

import java.io.Serializable;

/**
 * Created by PK on 4/3/2015.
 */
public class Record implements Serializable
{
    private int id;
    private boolean isMA = false;
    private String nameStudent;
    private String surnameStudent;
    private String mailStudent;
    private String titlePL;
    private String titleEN;
    private String namePromoter;
    private String surnamePromoter;
    private String abstractPL;
    private String abstractEN;
    private String keyWordsPL;
    private String keyWordsEN;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (id != record.id) return false;
        if (isMA != record.isMA) return false;
        if (abstractEN != null ? !abstractEN.equals(record.abstractEN) : record.abstractEN != null) return false;
        if (abstractPL != null ? !abstractPL.equals(record.abstractPL) : record.abstractPL != null) return false;
        if (keyWordsEN != null ? !keyWordsEN.equals(record.keyWordsEN) : record.keyWordsEN != null) return false;
        if (keyWordsPL != null ? !keyWordsPL.equals(record.keyWordsPL) : record.keyWordsPL != null) return false;
        if (mailStudent != null ? !mailStudent.equals(record.mailStudent) : record.mailStudent != null) return false;
        if (namePromoter != null ? !namePromoter.equals(record.namePromoter) : record.namePromoter != null)
            return false;
        if (nameStudent != null ? !nameStudent.equals(record.nameStudent) : record.nameStudent != null) return false;
        if (surnamePromoter != null ? !surnamePromoter.equals(record.surnamePromoter) : record.surnamePromoter != null)
            return false;
        if (surnameStudent != null ? !surnameStudent.equals(record.surnameStudent) : record.surnameStudent != null)
            return false;
        if (titleEN != null ? !titleEN.equals(record.titleEN) : record.titleEN != null) return false;
        if (titlePL != null ? !titlePL.equals(record.titlePL) : record.titlePL != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + (isMA ? 1 : 0);
        result = 31 * result + (nameStudent != null ? nameStudent.hashCode() : 0);
        result = 31 * result + (surnameStudent != null ? surnameStudent.hashCode() : 0);
        result = 31 * result + (mailStudent != null ? mailStudent.hashCode() : 0);
        result = 31 * result + (titlePL != null ? titlePL.hashCode() : 0);
        result = 31 * result + (titleEN != null ? titleEN.hashCode() : 0);
        result = 31 * result + (namePromoter != null ? namePromoter.hashCode() : 0);
        result = 31 * result + (surnamePromoter != null ? surnamePromoter.hashCode() : 0);
        result = 31 * result + (abstractPL != null ? abstractPL.hashCode() : 0);
        result = 31 * result + (abstractEN != null ? abstractEN.hashCode() : 0);
        result = 31 * result + (keyWordsPL != null ? keyWordsPL.hashCode() : 0);
        result = 31 * result + (keyWordsEN != null ? keyWordsEN.hashCode() : 0);
        return result;
    }

    public String getKeyWordsEN()
    {
        return keyWordsEN;
    }

    public void setKeyWordsEN(String keyWordsEN)
    {
        this.keyWordsEN = keyWordsEN;
    }

    public String getKeyWordsPL()
    {
        return keyWordsPL;
    }

    public void setKeyWordsPL(String keyWordsPL)
    {
        this.keyWordsPL = keyWordsPL;
    }

    public String getAbstractEN()
    {
        return abstractEN;
    }

    public void setAbstractEN(String abstractEN)
    {
        this.abstractEN = abstractEN;
    }

    public String getAbstractPL()
    {
        return abstractPL;
    }

    public void setAbstractPL(String abstractPL)
    {
        this.abstractPL = abstractPL;
    }

    public String getSurnamePromoter()
    {
        return surnamePromoter;
    }

    public void setSurnamePromoter(String surnamePromoter)
    {
        this.surnamePromoter = surnamePromoter;
    }

    public String getNamePromoter()
    {
        return namePromoter;
    }

    public void setNamePromoter(String namePromoter)
    {
        this.namePromoter = namePromoter;
    }

    public String getTitleEN()
    {
        return titleEN;
    }

    public void setTitleEN(String titleEN)
    {
        this.titleEN = titleEN;
    }

    public String getTitlePL()
    {
        return titlePL;
    }

    public void setTitlePL(String titlePL)
    {
        this.titlePL = titlePL;
    }

    public String getMailStudent()
    {
        return mailStudent;
    }

    public void setMailStudent(String mailStudent)
    {
        this.mailStudent = mailStudent;
    }

    public String getSurnameStudent()
    {
        return surnameStudent;
    }

    public void setSurnameStudent(String surnameStudent)
    {
        this.surnameStudent = surnameStudent;
    }

    public String getNameStudent()
    {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent)
    {
        this.nameStudent = nameStudent;
    }

    public boolean isMA()
    {
        return isMA;
    }

    public void setMA(boolean isMA)
    {
        this.isMA = isMA;
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
