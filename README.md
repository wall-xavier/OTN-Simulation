# OTN Simulation
The purpose of this program is to show the flow of photonic signals as they traverse an OTN (Optical Transport Network)

This simulation will follow a photonic signal through all components of the OTN showing things like, diffraction angles, loss, beam steering, and much more.

This is a work in progress, I am working to bring in support for most main components of an OTN system, eventually bringing it all together to create a fully functioning optical network.

## Important Installation Note
You will need to have maven along with OpenJDK 17 installed on your system to compile and run this program.

Compile the application with:

    # Compile
    mvn package

    # Run
    java -jar target/otn-simulation-project-1.0.jar
