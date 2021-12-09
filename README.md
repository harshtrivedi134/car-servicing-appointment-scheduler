# car-servicing-appointment-system
appointment scheduling system for a car service center with single location


# Pre-requisites 

* Java 11
* Docker 
* Maven


# How to run locally

1. Start the postgres server by running the bootstrap.sh script as shown below.  
   This will configure the postgres with username/password and database.
   `$ ./bootstrap.sh`
2. To build the sping-boot maven project.  
   `$ mvn clean install`
3. To run the spring-boot project.  
   `$ mvn spring-boot:run`
4. To stop the postgres container.  
    `$ ./teardown.sh`
   
## License

This project is licensed under the MIT License