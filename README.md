# Know your protein

***Know your protein*** is web application that allows secondary structure analysis of proteins based on their infrared spectras.

*This repository contains server-side part of application. Client-side part of the app is available [here](https://github.com/jakubowski1005/know-your-protein-client).*


## Table of content

- [Description](#description)
- [Technologies, tools and libraries](#technologies-tools-and-libraries)
- [Author](#author)
- [Todo](#todo)

## Description

This project is my engineers diploma project. The application allows user to get proportions of specific secondary protein structures like alpha-helix or beta-sheet. Data are calculated by analysing results of Fourier Transform Infrared Spectroscopy. Received data can be saved in the database or can be downloaded as PDF document.

Data analysis stages:
- cutting out the component band,
- smoothing and filtration,
- numerical differentiation,
- peak detection,
- structures fitting,
- deconvolution,
- numerical integration

Server-side application is responsible for user account handling (authorization and authentication), connecting with database and data processing.


## Technologies, tools and libraries ##

- Gradle 
- Spring Boot 2
- Spring Security with JWT
- Spring Data JPA
- Spock Framework
- Lombok
- Apache Commons
- PostgreSQL

## Author

- Artur Jakubowski

## Todo

- [X] Choose technologies, tools and libraries
- [X] Project application structure
- [X] Create repository and task list
- [X] Initialize project using Gradle and add needed dependencies
- [X] Implement data processing elements and tests
- [X] Implement data models, services and controllers with tests
- [X] Implement auth handling woth Spring Security with JWT
- [X] Refactor code, check if code was write with good practises
- [X] Deploy app on Heroku
