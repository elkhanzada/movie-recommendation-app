$(document).ready(function() {
    function makeCards(value){
        if (value.hasOwnProperty("error")) {
            alert(value.error)
        }
        else {
            var main = $("<div>").addClass("card").css("width","18rem")
            var img = $("<img>").attr("src",value.poster).attr("onerror", "this.src = 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/1200px-No-Image-Placeholder.svg.png'").appendTo(main);
            var child = $("<div>").addClass("card-body").appendTo(main)
            var h5 = $("<h5>").addClass("card-title").html(value.title.toUpperCase()).appendTo(child)
            var p = $("<p>").addClass("card-text").html(value.genres.toUpperCase()).appendTo(child)
            var a = $("<a>").attr("href",value.link).attr("target","_blank").attr("class","btn btn-primary").html("Watch me").appendTo(child);
            $("body").append(main)
        }
    }
    function startBuilding(res) {
        $.each(res, function(key,value) {
            makeCards(value)
        });
    }

    function makeLists(value) {
        return $("<li>").html(value).bind("click",function () {
            selectMovie(value)
        })
    }
    function selectMovie(value) {
        $("#title").val(value);
        $("#suggestion-box").hide();
    }
    $('#users').submit(function() { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('method'), // GET or POST
            url: $(this).attr('action'), // the file to call
            beforeSend: function () {
                $(".card").remove();
                $("#loading").show()
            },
            success: function(res) { // on success..
                $("#loading").hide()
                startBuilding(res)
            }
        });
        return false; // cancel original event to prevent form submitting
    });
    $('#movies').submit(function() { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('method'), // GET or POST
            url: $(this).attr('action'), // the file to call
            beforeSend: function () {
                $(".card").remove();
                $("#loading").show()
            },
            success: function(res) { // on success..
                $("#loading").hide()
                startBuilding(res)
            }
        });
        return false; // cancel original event to prevent form submitting
    });
    $("#title").keyup(function(){
        $.ajax({
            type: "POST",
            url: "autocomplete",
            data:'keyword='+$(this).val(),
            beforeSend: function(){
                $("#title").css("background","#FFF url(LoaderIcon.gif) no-repeat 165px");
            },
            success: function(data){
                $("#movie-list").remove()
                $("#suggestion-box").show()
                var ul = $("<ul>").attr("id","movie-list").appendTo("#suggestion-box");
                var i  = 0;
                $.each(data, function(key,value) {
                    var li = makeLists(value.title.toUpperCase());
                    li.appendTo(ul)
                    i+=1
                    return i<10;
                });
                $("#title").css("background","#FFF");
            }
        });
    });
});