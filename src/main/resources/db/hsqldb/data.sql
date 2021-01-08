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