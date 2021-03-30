# What this repository is about
In this repository, we give average rating score of the movies which are rated by people with various occupations.
# Instruction for those who do not use docker
### Requirement
Java 1.11 (Please set ```JAVA_HOME``` before running the code)
### Check out the source code
```bash
$ git clone https://github.com/elkhan199/group9.git [destinationPath]
$ cd [destinationPath]
```
### Build
```bash
$ mvn install
```
# Instruction for docker users
### Building the image and accessing to the container
```Dockerfile``` and the script to access the container is located in ```scripts``` folder
```bash
$ cd scripts/ && sh docker_run.sh [image_name]
```
### Build and run
Once you are in the container, please run ```run.sh``` script as follows
```bash
$ . run.sh
```
It will print output for the example arguments
# Usage
Our tool takes into two arguments as follows
```bash
$ java -cp target/group9-1.0-SNAPSHOT.jar Main [genre] [occupation]
```
If you want to give more than one genre, then separate them with "|" delimeter. 
```
$ java -cp target/group9-1.0-SNAPSHOT.jar Main ["genre_1|genre_2"] [occupation]
```
e.g.:
```bash
$ java -cp target/group9-1.0-SNAPSHOT.jar Main "action|comedy" educator
```
These are the all available occupations we currently have
| Occupation list      |
|----------------------|
| academic/educator    |
| artist               |
| clerical/admin       |
| college/gradstudent  |
| customerservice      |
| doctor/healthcare    |
| executive/managerial |
| farmer               |
| homemaker            |
| K-12student          |
| lawyer               |
| programmer           |
| retired              |
| sales/marketing      |
| scientist            |
| self-employed        |
| technician/engineer  |
| tradesman/craftsman  |
| unemployed           |
| writer               |
| other                |

# Troubleshooting
Cases when user input very long string may cause something (i dont know!) in split, get functions.@#$%^&(^%$#@//\/\/\/\/\\/.].].]}>}>}gnlfgjn(*&^%^&*(GHSDFG @#$^&()&^%$#±/\/\/\/

Input requirements:
1) The number of arguments passed must be exactly 2. Otherwise a warning message is outputed and program is terminated.
2) Both genre and occupation strings are not case sensitive.
3) Genres must be split by '|' character, otherwise a warning message is outputed and program is terminated.
4) If either of occupation or genre string is too long (> 50 chars), we regard it as invalid input since there can't be such occupation or genre. In this case we terminate the program and output corresponding warning message.
5) If occupation string is not one of specified user occupations (not an educator, student or else), then we regard it as 'others' case and we give id 0 to this occupation. The characters in the occupation string can be arbitrary, and the only restriction is in the length of string (not more than 50)
6) If the list of genres requested does not correspond to any movie, we print out warning message and terminate program

Output:
1) If every input requirement is satisfied, then the program will output average score rounded to 2 decimals.


- If the user enters an occupation that is not specified in our users list (not an educator, actor or else), then that occupation should be considered as "other" and have an id 0. However, we added an additional check for the 'other' case which is, if an input is too long (>50) then it is obviously some nonexistent occupation. In this case (nonexistent occupaton), we throw warning message that string is too long and terminate the programm. In any other case, even if the input for occupation has some characters like $, ^, we will consider is as an 'other' section. (We couldn't exlude such cases because example as K-12student showed us that input can contain characters and numbers.


- One more check will be done on the genres input from the user. If amount of genres given by user is too much (> 10), we will print out message that there are no movie with this list of genres. Also, we check each genre string for its length, and if the length is too long (> 50), then again, we output message that there is no movie with such genre.  


