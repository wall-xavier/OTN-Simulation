# OTN Simulation
The purpose of this program is to show the flow of photonic signals as they traverse an OTN (Optical Transport Network)

This simulation will follow a photonic signal through all components of the OTN showing things like, diffraction angles, loss, beam steering, and much more.

This is a work in progress, I am working to bring in support for most main components of an OTN system, eventually bringing it all together to create a fully functioning optical network.

## Important Installation Note
This project targets Java 21 (LTS). You will need Maven and an OpenJDK 21 (or a JDK that provides Java 21) installed on your system to compile and run this program.

## Configuring Devices
The OTN Network will come with a default configuration, you can change the configuration by using the terminal that you will be launched into. The configuration of devices on the network is done in ProfLang for OTN, a proprietary language developed for this project. Details on this language can be found in the [docs](/docs/) folder. 

Steps:

    # Verify Java and Maven
    mvn -v

    # Compile
    mvn package

    # Run
    java -jar target/otn-simulation-project-1.0.jar
