var objectsMap = [];
$( document ).ready(function() {
     
    var url = window.location;
    
   window.mapOfValues;
     
    // SUBMIT FORM
    $("#GetFilesForm").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });
     
     
    function ajaxPost(){
         
        // PREPARE FORM DATA
        var formData = {
            filepattern : $("#GetFilesFormInput").val()
        }
         
        // DO POST
        $.ajax({
            type : "POST",
            url : url + "/getResult",
            data : JSON.stringify(formData),
            dataType : 'json',
            processData: false, // Don't process the files
            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
            success: function (data) {
                $(data.data).each(
                function(key,value) {
                	objectsMap.push(value);
                	var li = $('<option value="'+value.name+'">'+value.name+'</option>');
                	var beginDivX = $('<div id="'+value.name+'X">');
                	var beginDivY = $('<div id="'+value.name+'Y">');
                	if(key != 0){
                		beginDivX = $('<div id="'+value.name+'X">').hide();
                		beginDivY = $('<div id="'+value.name+'Y">').hide();
                		}
                	
                	$(value.HEADERS).each(function(k,v){
                		var XAsis = $('<input type="radio" name="DataRowXdatafor'+value.name+'" value="'+v+'">'+v+'</input><br/>');
                		var YAsis = $('<input type="radio" name="DataRowYdatafor'+value.name+'" value="'+v+'">'+v+'</input><br/>');
                		$(beginDivX).append(XAsis);
                		$(beginDivY).append(YAsis);
                	});
                	$(beginDivX).append('</div>');
                	$(beginDivX).append('</div>');
                	
                	$('#ValuesXplaceholder').append(beginDivX);
                	$('#ValuesYplaceholder').append(beginDivY);
                	$('#dLabel').append(li);
                       });
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
         
        // Reset FormData after Posting
        resetData();
 
    }
     
    function resetData(){
        $("#GetFilesFormInput").val("");
    }
})
