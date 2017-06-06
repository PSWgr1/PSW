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
                	var beginDivX = $('<div id="'+value.name+'X" class="container">');
                	var beginDivY = $('<div id="'+value.name+'Y" class="container">');
                	if(key != 0){
                		beginDivX = $('<div id="'+value.name+'X" class="container">').hide();
                		beginDivY = $('<div id="'+value.name+'Y" class="container">').hide();
                		}
                	
                	var ulDocX=$('<ul>');
                	var ulDocY=$('<ul>');
                	
                	$(value.HEADERS).each(function(k,v){
                		var XAsis = $('<li><input type="radio" id="DataRowXdatafor'+value.name+v+'" name="DataRowXdatafor'+value.name+'" value="'+v+'"></input><label for="DataRowXdatafor'+value.name+v+'">'+v+'</label><div class="check"><div class="inside"></div></div></li>');
                		var YAsis = $('<li><input type="radio" id="DataRowYdatafor'+value.name+v+'" name="DataRowYdatafor'+value.name+'" value="'+v+'"></input><label for="DataRowYdatafor'+value.name+v+'">'+v+'</label><div class="check"><div class="inside"></div></div></li>');
                		$(ulDocX).append(XAsis);
                		$(ulDocY).append(YAsis);
                	});
                	$(beginDivX).append(ulDocX);
                	$(beginDivY).append(ulDocY);
                	
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
