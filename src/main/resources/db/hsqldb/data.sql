

/*TEAMS*/
INSERT INTO
    teams(name, identifier)
values
    (
    	'Cybergroup', 
    	'cyber'
    );
    
INSERT INTO teams(name , identifier)
values
	(
		'Microsoft', 
		'infor'
	);
	
INSERT INTO teams(name , identifier)
values
	(
		'Inova S.L',
	 	'infor'
	 );
	
INSERT INTO teams(name , identifier)
values
	(
		'Monitor S.L',
		 'infor'
	);

INSERT INTO teams(name , identifier)
values
	(
		'AVERROES S.L',
		'infor'
	);

/*USERS*/
INSERT INTO
    users(name, lastname, email, password, role, team_id)
values
    (
        'Johnny',
        'Silverhand',
        'johnnysilverhand@cyber',
        '123123123',
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
        '123456789',
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
        '123456789',
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
        '123456789',
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
        '123456789',
        1,
        1
    );

/*DEPARTMENTS*/
INSERT INTO
    departments(name, description, team_id)
values
    (
    	'Calidad',
     	'Aseguro la ...', 
     	1
     );

INSERT INTO
    belongs(is_department_manager, user_id, department_id)
values
    (
    	TRUE, 
    	2, 
    	1
    );

INSERT INTO
    belongs(is_department_manager, user_id, department_id)
values
    (
    	FALSE, 
    	5, 
    	1
    );

INSERT INTO
    departments(name, description, team_id)
values
    (
    	'Gestion',
     	'La mejor gestion ...',
     	 1
     );

INSERT INTO
    belongs(is_department_manager, user_id, department_id)
values
    (
    	TRUE, 
    	3, 
    	2
    );

INSERT INTO
    departments(name, description, team_id)
values
    (
    	'Ventas',
    	 'Debemos vender ...',
    	  1
    );

/*BELONGS*/

INSERT INTO
    belongs(is_department_manager, user_id, department_id)
values
    (
    	TRUE,
    	 4,
    	 3
    );

INSERT INTO
    belongs(is_department_manager, user_id, department_id)
values
    (
    	FALSE,
    	 2,
    	 2
    );

INSERT INTO
    belongs(is_department_manager, user_id, department_id)
values
    (
    	FALSE, 
    	2, 
    	3
    );

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
    
/*PARTICIPATION*/

INSERT INTO
    participations(is_project_manager, user_id, project_id)
values
    (
    	TRUE, 
    	2, 
    	1
    );

INSERT INTO
    participations(is_project_manager, user_id, project_id)
values
    (
    	TRUE, 
    	2, 
    	2
    );

INSERT INTO
    participations(is_project_manager, user_id, project_id)
values
    (
    	TRUE, 
    	2, 
    	3
    );

INSERT INTO
    participations(is_project_manager, user_id, project_id)
values
    (TRUE, 2, 4);

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
    todos(title, user_id, milestone_id)
values
    ('Mark this as done', 2, 1);

INSERT INTO
    todos(title, user_id, milestone_id)
values
    ('Mark this again ', 2, 1);

INSERT INTO
    todos(title, user_id, milestone_id)
values
    ('Finish The toDos section', 2, 1);