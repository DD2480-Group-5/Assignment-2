# Assignment-2 - CI Server
This repository contains the source code, unit tests and documentation for a continious integration server.

## Prerequisites
The application uses gradle as build tool and is written in Java SE 16.
## Test, build and run
To test the application, use the following command
```sh
$ gradle test
```
To build the application run, use the following command
```sh
$ gradle build
```
To run the application run, use the following command
```sh
$ gradle run
```
The server can now be visited at http://localhost:8080/.
The server accepts a POST request from a GitHub webhook to start a CI pipeline which will update the commit status where the change was made. Currently, the CI server can only build and test gradle projects.
## Statement of contributions
### Minchong Li

### Ali Shahwali

### Elias Thiele

### Zihao Xu
- Implement `cloneRepository` method and its unit test.
- Write unit tests for `buildRepository` method for three possible cases: build success, compile error, test error.
- Bug fixes in JSON parser.
- Bug fixes in commit status request format. 

## Way of working assessment according to essence standard
From our assessment we are in the "**Formed**"" state, our individual responsibilites are understood, we are in total 4 members that are all enabled to work, everyone understands how to perform their work and everyone is acceptin work. We have defined a communication mechanism that works well. In order to move to the next state "**Collaborating**", we will have to have a more open communication and, maybe by having more voice/physical communication rather than text based. Communication through text only makes it rather hard to fulfill the checklist for the "**Collaborating**" state.