var records = [];

$( document ).ready(function() {
    $.ajax({
        url: "getRecordsJson",
        type: "POST",
        data: { 'idBasket': '' + $("body").attr("id")},
        //contentType: 'application/json',
        success: function(recordsf){
            for(var el in recordsf) {
                var tmp = recordsf[el];
                records.push(makeRecord(tmp));
            }
            refreshAll();
        },
        error: function(request, status, error){
            $("body").html(request.responseText);
        }
    });
});

var idFields = new Array();
    var nameStudent = "nameStudent";
    var surnameStudent = "surnameStudent";
    var mailStudent = "mailStudent";
    var titlePL = "titlePL";
    var titleEN = "titleEN";
    var namePromoter = "namePromoter";
    var surnamePromoter = "surnamePromoter";
    var abstractPL = "abstractPL";
    var abstractEN = "abstractEN";
    var keyWordsPL = "keyWordsPL";
    var keyWordsEN = "keyWordsEN";
    idFields[nameStudent] = "nameStudent";
    idFields[surnameStudent] = "surnameStudent";
    idFields[mailStudent] = "mailStudent";
    idFields[titlePL] = "titleEssayPL";
    idFields[titleEN] = "titleEssayEN";
    idFields[namePromoter] = "namePromotor";
    idFields[surnamePromoter] = "surnamePromotor";
    //idFields[abstractPL] = "abstractPL";
    //idFields[abstractEN] = "abstractEN";
    idFields[keyWordsPL] = "keyWordsPL";
    idFields[keyWordsEN] = "keyWordsEN";

function makeRecord(tmp)
{
    var rec = creatRecord(tmp.id);
    rec.setName(tmp.nameStudent);
    rec.setSurname(tmp.surnameStudent);
    rec.setOriginValue(nameStudent, tmp.nameStudent);
    rec.setOriginValue(surnameStudent, tmp.surnameStudent);
    rec.setOriginValue(mailStudent, tmp.mailStudent);
    rec.setOriginValue(titlePL, tmp.titlePL);
    rec.setOriginValue(titleEN, tmp.titleEN);
    rec.setOriginValue(namePromoter, tmp.namePromoter);
    rec.setOriginValue(surnamePromoter, tmp.surnamePromoter);
    //rec.setter(abstractPL, tmp.abstractPL);
    //rec.setter(abstractEN, tmp.abstractEN);
    rec.setOriginValue(keyWordsPL, tmp.keyWordsPL);
    rec.setOriginValue(keyWordsEN, tmp.keyWordsEN);
    return rec;
}

function creatRecord(id)
{
    if (typeof id == 'undefined') {
        alert("Error: record bez id");
        return;
    }
    var record = {
        changed: true //is saved on server?
    };
    record.origin = [];

    record.record = $("div#contentPanel.panel.panel-default").clone(true);
    record.linkNavPanel = $("a#linkToStudent").clone(true);

    record.setId = function(id){
        record.id = id;
        record.record.attr("id", "record-" + id);
        record.linkNavPanel.attr("id", "arecord-" + id);
        record.linkNavPanel.attr("href", "#record-" + id);
    }
    record.getId = function(){
        return record.record.attr("id").split("-", 2)[1];
    }
    record.setName = function(name){
        record.record.find("input#nameStudent").val(name);
        record.linkNavPanel.find("span#nameStudent").html(name);
    }
    record.setSurname = function(surname){
        record.record.find("input#surnameStudent").val(surname);
        record.linkNavPanel.find("span#surnameStudent").html(surname);
    }
    record.getName = function(){
        return record.record.find("input#nameStudent").val();
    }
    record.getSurname = function() {
        return record.record.find("input#surnameStudent").val();
    }
    record.setOriginValue = function(idInput, val){
        record.origin["" + idFields[idInput]] = val;
        record.setter(idInput, val);
    }
    record.getOriginValue = function(idInput){
        return record.origin[idFields[idInput]];
    }
    record.setter = function(idInput, val){
        record.record.find("input#" + idFields[idInput]).val(val);
    }
    record.getter = function(idInput, val){
        return record.record.find("input#" + idFields[idInput]).val();
    }

    record.isOkTotalCheck = function(){
        var isCorrect = true;
        var isChanged = false;
        for(var el in idFields){
            var val = record.record.find("input#" + idFields[el]).val();
            record.record.find("input#" + idFields[el]).removeClass("hasntClicked");
            if(val.trim() == ''){ //filed is empty, error
                record.record.find("input#" + idFields[el]).removeClass("has-change");
                record.record.find("input#" + idFields[el]).addClass("has-error");
                isCorrect = false;

            } else if(val != record.origin[idFields[el]]) { //has changed
                record.record.find("input#" + idFields[el]).removeClass("has-error");
                record.record.find("input#" + idFields[el]).addClass("has-change");
                isChanged = true;
            } else {
                record.record.find("input#" + idFields[el]).removeClass("has-change");
                record.record.find("input#" + idFields[el]).removeClass("has-error");
            }
        }
        if(!isCorrect){
            record.linkNavPanel.removeClass("has-change");
            record.linkNavPanel.addClass("has-error");
        } else if(isChanged){
            record.linkNavPanel.removeClass("has-error");
            record.linkNavPanel.addClass("has-change");
        } else {
            record.linkNavPanel.removeClass("has-change");
            record.linkNavPanel.removeClass("has-error");
        }
        return isCorrect;
    }

    record.check = function(){
        var isCorrect = true;
        var isChanged = false;
        for(var el in idFields){
            if(record.record.find("input#" + idFields[el]).hasClass("hasntClicked"))
                continue;
            var val = record.record.find("input#" + idFields[el]).val();
            if(val.trim() == ''){ //filed is empty, error
                record.record.find("input#" + idFields[el]).removeClass("has-change");
                record.record.find("input#" + idFields[el]).addClass("has-error");
                isCorrect = false;
            } else if(val != record.origin[idFields[el]]) { //has changed
                record.record.find("input#" + idFields[el]).removeClass("has-error");
                record.record.find("input#" + idFields[el]).addClass("has-change");
                isChanged = true;
            } else {
                record.record.find("input#" + idFields[el]).removeClass("has-change");
                record.record.find("input#" + idFields[el]).removeClass("has-error");
            }
        }
        if(!isCorrect){
            record.linkNavPanel.removeClass("has-change");
            record.linkNavPanel.addClass("has-error");
        } else if(isChanged){
            record.linkNavPanel.removeClass("has-error");
            record.linkNavPanel.addClass("has-change");
        } else {
            record.linkNavPanel.removeClass("has-change");
            record.linkNavPanel.removeClass("has-error");
        }
    }
    //record.saveOnServer = function(){
    //    var isOk = record.isOkTotalCheck();
    //    if(!isOk){
    //        alert("Masz bledy");
    //        return;
    //    }
    //    alert("saved");
    //    //TODO save
    //}
    record.saveOnServer = function(){
        var rec = {
            id: record.getId(),
        }
        rec["" + nameStudent] = record.getter(nameStudent);
        rec["" + surnameStudent] = record.getter(surnameStudent);
        rec["" + mailStudent] = record.getter(mailStudent);
        rec["" + titlePL] = record.getter(titlePL);
        rec["" + titleEN] = record.getter(titleEN);
        rec["" + namePromoter] = record.getter(namePromoter);
        rec["" + surnamePromoter] = record.getter(surnamePromoter);
        rec["" + abstractPL] = record.getter(surnamePromoter);
        rec["" + abstractEN] = record.getter(surnamePromoter);
        //rec.setter(abstractPL, tmp.abstractPL); //TODO
        //rec.setter(abstractEN, tmp.abstractEN); /TODO
        rec["" + keyWordsPL] = record.getter(keyWordsPL);
        rec["" + keyWordsEN] = record.getter(keyWordsEN);
        $.ajax({
            url: "saveRecord?idBasket=" + $("body").attr("id"),
            type: "POST",
            contentType : 'application/json; charset=utf-8',
            async: false,
            data: JSON.stringify(rec),
            //contentType: 'application/json',
            success: function(data){
                if(data + "" !== "true")
                {
                    alert("Error: dane nie sa zapisane na serwerze");
                    return;
                }
                record.linkNavPanel.removeClass("has-change");

                record.setOriginValue(nameStudent, record.getter(nameStudent));
                record.setOriginValue(surnameStudent, record.getter(surnameStudent));
                record.setOriginValue(mailStudent, record.getter(mailStudent));
                record.setOriginValue(titlePL, record.getter(titlePL));
                record.setOriginValue(titleEN, record.getter(titleEN));
                record.setOriginValue(namePromoter, record.getter(namePromoter));
                record.setOriginValue(surnamePromoter, record.getter(surnamePromoter));
                //record.setter(abstractPL, tmp.abstractPL);
                //record.setter(abstractEN, tmp.abstractEN);
                record.setOriginValue(keyWordsPL, record.getter(keyWordsPL));
                record.setOriginValue(keyWordsEN, record.getter(keyWordsEN));

                record.check();
            },
            error: function(request, status, error){
                $("body").html(request.responseText);
            }
        });
    }
    record.delete = function(){
        if(!confirm("Czy jestes pewen, ze chcesz usunac ten record: " + record.getName() + " " + record.getSurname() + "?"))
            return;
        var form = document.createElement("form");
        form.setAttribute("method", "POST");

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "idBasket");
        hiddenField.setAttribute("value", $("body").attr("id"));
        form.appendChild(hiddenField);
        hiddenField = document.createElement("input");
        hiddenField.setAttribute("name", "idRecord");
        hiddenField.setAttribute("value", record.getId());
        form.appendChild(hiddenField);
        var tmp = $(form).serialize();
        $.ajax({
            type: "POST",
            url: "deleteRecord",
            data: $(form).serialize(), // serializes the form's elements.
            success: function(data)
            {
                if(data + "" == "true")
                {
                    record.record.remove();
                    record.linkNavPanel.remove();
                } else {
                    alert("Serwer odmowil w usuwaniu rekordu");
                }
            },
            error: function(request, status, error){
                alert("Error: rekord nie usuniety\n" + request.responseText);
            }
        });
    }

    init = function(){
        record.setId(id);
        record.record.focusout(function(){
            record.check();
        });
        for(var el in idFields){
            record.record.find("input#" + idFields[el]).addClass("hasntClicked");
            record.record.find("input#" + idFields[el]).focus(function(){
                $(this).removeClass("hasntClicked");
            });
        }
        record.record.find("input#" + idFields[nameStudent]).focusout(function(){
            record.setName($(this).val());
        });
        record.record.find("input#" + idFields[surnameStudent]).focusout(function(){
            record.setSurname($(this).val());
        });
        record.record.find("button#save").click(function(){
            record.saveOnServer();
        });
        record.linkNavPanel.find("span#save").click(function(){
            record.saveOnServer();
        });
        record.record.find("button#detele").click(function(){
            record.delete();
        });
        record.linkNavPanel.find("span#delete").click(function(){
            record.delete();
        });
    }
    init();
    return record;
}

function addNewRecord()
{
    var id = null;
    $.ajax({
        url: "addRecord",
        type: "POST",
        async: false,
        data: { 'idBasket': '' + $("body").attr("id")},
        //contentType: 'application/json',
        success: function(recordsf){
            id = recordsf;
        },
        error: function(request, status, error){
            $("body").html(request.responseText);
        }
    });
    var obj = getRecordByIdServer(id);
    records.push(obj);
    var navlink = $("div#navMenu div#navbar div.list-group");
    var divForms = $("div#studentsForm");
    navlink.append(obj.linkNavPanel);
    divForms.append(obj.record);
}

function getRecordByIdServer(id){
    var rec = null;
    $.ajax({
        url: "getRecord",
        type: "POST",
        async: false,
        data: {
                'idBasket': '' + $("body").attr("id"),
                'idRecord': '' + id
        },
        //contentType: 'application/json',
        success: function(recordsf){
            rec = makeRecord(recordsf);
        },
        error: function(request, status, error){
            $("body").html(request.responseText);
        }
    });
    return rec;
}

function refreshAll()
{
    var divForms = $("div#studentsForm");
    var navlink = $("div#navMenu div#navbar div.list-group");
    for(var el in records){
        var obj = records[el];
        navlink.append(obj.linkNavPanel);
        divForms.append(obj.record);
        obj.isOkTotalCheck();
    }
}

function saveAllRecords(){
    for(var el in records){
        records[el].saveOnServer();
    }
}

function generateXML(){
    if(!confirm("Wszystkie zmiany zostana zapisane automatycznie, czy chcesz kontynuowac?"))
        return;
    saveAllRecords();
    for(var el in records){
        if(!records[el].isOkTotalCheck()){
            alert("Masz bledy w formularzu, plik nie zostal wygenerowany, prosze poprawic bledy");
            return;
        }
    }
    var link = document.createElement("a");
    link.download = "data:text/html,HelloWorld!";
    link.href = "helloWorld.txt";
    link.click();
}