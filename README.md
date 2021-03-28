# group9

Cases when user input very long string may cause something (i dont know!) in split, get functions.
If the user inputs an occupation that is not specified in our users list (not an educator, actor or else), then that occupation should be considered as "other" and have an id 0. However, we added an additional check for the 'other' case which is, if an input is too long (>50) then it is obviously some nonexistent occupation. In this case (nonexistent occupaton), we throw warning that string is too long. In any other case, even if input for occupation has some characters like $, ^, we will consider is as 'other' section. (We couldn't exlude such cases because example as K-12student showed us that input can contain characters and numbers.
Ask Kasym about FileReader.

