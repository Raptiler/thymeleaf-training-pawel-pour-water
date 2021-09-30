### HEROKU
https://pour-water.herokuapp.com/
###

1. Compile and run aplication.
2. Go to http://localhost:8080
3. Create couple or more glasses
4. Use intelligent menu to pour the water from one glass to another
5. Have Fun!

Used frameworks: 
Spring - Configuration [Little developed] and Controller
Thymeleaf - 
* th:each - used to make loops
* th:style - used to dynamic manage style mostly to put water to the glass
* th:action - used to perform GET/POST actions
* th:object - used to manage objects from controller
* th:field - used to manage objects properties
* th:text - used to show text and a static variables from project ex. [th:text="${T(pl.pzprojects.thymeleaftrainingpawel.exceptions.GlassFormExceptionManager).STATUS}]
* th:inline - used to compare javascript with thymeleaf model variables which was helpful to make intelligent range bar which will not allow You to pour more water then it is possible
* th:if/th:unless - used to make if/else statement in thymeleaf
Mockito - Used to make Tests
file: ![Pour-the-water](https://github.com/Raptiler/thymeleaf-training-pawel-pour-water/blob/master/1.png)
