$(document).ready(function() {
    $('#users').submit(function() { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('method'), // GET or POST
            url: $(this).attr('action'), // the file to call
            success: function(res) { // on success..
                $(".card").remove();
                $.each(res, function(key,value) {
                    var main = $("<div>").addClass("card").css("width","18rem")
                    var img = $("<img>").attr("src",value.poster).appendTo(main);
                    var child = $("<div>").addClass("card-body").appendTo(main)
                    var h5 = $("<h5>").addClass("card-title").html(value.title).appendTo(child)
                    var p = $("<p>").addClass("card-text").html(value.genres).appendTo(child)
                    var a = $("<a>").attr("href",value.link).attr("target","_blank").attr("class","btn btn-primary").html("Watch me").appendTo(child);
                    $("body").append(main)
                });
            }
        });
        return false; // cancel original event to prevent form submitting
    });
    $('#movies').submit(function() { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('method'), // GET or POST
            url: $(this).attr('action'), // the file to call
            success: function(res) { // on success..
                $(".card").remove();
                $.each(res, function(key,value) {
                    var main = $("<div>").addClass("card").css("width","18rem")
                    var img = $("<img>").attr("src",value.poster).appendTo(main);
                    var child = $("<div>").addClass("card-body").appendTo(main)
                    var h5 = $("<h5>").addClass("card-title").html(value.title).appendTo(child)
                    var p = $("<p>").addClass("card-text").html(value.genres).appendTo(child)
                    var a = $("<a>").attr("href",value.link).attr("target","_blank").attr("class","btn btn-primary").html("Watch me").appendTo(child);
                    $("body").append(main)
                });
            }
        });
        return false; // cancel original event to prevent form submitting
    });
});