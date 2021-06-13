# What this repository is about
In this repository, we developed a website in which we recommend top 10 movies from given user data and for given genres (if chosen) and suggest top **N** (given by user) movies based on chosen movie.
User data contains attributes such as ```gender```,```age```, and ```occupation```. Each recommended movie will have its name, poster (if available),
genre information and a button that will take you to its IMDB profile.
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
\
MongoDB (Please run mongodb at port number 27017)
### Check out the source code
```bash
$ git clone https://github.com/elkhan199/group9.git [destinationPath]
$ cd [destinationPath]
```
### Build
```bash
$ mvn package
```
### Usage
```bash
$ java -jar target/group9-1.0-SNAPSHOT.jar
```
It will run the server locally and in your browser, go to this link -> http://localhost:8080/ 
# Instruction for docker users
### Building the image and running the server
```Dockerfile``` and the script to run the server in container is located in ```scripts``` folder
```bash
$ cd scripts/ && sh docker_run.sh [image_name]
```
### Usage
If the image is successfully built and run, in your browser go to the following link -> http://localhost:8080/group9
# Troubleshooting

Important notes:
1) Our algorithm is slow. It might take 30-40 seconds to get the movie list.
2) If the chosen movie does not exist, the user will be alarmed


# Contribution
- Kasymzhan Abdyldayev (20182002) - Algorithm implementation (Part II)
- Elkhan Ismayilzada (20182010) - Spring Integration, Error Handling, README
- Aibar Oshakbayev (20182021) - Testing
