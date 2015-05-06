package com.springapp.mvc.database;

import com.springapp.mvc.grains.RecordWywalicExampleForTestsTin;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * Created by PK on 4/8/2015.
 * Klasa do wywalenia, nie jest potrzebna dla PIK-OW, jest przykladem dla kontrolera przykladow z tonow
 */
@Service
public class DataSourceTinWywalicOnlyExampleForTests
{
    ///******PONIZSZE JEST DLA TIN-OW MOZNA TO USUNAC (DLA TESTU, NIE DZIALA ZGODNIE Z WYMAGANIAMI)******///
    private LinkedList<RecordWywalicExampleForTestsTin> records; //only for test

    public DataSourceTinWywalicOnlyExampleForTests()
    {
        this.records = new LinkedList<RecordWywalicExampleForTestsTin>();
        int n = 10;
        for(int i = 0; i < n; i++)
        {   //test
            RecordWywalicExampleForTestsTin tmp = new RecordWywalicExampleForTestsTin();
            tmp.setId(n - i -1); //started 0
            tmp.setMA(false);
            tmp.setNameStudent("TestName " + i);
            tmp.setMailStudent("test");
            tmp.setSurnamePromoter("TestName " + i);
            tmp.setNamePromoter("test");
            tmp.setSurnameStudent("Test Name " + i);
            tmp.setAbstractEN("");
            tmp.setAbstractPL("");
            tmp.setTitleEN("test");
            tmp.setTitlePL("test");
            tmp.setKeyWordsEN("test");
            tmp.setKeyWordsPL("test");

            records.push(tmp);
        }
    }

    public RecordWywalicExampleForTestsTin getRecord(int idPromoter, int idBasket, int idRecord)
    {
        return records.get(idRecord);
    }

    public LinkedList<RecordWywalicExampleForTestsTin> getRecordsByBasket(int idPromoter, int idBasket)
    {
        return records;
    }

    public boolean saveRecord(int idPromoter, int idBasket, int idRecord, RecordWywalicExampleForTestsTin record)
    {
        records.set(idRecord, record);
        return true;
    }

    public boolean saveRecordsByBasket(int idPromoter, int idBasket, LinkedList<RecordWywalicExampleForTestsTin> records)
    {
        this.records = records;
        return true;
    }

    public boolean deleteRecord(int idPromoter, int idBasket, int idRecord)
    {
        records.remove(idRecord);
        return true;
    }
}
