# TeamWorks

A showcase video can be found [here](https://youtu.be/g9Iu-Cj1-e0) (recorded in Spanish).

## Local Deployment
### Backend

- Clone the repository 
- import the maven project
- maven install
- execute the commnad: `./mvnw spring-boot:run` or run "PetclinicApplication"

Navigate to localhost:8080 and login with one of the following emails:
- johnnysilverhand@cyber (password 123123123)
- juliafabra@cyber (password 123123123)
- romancalle@cyber (password 123123123)

### Frontend
If you wish to modify frontend code and see changes on the fly:
- Navigate to `/frontend/teamworks`
- `npm install`
- `npm run start`

## Database configuration

In its default configuration, TEAMWORKS uses an in-memory database (H2) which
gets populated at startup with data. 


### Prerequisites
The following items should be installed in your system:
* Java 8 or newer.
* git command line tool (https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, just follow the install process here: https://www.eclipse.org/m2e/
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * IntelliJ IDEA
  * [VS Code](https://code.visualstudio.com)

## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [PetClinicApplication](https://github.com/gii-is-DP1/spring-petclinic/blob/master/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
|Properties Files | [application.properties](https://github.com/gii-is-DP1/spring-petclinic/blob/master/src/main/resources) |
|Caching | [CacheConfiguration](https://github.com/gii-is-DP1/spring-petclinic/blob/master/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |
