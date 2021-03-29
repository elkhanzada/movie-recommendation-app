# group9

Cases when user input very long string may cause something (i dont know!) in split, get functions.@#$%^&(^%$#@//\/\/\/\/\\/.].].]}>}>}gnlfgjn(*&^%^&*(GHSDFG @#$^&()&^%$#±/\/\/\/

- If the user enters an occupation that is not specified in our users list (not an educator, actor or else), then that occupation should be considered as "other" and have an id 0. However, we added an additional check for the 'other' case which is, if an input is too long (>50) then it is obviously some nonexistent occupation. In this case (nonexistent occupaton), we throw warning message that string is too long and terminate the programm. In any other case, even if the input for occupation has some characters like $, ^, we will consider is as an 'other' section. (We couldn't exlude such cases because example as K-12student showed us that input can contain characters and numbers.


- One more check will be done on the genres input from the user. If amount of genres given by user is too much (> 10), we will print out message that there are no movie with this list of genres. Also, we check each genre string for its length, and if the length is too long (> 50), then again, we output message that there is no movie with such genre.  


