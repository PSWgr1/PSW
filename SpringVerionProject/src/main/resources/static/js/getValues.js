$( document ).ready(function() {
     
    var url = window.location;
     
    // SUBMIT FORM
    $("#CommitFiles").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        ajaxPost();
    });
     
     
    function ajaxPost(){
         
        // PREPARE FORM DATA
        var formData = {
            XAsis : $("#ValuesXplaceholder").val(),
            YAsis : $("#ValuesYplaceholder").val()
        }
         
        // DO POST
        $.ajax({
            type : "POST",
            url : url + "/getChart",
            data : JSON.stringify(formData),
            dataType : 'json',
            processData: false, // Don't process the files
            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
            success: function (data) {
                $(data.data).each(
                function(key,value) {
                		alert(value);
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
        $("#ValuesXplaceholder").val("");
        $("#ValuesYplaceholder").val("");
    }
})
