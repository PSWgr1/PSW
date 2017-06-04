$( document ).ready(function() {
     
    var url = window.location;
     
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
                	var names = value.split("\\");
                	var li = $('<li><input type="checkbox" name="' + names[names.length-1] + '" id="checkedFiles"/>' + '<label for="' + names[names.length-1] + '"></label></li>');
                	li.find('label').text(names[names.length-1]);
                $('#filesList').append(li);
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
