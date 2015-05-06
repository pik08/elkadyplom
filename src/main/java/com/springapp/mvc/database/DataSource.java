package com.springapp.mvc.database;

import com.springapp.mvc.grains.Basket;
import com.springapp.mvc.grains.Record;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MultiHashtable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by PK on 4/6/2015.
 */
@Service
public class DataSource implements DataSourceImp
{
    ///******PONIZSZE JEST DLA TIN-OW MOZNA TO USUNAC (DLA TESTU, NIE DZIALA ZGODNIE Z WYMAGANIAMI)******///
    private LinkedList<LinkedList<Map<Integer, Record>>> database;

    public DataSource()
    {
        this.database = new LinkedList<LinkedList<Map<Integer, Record>>>();
        int countPromoters = 10;
        int countBaskets = 10;
        int countRecords = 10;
        for(int i = countPromoters - 1; i >= 0; i-- )
        {
            LinkedList<Map<Integer, Record>> baskets = new LinkedList<Map<Integer, Record>>();
            for (int j = countBaskets - 1; j >= 0; j--)
            {
                Map<Integer, Record> records = new HashMap<Integer, Record>();
                for(int k = countRecords - 1; k >= 0; k--)
                {
                    Record tmp = new Record();
                    tmp.setId(k); //started 0
                    tmp.setMA(false);
                    tmp.setNameStudent("TestName-" + i + "-" + j + "-" + k);
                    tmp.setMailStudent("test");
                    tmp.setSurnamePromoter("TestName " + k);
                    tmp.setNamePromoter("test");
                    tmp.setSurnameStudent("Test Name " + k);
                    tmp.setAbstractEN("");
                    tmp.setAbstractPL("");
                    tmp.setTitleEN("test");
                    tmp.setTitlePL("test");
                    tmp.setKeyWordsEN("test");
                    tmp.setKeyWordsPL("test");

                    records.put(k, tmp);
                }
                baskets.push(records);
            }
            database.push(baskets);
        }
    }

    public LinkedList<Basket> getBaskets(int idPromoter)
    {
        LinkedList<Basket> baskets = new LinkedList<Basket>();
        int n = 0;
        for(Map<Integer, Record> tmp: database.get(idPromoter))
        {
            Basket basket = new Basket();
            basket.setId(n);
            basket.setContain(tmp.size());
            basket.setName("Name-" + n);
            basket.setDateCreate(new Date());
            basket.setDateEdit(new Date());
            n++;
            baskets.push(basket);
        }
        return baskets;
    }
    public void addBasket(int idPromoter, String name)
    {
        Map<Integer, Record> baskets = new HashMap<Integer, Record>();
        database.get(idPromoter).push(baskets);
    }
    public void deleteBasket(int idPromoter, int idBasket)
    {
        database.get(idPromoter).remove(idBasket);
    }

    public Record getRecord(int idPromoter, int idBasket, int idRecord)
    {
        return database.get(idPromoter).get(idBasket).get(idRecord);
    }

    public Map<Integer, Record> getRecordsByBasket(int idPromoter, int idBasket)
    {
        return database.get(idPromoter).get(idBasket);
    }

    public boolean saveRecord(int idPromoter, int idBasket, int idRecord, Record record)
    {
        database.get(idPromoter).get(idBasket).put(idRecord, record);
        return true;
    }

    public boolean saveRecordsByBasket(int idPromoter, int idBasket, Map<Integer, Record> records)
    {
        database.get(idPromoter).set(idBasket, records);
        return true;
    }

    public Integer addNewRecord(int idPromoter, int idBasket, Record record)
    {
        //TODO
        int key = database.get(idPromoter).get(idBasket).size();
        Random rand = new Random();
        int randomNum = rand.nextInt((100000 - 10) + 1) + 10;
        key += (key % 3 + 1)* randomNum;
        record.setId(key);
        database.get(idPromoter).get(idBasket).put(key, record);
        return key;
    }

    public boolean deleteRecord(int idPromoter, int idBasket, int idRecord)
    {
        database.get(idPromoter).get(idBasket).remove(idRecord);
        return true;
    }
}
