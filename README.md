# Know your protein

***Know your protein*** is web application that allows secondary structure analysis of proteins based on their infrared spectras.

*This repository is responsible for server-side part of application. Client-side part of the app is available [here](https://github.com/jakubowski1005/know-your-protein-client).*


## Table of content

- [Description](#description)
- [Technologies, tools and libraries](#technologies-tools-and-libraries)
- [Author](#author)
- [Todo](#todo)

## Description

This project is my engineer's diploma project. The application would allow user to get content of specific secondary protein structures like alpha-helix or beta-sheet. Data would be calculated from Fourier Transform Infrared Spectroscopy results. Received data could be save in the database or save as PDF document.

The data would multistage processed:
- cut out important bands,
- smoothing and filtration,
- numerical differentiation,
- find peeks,
- fit structures,
- deconvolution,
- numerical integral

Server-side part of application would be responsible for user account handling (authorization and authentication), connecting with database and data processing.


## Technologies, tools and libraries ##

- Gradle 
- Spring Boot 2
- Spring Security, OAuth2 with JWT
- Spring JPA and Hibernate
- Spock Framework
- Lobok
- PostgreSQL

## Author

- Artur Jakubowski

## Todo

- [X] Choose technologies, tools and libraries
- [X] Project application structure
- [X] Create repository and task list
- [X] Initialize project using Gradle and add needed dependencies
- [ ] Implement data processing elements and tests
- [ ] Implement data models, services and controllers with tests
- [ ] Implement auth handling woth Spring Security, OAuth2 with JWT
- [ ] Refactor code, check if code was write with good practises
- [ ] Deploy app on Heroku
