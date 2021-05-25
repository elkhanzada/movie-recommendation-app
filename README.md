# What this repository is about
In this repository, we recommend top 10 movies from given user data and for given genres (if applicable) and suggest top **N** (given by user) movies based on chosen movie.
# Algorithm
The implementation of our algorithm is located in [Utils.java](src/main/java/com/utils/Utils.java)


For this milestone, here is what we did for first part. We first got the movies based on genres (if given) as well as the users based on given user data. After grouping them, we used [IMDB ranking formula](https://www.fxsolver.com/browse/formulas/Bayes+estimator+-+Internet+Movie+Database+%28IMDB%29) to sort the movies which are rated by people with given user data. The formula is as follows

<img src="https://render.githubusercontent.com/render/math?math=WR%20=\frac{(v*R%20%2B%20m*C)}{(v%2Bm)}" width="150" height="150">

Where ```v``` is the number of votes for the movie, ```m``` is minimum votes required to be listed in the Top 10, ```R``` is the average for the movie as a decimal number from 0 to 5 and ```C``` is the average rating of all movies grouped by genre and user data. The minimum number of votes for the movie is chosen to be 10 and the minimum weighted rating (```WR```) is 3.0. If we are unable to find people with given user data or the number of movies are less than 10, we firstly print them, and we go one level down to ignore one of the inputs to make it 10. We have following levels. Smaller number means higher priority.
1. Gender, Age, Occupation
2. Gender, Age, <del>Occupation</del>
3. <del>Gender</del>, Age, Occupation
4. Gender, <del>Age</del>, Occupation
5. <del>Gender</del>, Age, <del>Occupation</del>
6. <del>Gender</del>, <del>Age</del>, Occupation
7. Gender, <del>Age</del>, <del>Occupation</del>
8. <del>Gender</del>, <del>Age</del>, <del>Occupation</del>

For the second part, we have two levels. Smaller number means higher priority.
1. Movies that have at least one common genre with the chosen movie
2. Other movies

We get the genres of chosen movie, take the movies based on the genres, 
and use the same algorithm to recommend **N** movies 
decided by the user. If we do not have enough movies 
for given genres,then we print the other movies (second priority) based on the same algorithm  to fulfill the number **N** after printing the first priority movies. 
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
$ mvn package
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
We integrated Spring. First, you need to run the server with following command.
```bash
$ java -jar target/group9-1.0-SNAPSHOT.jar
```
If you want to get movies based on user data and genres, run this command.
```bash
$ curl -X GET http://localhost:8080/users/recommendations -H 'Content-type:application/json' -d '{"gender" : "[gender]", "age" :"[age]", "occupation" : "[occupation]", "genre" : "[genre_1|genre_2]"}'
```
e.g.:
```bash
$ curl -X GET http://localhost:8080/users/recommendations -H 'Content-type:application/json' -d '{"gender" : "F", "age" :"25", "occupation" : "gradstudent", "genre" : "Action|War"}'
```
If you want to get movies based on the movie title, run this command.
```bash
$ curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title" : "[movie_title]", "limit":[limit]}'
```
e.g.:
```bash
$ curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title" : "Toy Story (1995)", "limit": 20}'
```
It is allowed to leave the values of ```gender```, ```age```, ```occupation```, ```genre``` keys as empty. Passing ```limit``` key (default is 10) is optional.

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
1) All the keys in the first part and title in the second part are required. Otherwise, an appropriate error message will be given.
2) Gender, genre, occupation, title strings are not case sensitive.
3) Genres must be split by '|' character, otherwise an appropriate error message will be given.
4) User must include each possible genre maximum once, otherwise an appropriate error message will be given.
5) If occupation string is not one of specified user occupations (not an educator, student or else) or not equal to "other", then appropriate error message will be given. 
6) All the values of key may be left empty except of the title key. It must contain at least one movie. Otherwise, an appropriate error message will be given.
7) If you pass json string in wrong format, then an appropriate error message will be given.
8) If chosen movie title is not found in our dataset, then an appropriate error message will be given.
9) For the first part,  you must pass exactly 4 keys and for the second part you must pass either 1 or 2 keys. Otherwise, an appropriate error message will be given.

Output:
1) If every input requirement is satisfied, then the program will return json array that contains movies with their titles, genres, corresponding [IMDB](https://www.imdb.com/) links.



# Contribution
- Kasymzhan Abdyldayev (20182002) - Algorithm implementation (Part II)
- Elkhan Ismayilzada (20182010) - Spring Integration, Error Handling, README
- Aibar Oshakbayev (20182021) - Testing