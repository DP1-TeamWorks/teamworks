INSERT INTO
    teams(name, identifier)
values
    ('Cybergroup', 'cyber')
    /*USERS*/
INSERT INTO
    users(
        name,
        lastname,
        email,
        password,
        role,
        teamId
    )
values
    (
        "Johnny",
        "Silverhand",
        "johnnysilverhand@cyber",
        "123123123",
        0,
        1
    )
    /*PROJECTS*/
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "Networking Solutions",
        "Develop network solutions for the team!",
        1
    )
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "Netrunning School",
        "Teach netrunning skills!",
        1
    )
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "School Project",
        "Develop an api rest ",
        1
    )
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "Homemade Dishes",
        "Web app for a homemade food enterprise",
        1
    )
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "2ndStreet Butcher's",
        "Web app for the street butcher's shop",
        2
    )
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "Hello You",
        "Social Network development",
        2
    )
INSERT INTO
    projects(
        name,
        description,
        departmentId
    )
values
    (
        "2ndStreet Bakery",
        "Web app for the street bakery",
        2
    )
INSERT INTO users(name,lastname,email,password,role,team_id)values("Julia", "Fabra", "juliafabra@cyber","123456789",1)
INSERT INTO users(name,lastname,email,password,role,team_id)values("Maria", "Torres", "mariatorres@cyber","123456789",1)
INSERT INTO users(name,lastname,email,password,role,team_id)values("Luis", "Cumbrera", "luiscumbrera@cyber","123456789",1)
INSERT INTO users(name,lastname,email,password,role,team_id)values("Roman", "Calle", "romancalle@cyber","123456789",1)
INSERT INTO departments(name,description,teamId) values('Calidad','Aseguro la ...',1)
INSERT INTO belongs(isDepartmentManager,userId,departmentId) values(true,1,2)
INSERT INTO belongs(isDepartmentManager,userId,departmentId) values(false,1,5)
INSERT INTO departments(name,description,teamId) values('Gestion','La mejor gestion ...',1)
INSERT INTO belongs(isDepartmentManager,userId,departmentId) values(true,2,3)
INSERT INTO departments(name,description,teamId) values('Ventas','Debemos vender ...',1)
INSERT INTO belongs(isDepartmentManager,userId,departmentId) values(true,3,4)

