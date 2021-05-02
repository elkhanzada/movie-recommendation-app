# What this repository is about
In this repository, we recommend top 10 movies from given user data and for given genres (if applicable).
# Algorithm
For this milestone, we used [IMDB ranking algorithm](https://www.fxsolver.com/browse/formulas/Bayes+estimator+-+Internet+Movie+Database+%28IMDB%29) to sort the movies which are rated by people with given user data. The minimum number of votes for the movie is chosen to be 10. If we are unable to find people with given user data, we go one level down to ignore one of the inputs. We have following levels. Smaller number means higher priority.
1. Gender, Age, Occupation
2. Gender, Age, <del>Occupation</del>
3. <del>Gender</del>, Age, Occupation
4. Gender, <del>Age</del>, Occupation
5. <del>Gender</del>, Age, <del>Occupation</del>
6. <del>Gender</del>, <del>Age</del>, Occupation
7. Gender, <del>Age</del>, <del>Occupation</del>
8. <del>Gender</del>, <del>Age</del>, <del>Occupation</del>
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
It will print output for the example arguments.
# Usage
Our tool takes can take either 3 or 4 arguments as follows
```bash
$ java -cp target/group9-1.0-SNAPSHOT.jar Main [gender] [age] [occupation]
```
```bash
$ java -cp target/group9-1.0-SNAPSHOT.jar Main [gender] [age] [occupation] [genre]
```
It is allowed to leave gender, age, and occupation as empty but if you opt for 4 arguments, then the genre input must not be empty. 
If you want to give more than one genre, then separate them with "|" delimeter. 
```
$ java -cp target/group9-1.0-SNAPSHOT.jar Main [gender] [age] [occupation] ["genre_1|genre_2"]
```
e.g.:
```bash
$ java -cp target/group9-1.0-SNAPSHOT.jar Main "F" "25" "gradstudent" "action|comedy"
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
1) The number of arguments passed must be exactly 3 or 4. Otherwise, a warning message is outputed and program is terminated.
2) Gender, genre and occupation strings are not case sensitive.
3) Genres must be split by '|' character, otherwise a warning message is outputed and program is terminated.
4) User must include each possible genre maximum once, otherwise a warning message is outputed and program is terminated.
5) If occupation string is not one of specified user occupations (not an educator, student or else) or not equal to "other", then we print the warning message and terminate the program. 
6) All inputs may be left empty except of the genre input. It must contain at least one valid genre and will terminate program with warning message othersise.
Output:
1) If every input requirement is satisfied, then the program will output ten movies with their corresponding [IMDB](https://www.imdb.com/) links.



# Contribution
- Kasymzhan Abdyldayev (20182002) - Error handling
- Elkhan Ismayilzada (20182010) - Algorithm implementation, Testing, Readme
- Aibar Oshakbayev (20182021) - Testing, Readme
