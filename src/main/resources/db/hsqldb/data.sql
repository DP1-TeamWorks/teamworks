/*USERS*/
INSERT INTO
    teams(name, identifier)
values
    ('Cybergroup', 'cyber');

INSERT INTO
    users(name, lastname, email, password, role, team_id)
values
    (
        'Johnny',
        'Silverhand',
        'johnnysilverhand@cyber',
        '$2a$10$0nvRQTbmAF3gLpwOs5Nc4ujS0FYMsQy1DI0eV9x03JDapfItsLw56',
        0,
        1
    );

INSERT INTO
    users(name, lastname, email, password, role, team_id)
values
    (
        'Julia',
        'Fabra',
        'juliafabra@cyber',
        '$2a$10$0nvRQTbmAF3gLpwOs5Nc4ujS0FYMsQy1DI0eV9x03JDapfItsLw56',
        1,
        1
    );

INSERT INTO
    users(name, lastname, email, password, role, team_id)
values
    (
        'Maria',
        'Torres',
        'mariatorres@cyber',
        '$2a$10$0nvRQTbmAF3gLpwOs5Nc4ujS0FYMsQy1DI0eV9x03JDapfItsLw56',
        1,
        1
    );

INSERT INTO
    users(name, lastname, email, password, role, team_id)
values
    (
        'Luis',
        'Cumbrera',
        'luiscumbrera@cyber',
        '$2a$10$0nvRQTbmAF3gLpwOs5Nc4ujS0FYMsQy1DI0eV9x03JDapfItsLw56',
        1,
        1
    );

INSERT INTO
    users(name, lastname, email, password, role, team_id)
values
    (
        'Roman',
        'Calle',
        'romancalle@cyber',
        '$2a$10$0nvRQTbmAF3gLpwOs5Nc4ujS0FYMsQy1DI0eV9x03JDapfItsLw56',
        1,
        1
    );

/*DEPARTMENTS*/
INSERT INTO
    departments(name, description, team_id)
values
    ('Calidad', 'Aseguro la ...', 1);

INSERT INTO
    belongs(is_department_manager, user_id, department_id, initial_date)
values
    (TRUE, 2, 1, '2021-02-03');

INSERT INTO
    belongs(is_department_manager, user_id, department_id, initial_date)
values
    (FALSE, 5, 1, '2021-02-03');

INSERT INTO
    departments(name, description, team_id)
values
    ('Gestion', 'La mejor gestion ...', 1);

INSERT INTO
    belongs(is_department_manager, user_id, department_id, initial_date)
values
    (TRUE, 3, 2, '2021-02-03');

INSERT INTO
    departments(name, description, team_id)
values
    ('Ventas', 'Debemos vender ...', 1);

INSERT INTO
    belongs(is_department_manager, user_id, department_id, initial_date)
values
    (TRUE, 4, 3, '2021-02-03');

INSERT INTO
    belongs(is_department_manager, user_id, department_id, initial_date)
values
    (FALSE, 2, 2, '2021-02-03');

INSERT INTO
    belongs(is_department_manager, user_id, department_id, initial_date)
values
    (FALSE, 2, 3, '2021-02-03');

/*PROJECTS*/
INSERT INTO
    projects(name, description, department_id)
values
    (
        'Networking Solutions',
        'Develop network solutions for the team!',
        1
    );

INSERT INTO
    projects(name, description, department_id)
values
    (
        'Netrunning School',
        'Teach netrunning skills!',
        1
    );

INSERT INTO
    projects(name, description, department_id)
values
    (
        'School Project',
        'Develop an api rest ',
        1
    );

INSERT INTO
    projects(name, description, department_id)
values
    (
        'Homemade Dishes',
        'Web app for a homemade food enterprise',
        1
    );

INSERT INTO
    projects(name, description, department_id)
values
    (
        '2ndStreet Butchers',
        'Web app for the street butchers shop',
        2
    );

INSERT INTO
    projects(name, description, department_id)
values
    (
        ' Hello You ',
        ' Social Network development ',
        2
    );

INSERT INTO
    projects(name, description, department_id)
values
    (
        ' 2ndStreet Bakery ',
        ' Web app for the street bakery ',
        2
    );

INSERT INTO
    participations(is_project_manager, user_id, project_id, initial_date)
values
    (TRUE, 2, 1, '2021-02-03');

INSERT INTO
    participations(is_project_manager, user_id, project_id, initial_date)
values
    (TRUE, 2, 2, '2021-02-03');

INSERT INTO
    participations(is_project_manager, user_id, project_id, initial_date)
values
    (TRUE, 2, 3, '2021-02-03');

INSERT INTO
    participations(is_project_manager, user_id, project_id, initial_date)
values
    (TRUE, 2, 4, '2021-02-03');

/*TAGS*/
INSERT INTO
    tags(title, color, project_id)
values
    ('Planning', '#EFCAC4', 1);

INSERT INTO
    tags(title, color, project_id)
values
    ('Debugging', '#E19494', 1);

INSERT INTO
    tags(title, color, project_id)
values
    ('Testing', '#B0D9CD', 1);

/*MILESTONES*/
INSERT INTO
    milestones(name, due_for, project_id)
values
    (
        'New Year objectives',
        TO_DATE('13/12/2021', 'DD/MM/YYYY'),
        1
    );

/*ToDoS*/
INSERT INTO
    todos(title, done, user_id, milestone_id)
values
    ('Mark this as done', FALSE, 2, 1);

INSERT INTO
    todos(title, done, user_id, milestone_id)
values
    ('Mark this again ', FALSE, 2, 1);

INSERT INTO
    todos(title, done, user_id, milestone_id)
values
    ('Finish The toDos section', FALSE, 2, 1);

/*MESSAGES*/
INSERT INTO
    messages(timestamp, subject, text, read, sender_id)
values
    (
        TO_DATE('18/04/2020', 'DD/MM/YYYY'),
        'Hello World in TeamWorks',
        'LOREM IPSUM DOLOR ET SI JFD KASDL EHRTWE DLFAJSDC LWKER HQLKWJEFHLS KD',
        FALSE,
        1
    );

INSERT INTO
    messages(timestamp, subject, text, read, sender_id)
values
    (
        TO_DATE('16/1/2020', 'DD/MM/YYYY'),
        'Welcome to TeamWorks',
        'LOREM IPSUM DOLOR ET SI JFD KASDL EHRTWE DLFAJSDC LWKER HQLKWJEFHLS KD',
        FALSE,
        1
    );

INSERT INTO
    messages(timestamp, subject, text, read, sender_id)
values
    (
        TO_DATE('14/05/2020', 'DD/MM/YYYY'),
        'Hello World in TeamWorks',
        'LOREM IPSUM DOLOR ET SI JFD KASDL EHRTWE DLFAJSDC LWKER HQLKWJEFHLS KD',
        FALSE,
        4
    );

INSERT INTO
    messages(timestamp, subject, text, read, sender_id)
values
    (
        TO_DATE('14/10/2020', 'DD/MM/YYYY'),
        'Hello World in TeamWorks',
        'LOREM IPSUM DOLOR ET SI JFD KASDL EHRTWE DLFAJSDC LWKER HQLKWJEFHLS KD',
        TRUE,
        3
    );

INSERT INTO
    messages(timestamp, subject, text, read, sender_id)
values
    (
        TO_DATE('14/10/2020', 'DD/MM/YYYY'),
        'Hello World in TeamWorks',
        'LOREM IPSUM DOLOR ET SI JFD KASDL EHRTWE DLFAJSDC LWKER HQLKWJEFHLS KD',
        FALSE,
        2
    );

INSERT INTO
    messages(timestamp, subject, text, read, sender_id)
values
    (
        TO_DATE('14/10/2020', 'DD/MM/YYYY'),
        'Hello World in TeamWorks',
        'LOREM IPSUM DOLOR ET SI JFD KASDL EHRTWE DLFAJSDC LWKER HQLKWJEFHLS KD',
        TRUE,
        2
    );

INSERT INTO
    MESSAGES_RECIPIENTS(message_id, recipients_id)
values
    (1, 2);

INSERT INTO
    MESSAGES_RECIPIENTS(message_id, recipients_id)
values
    (2, 2);

INSERT INTO
    MESSAGES_RECIPIENTS(message_id, recipients_id)
values
    (3, 2);

INSERT INTO
    MESSAGES_RECIPIENTS(message_id, recipients_id)
values
    (4, 2);

INSERT INTO
    MESSAGES_RECIPIENTS(message_id, recipients_id)
values
    (5, 3);

INSERT INTO
    MESSAGES_RECIPIENTS(message_id, recipients_id)
values
    (6, 1);

INSERT INTO
    MESSAGES_TAGS(message_id, tags_id)
values
    (2, 1);

INSERT INTO
    MESSAGES_TAGS(message_id, tags_id)
values
    (2, 3);

INSERT INTO
    MESSAGES_TAGS(message_id, tags_id)
values
    (3, 2);

INSERT INTO
    MESSAGES_TAGS(message_id, tags_id)
values
    (1, 1);
