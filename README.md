# What this repository is about
In this repository, we give average rating score of the movies which are rated by people with various occupations.
# Instruction for those who do not use docker
### Requirement
Java 1.11 (Please set ```JAVA_HOME``` before running the code)
\
Maven
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
It will print output for the example arguments (Adventure educator)
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

Input requirements:
1) The number of arguments passed must be exactly 2. Otherwise a warning message is outputed and program is terminated.
2) Both genre and occupation strings are not case sensitive.
3) Genres must be split by '|' character, otherwise a warning message is outputed and program is terminated.
4) If user inputs one genre multiple times, then we will regard it as one. For example, we will regard "action|action" to be same as "action"
5) If either of occupation or genre string is too long (> 50 chars), we regard it as invalid input since there can't be such occupation or genre. In this case we terminate the program and output corresponding warning message.
6) If occupation string is not one of specified user occupations (not an educator, student or else), then we regard it as 'others' case and we give id 0 to this occupation. The characters in the occupation string can be arbitrary, and the only restriction is in the length of string (not more than 50)
7) If the list of genres requested does not correspond to any movie, we print out warning message and terminate program

Output:
1) If every input requirement is satisfied, then the program will output average score rounded to 2 decimals.
