package OTN.Simulation.Assets.WSS;
import java.util.*;

public class WSS{

    // Optical WSS Constants
    final int C_BAND_CENTER_NM = 1550;
    final int GRATING_LINES_PER_MM = 500;
    // Calculate grating pitch (Lambda) in nm (1 meter = 10^9 nm, 1 mm = 10^6 nm)
    final double GRATING_PITCH_NM = 1e6 / GRATING_LINES_PER_MM;
    final int GRATING_ORDER = 1;
    public final int INPUT_ANGLE_DEG = 10; // Angle the light hits the grating

    // Physical WSS Constants (in mm)
    final int FOCAL_LENGTH_X_MM = 100; // Focal length (Grating to LCoS)
    final int FOCAL_LENGTH_Y_MM = 95;  // Focal length (LCoS to Output Ports)
    final double PORT_PITCH_MM = .25;  // Distance between ports (250 Micrometers)
    final double LCOS_PIXEL_PITCH_MM = .008; // Size of one pixel (8 Micrometers)
    
    // Convert degrees to radians
    double theta_i_rad = Math.toRadians(INPUT_ANGLE_DEG);

    // WSS Configurable pieces
    int ports = 0;
    String [] sites = new String[0];
    private final int centerPortNumber; 

    // Set amount of ports and port names
    public WSS(String[] toSetSites, int portNum){
        if (portNum < 1 || toSetSites.length != portNum) {
            throw new IllegalArgumentException("Port count must be positive and match the site array length.");
        }
        this.ports = portNum;
        this.sites = toSetSites;
        this.centerPortNumber = (portNum + 1) / 2;
    }
    
    private int getPhysicalIndex(int portNumber) {
        return portNumber - this.centerPortNumber;
    }
    
    // Display the ports and sites they are assigned to
    public void getSiteLayout(){
        System.out.println("This WSS has " + ports + " port(s).");
        System.out.println("The physical center axis aligns with Port " + centerPortNumber + " (Physical Index 0).");
        System.out.println("----------------------------------------");
        
        for(int i = 0; i < sites.length; i++){
            int portNumber = i + 1;
            int physicalIndex = getPhysicalIndex(portNumber);
            System.out.printf("Port %d (Phys Index: %d) assigned to: %s%n", 
                               portNumber, physicalIndex, sites[i]);
        }
        System.out.println("----------------------------------------");
    }
    
   // Calculates the spatial position (X-coordinate in mm) where a wavelength lands on the LCoS.
    public double getXPosition(double wavelength){
        double thetaDRad = 0;
        double sinThetaD = 0;
        
        // 1. Calculate the diffraction angle (theta_d) for the given wavelength
        try{
            // Grating dispersion equation: m*lambda = Lambda * (sin(theta_i) + sin(theta_d))
            // Rearranged: sin(theta_d) = (m * lambda) / Lambda - sin(theta_i)
            sinThetaD = (GRATING_ORDER * wavelength) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
            thetaDRad = Math.asin(sinThetaD);
        }
        catch (ArithmeticException e){
            System.out.println("Error in diffraction angle calculation: " + e.getMessage());
            return 0;
        }
        
        // 2. Calculate the diffraction angle for the center C band wavelength (reference point)
        double sinThetaDCenter = (GRATING_ORDER * C_BAND_CENTER_NM) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
        double thetaDRadCenter = Math.asin(sinThetaDCenter);
        
        // 3. Calculate the X position based on the focal length and the angular difference
        double xPosition = FOCAL_LENGTH_X_MM * (thetaDRad - thetaDRadCenter);

        return xPosition;
    }

    // Calculates the diffraction angle (theta_d) in degrees for a given wavelength.

    public double getThetaD(double wavelength){
        double sinThetaD;
        double thetaDRad;
        
        try{
            sinThetaD = (GRATING_ORDER * wavelength) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
            thetaDRad = Math.asin(sinThetaD);
        }
        catch (ArithmeticException e){
            System.out.println("Error in diffraction angle calculation: " + e.getMessage());
            return 0;
        }
        
        return Math.toDegrees(thetaDRad);
    }
    
    // Calculates the required LCoS steering angle (theta_lcos) in degrees 
     
    public double getThetaLcos(double wavelength, int targetPortNumber){
        
        // Convert the 1-based port number to the physical index
        int physicalIndex = getPhysicalIndex(targetPortNumber);
        
        // 1. Calculate the target position in mm relative to the center (0)
        double targetPositionMM = physicalIndex * PORT_PITCH_MM;
        
        // 2. Calculate the final required beam angle (theta_out) after the LCoS, 
        // assuming the output lens is one focal length away (FOCAL_LENGTH_Y_MM)
        double thetaOutRad = targetPositionMM / FOCAL_LENGTH_Y_MM; 
        
        // 3. The LCoS must introduce an angle (theta_lcos) equal to the target output angle.
        double thetaLcosRad = thetaOutRad;
        
        return Math.toDegrees(thetaLcosRad);
    }
    
    // Calculates the target position in millimeters (mm) where the beam must land
    public double getTargetPosition(double wavelength, int targetPortNumber){
        
        int physicalIndex = getPhysicalIndex(targetPortNumber);
        
        // Target Position = Physical Index * Port Pitch
        double targetPositionMM = physicalIndex * PORT_PITCH_MM;
        
        return targetPositionMM;
    }
    
    
    // Calculates the required LCoS phase ramp per pixel, expressed as a fraction of the 2-pi cycle (0 to 1).
    public double requiredPhaseRampPerPixel(double wavelength, int targetPortNumber){
        
        int physicalIndex = getPhysicalIndex(targetPortNumber);
        
        // 1. Calculate the required LCoS steering angle in radians (theta_lcos)
        double targetPositionMM = physicalIndex * PORT_PITCH_MM;
        double thetaOutRad = targetPositionMM / FOCAL_LENGTH_Y_MM; 
        double thetaLcosRad = thetaOutRad;
        
        /* 2. Calculate the required LCoS phase ramp (Delta_phi) for each pixel:
         * Delta_phi (rad) = k * P_pixel * theta_lcos
         * Where k = 2pi / lambda (wavelength must be in mm)
         */
        
        // Convert wavelength from nm to mm for consistency in units
        double wavelengthMM = wavelength * 1e-6;
        
        double k = 2 * Math.PI / wavelengthMM; // Wavenumber k in (1/mm)
        
        // Delta_phi in radians
        double deltaPhiRad = k * LCOS_PIXEL_PITCH_MM * thetaLcosRad;
        
        // 3. Convert the phase shift (deltaPhiRad) into a fraction of a 2-pi cycle
        double requiredPhaseRampPerPixel = deltaPhiRad / (2 * Math.PI);
        
        return requiredPhaseRampPerPixel;
    }

}
