/**
 * Created by PK on 4/19/2015.
 */

$( document ).ready(function() {
    $("button#add").click(function(){
        var x = prompt("Prosze wpisac nazwe nowego koszyka: ");
        if(x == null) {
            return;
        } else if(x == ''){
            alert("Wpisana pusta wartosc, koszyk nie dodany");
        }
        var form = document.createElement("form");
        form.setAttribute("method", "POST");
        form.setAttribute("action", "baskets");

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "nameBasket");
        hiddenField.setAttribute("value", x);

        form.appendChild(hiddenField);

        document.body.appendChild(form);
        form.submit();
    });
});

function deleteBasket(id){
    if(!confirm("Czy jestes pewen, ze chcesz usunac ten koszyk?"))
        return
    var form = document.createElement("form");
    form.setAttribute("method", "POST");
    form.setAttribute("action", "deleteBasket");

    var hiddenField = document.createElement("input");
    hiddenField.setAttribute("type", "hidden");
    hiddenField.setAttribute("name", "idBasket");
    hiddenField.setAttribute("value", id);

    form.appendChild(hiddenField);

    document.body.appendChild(form);
    form.submit();
}
