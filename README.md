# zoran.io

[![Build Status](https://travis-ci.com/msadowskigraduate/zoran.io.svg?branch=develop)](https://travis-ci.com/msadowskigraduate/zoran.io)
[![Maintainability](https://api.codeclimate.com/v1/badges/40bbf2ad62babed8ada3/maintainability)](https://codeclimate.com/github/msadowskigraduate/zoran.io/maintainability)

## Contents 
- ### Basics 
    - #### What is zoran.io?
    - #### Technology 
- ### Usage
- ### Installation 
    - #### Local 
        - Backend 
        - Frontend 
- ### Roadmap
- ### Changelog 
- ### Contributing 

## Basics 

### What is zoran.io?
zoran.io is a dissertation project aiming at improving and speeding up development for fresh projects. It uses code generator concepts to instantly create project stubs and then integrates code from user-generated templates to automatically create boilerplate elements in project.

### Technology
zoran.io is a Java application based on Spring Boot 2.1 with front end application written in AngularDart with Dart 2.1. 

For template filestore, zoran.io is using GitHub leveraging it's versioning potential, versatile API and simplicity for users.

![Solution Architecture](https://github.com/msadowskigraduate/zoran.io/blob/develop/docs/architecture.png?raw=true)

## Usage 
Application is meant to be consumed as a webservice, however it may also be deployed and used locally. For local deployment follow Installation guide.

## Installation

### Local

First of all, clone this repository:

`$ git clone https://github.com/msadowskigraduate/zoran.io.git`

- #### Backend
As the application is being developed using Spring Boot, local deployment is quite easy. 

Generate OAuth credentials in your GitHub [console](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/). 

In `zoran.io/zoran-core/src/main/resources/application-github.yml` paste your Client's id and secret.

Download and install MongoDB according to the [guide](https://docs.mongodb.com/manual/installation/).

Navigate to `zoran.io` folder and run the application with following commands:

`$ cd ./zoran-core`

`$ mvn spring-boot:run`

The application will run on port 8080. Verify application is running correctly by visiting Swagger [page](http://localhost:8080).

- #### Frontend 
First of all, make sure you have Dart SDK installed. If not, visit the following [page](https://webdev.dartlang.org/tools/sdk#install).

Navigate to folder that contains UI:

`$ cd ./zoran-ui`

Get dependencies!

`$ pub get`

Run!

`$ webdev serve web:8082` OR `$ pub run build_runner serve`

The application will start on port 8082.

## Roadmap

TBD

## Changelog

First changelog will be release with first deployment on dev environment.

## Contribution

TBD
