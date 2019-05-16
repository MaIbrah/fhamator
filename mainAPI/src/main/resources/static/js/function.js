function addKeyValueInputs(table,onclick) {

    $(onclick)
            .on("click",
                    function () {
                        var newRow = $("<tr>");
                        var cols = "";
                        cols += '<td style="text-align: center"><input name="keys[]"/></td>';
                        cols += '<td style="text-align: center"><input name="values[]"/></td>';
                        cols += `<td style="text-align: center"><input type="button" id="ibtnDel" value="Supprimer" class="btn btn-danger"/></td>`;
                        newRow.append(cols);
                        $(table).append(newRow);

                    });
    $(table).on("click", "#ibtnDel",
            function (event) {
                $(this).closest("tr").remove();
            });
}
function ajaxJSON(data,url,type,redirect){
    $.ajax({
        url : '/'+url+'/',
        type: type,
        data:  JSON.stringify(data),
        dataType: 'json',
        contentType: "application/json",
        complete  : function(response){ // code_html contient le HTML renvoyé
            window.location.href = redirect;
        }
    });
}
function ajax(id,url,type){
    $.ajax({
        url : '/'+url+'/'+id,
        type : type,
        complete : function(response){ // code_html contient le HTML renvoyé
            location.reload();
        }
    });
}