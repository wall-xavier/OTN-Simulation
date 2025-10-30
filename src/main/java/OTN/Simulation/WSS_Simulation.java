package OTN.Simluation;
import OTN.Simulation.Assets.WSS.WSS;
import OTN.Simulation.Assets.Light.PhotonicPath;
import java.util.*;

public class WSS_Simulation{

   public static void main(String[]args){
   
      double wavelength;
      int portNum;
      int destinationPort;
      
      Scanner input = new Scanner(System.in);
   
      System.out.println("-------------------------------------------------------");
      System.out.println("           Welcome to the WSS Simulation               ");
      System.out.println("-------------------------------------------------------");
      
      // Enter Wavelength to be switched
      
      System.out.println("\n\n Please provide a wavelength that you would like to switch: ");
      System.out.print("\tWavelength (Numbers only in NM) >> ");
      wavelength = input.nextDouble();
      
      // Enter amount of WSS outgoing ports
      
      System.out.println("\n\n Please provide the amount of ports you would like your WSS to switch: ");
      System.out.print("\t Number of Ports >> ");
      portNum = input.nextInt();
      
      // Create labs for the ports
      
      System.out.println("\n\n Please provide the names of the sites for these ports: ");
      String [] sites = new String[portNum];
      input.nextLine();
      for(int i = 0; i < sites.length; i ++){
      
         int portIndex = i + 1;
         
         System.out.print("\t P" + portIndex + " assigned to Site >> ");
         sites[i] = input.nextLine();
         System.out.println();
         
      }
      
      System.out.println("\n\nCreating your WSS...");
      
      // Use constructor to create a WSS object
      
      WSS demoWSS = new WSS(sites, portNum);
      
      // Print out current wss layout
      
      demoWSS.getSiteLayout();
      
      System.out.println("\n\nWhich port would you like to switch the " + wavelength + " wavelength?");
      System.out.print("Numbers only input the destination port >> ");
      destinationPort = input.nextInt();
      
      // Begin WSS simulation on given wavelength and port index
      
      System.out.println("-------------------------------------------------------");
      System.out.println("                 Running Simulation                    ");
      System.out.println("-------------------------------------------------------");
      
      // Grating Dispersion Calculations
      
      System.out.println("---- 1: Grating Dispersion: (Separating Wavelengths) ----");
      System.out.println("-> Input Grating Angle (Grating-In) >> " + demoWSS.INPUT_ANGLE_DEG);
      System.out.println("-> Diffraction Angle (Grading-Out) >> " + demoWSS.getThetaD(wavelength));
      System.out.println("-> LCoS landing position (x-position) >> " + demoWSS.getXPosition(wavelength));
   
      // LCoS Steering Calculations
      
      System.out.println("---- 2: LCoS Steering (Beam Routing) ----");
      System.out.println("-> Target Output (relative to center P5) >> " + demoWSS.getTargetPosition(wavelength, destinationPort) + "mm");
      System.out.println("-> Required LCoS Steering Angle >> " + demoWSS.getThetaLcos(wavelength, destinationPort) + " degrees");
 
      // LCoS Phase Modulation
      
      System.out.println("---- 3: LCoS Phase Modulation ----");
      System.out.println("-> The LCoS must create a phase ramp corresponding to a tilt of >> " + demoWSS.getThetaLcos(wavelength,destinationPort) + " degrees");
      System.out.println("-> This translates to a phase shift of >> "+ demoWSS.requiredPhaseRampPerPixel(wavelength,destinationPort) + " cycles (360) per LCoS pixel.");
      System.out.println("-> This phase shift steers the " + wavelength + " nm channel from its landing spot at " + demoWSS.getXPosition(wavelength) + " mm");
      System.out.println("-> to its output port at P" + destinationPort + " heading to " + sites[destinationPort - 1]);
      
      System.out.println("\n");
      System.out.println("-------------------------------------------------------");
      System.out.println("                      Thank You                        ");
      System.out.println("-------------------------------------------------------");
     
   }

}