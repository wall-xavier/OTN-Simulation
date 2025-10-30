import java.util.*;

public class WSS{

   // Optical WSS Constants
   final int C_BAND_CENTER_NM = 1550;
   final int GRATING_LINES_PER_MM = 500;
   final double GRATING_PITCH_NM = 1e6 / GRATING_LINES_PER_MM;
   final int GRATING_ORDER = 1;
   final int INPUT_ANGLE_DEG = 10; // Angle the light hits the grating

   // Physical WSS Constants
   final int FOCAL_LENGTH_X_MM = 100; // Focal length between the grating and the first imaging optic
   final int FOCAL_LENGTH_Y_MM = 95; // Focal length between the LCoS and the second imaging optic
   final double PORT_PITCH_MM = .25; // Distance between ports 250 Micrometers
   final double LCOS_PIXEL_PITCH_MM = .008; // Size of one pixel 8 Micrometers
   
   // Convert degrees to radians
   double theta_i_rad = Math.toRadians(INPUT_ANGLE_DEG);

   // WSS Configurable peices
   int ports = 0;
   String [] sites = new String[ports];
   
   // Set amount of ports and port names
   public void setSiteLayout(String[] toSetSites, int portNum){
      
      ports = portNum;
      sites = toSetSites;
   
   }
   
   // Display the ports and sites they are assigned to
   public void getSiteLayout(){
   
      System.out.println("This WSS has " + ports + " amount of ports, these ports have been assigned to: ");
      
      for(int i = 0; i <= sites.length - 1; i++){
         int portNumber = i + 1;
         System.out.println("P" + portNumber + " " + sites[i]);
         
      }
      
   }
   
   public double getXPosition(double wavelength){
   
   double thetaDRad = 0;
   double sinThetaD = 0;
   /* Calculates the spatial position (X-coordinate) where the wavelength lands on the LCoS
   We will set the center of the LCoS (X = 0) to be where the 1550 nm wavelength hits. */

   /* Grating dispersion equation m*lambda = Lambda * (sin(theta_i) + sin(theta_d))
   m = grating_order */
   
      // Calculate the diffration angle of the given wavelength     
      try{
      
         sinThetaD = (GRATING_ORDER * wavelength) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
         thetaDRad = Math.asin(sinThetaD);
      }
      catch (ArithmeticException e){
         System.out.println("Error in diffraction angle calculation" + e);
         return 0;
      }
      
      // Calculate the diffraction angle of the center C band wavelength
      double sinThetaDCenter = (GRATING_ORDER * C_BAND_CENTER_NM) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
      double thetaDRadCenter = Math.asin(sinThetaDCenter);
      
      double xPosition = FOCAL_LENGTH_X_MM * (thetaDRad - thetaDRadCenter);

      return xPosition;
   }

   public double getThetaD(double wavelength){
   double sinThetaD = 0;
   double thetaDRad = 0;
   /* Calculates the spatial position (X-coordinate) where the wavelength lands on the LCoS
   We will set the center of the LCoS (X = 0) to be where the 1550 nm wavelength hits. */

   /* Grating dispersion equation m*lambda = Lambda * (sin(theta_i) + sin(theta_d))
   m = grating_order */
   
      // Calculate the diffration angle of the given wavelength
      try{
      
         sinThetaD = (GRATING_ORDER * wavelength) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
         thetaDRad = Math.asin(sinThetaD);
      }
      catch (ArithmeticException e){
         System.out.println("Error in diffraction angle calculation" + e);
         return 0;
      }
      
      // Calculate the diffraction angle of the center C band wavelength
      double sinThetaDCenter = (GRATING_ORDER * C_BAND_CENTER_NM) / GRATING_PITCH_NM - Math.sin(theta_i_rad);
      double thetaDRadCenter = Math.asin(sinThetaDCenter);
      
      double xPosition = FOCAL_LENGTH_X_MM * (thetaDRad - thetaDRadCenter);
      double thetaDDeg = Math.toDegrees(thetaDRad);
      return thetaDDeg;
   }
   
   public double getThetaLcos(double wavelength, int targetPortIndex){
   
       /* Calculates the needed steering angle off the LCoS as well as the phase modulation
       Port index will be -4 (P1) to +4 (P9) where 0 (center) is (P5) and the local drop port will be P9 */
      
   
       /* Calculate the final beam angle
       Assuming the lens is one focal length away from the output ports */
       double targetPositionMM = targetPortIndex * PORT_PITCH_MM;
       double thetaOutRad = targetPositionMM / FOCAL_LENGTH_Y_MM; 
      
       // LCoS Steers the beam to make the beam angle match the final required angle
       double thetaLcosRad = thetaOutRad;
         
        /* Calculate the required LCoS phase ramp (Phase shift required for each pixel)
        Phase shift equation (Delta_phi) = k * P_pixel * theta_lcos
        Where k = 2pi / lambda */
        
       // Conversion for consistency
       double wavelengthMM = wavelength * 1e-6;
       
       double k = 2 * Math.PI  / wavelengthMM;
       
       double deltaPhiRad = k * LCOS_PIXEL_PITCH_MM * thetaLcosRad;
       
       // Showing the phase shift as a fraction of the whole 2pi cycle
       double requiredPhaseRampPerPixel = Math.toDegrees(deltaPhiRad) / 360;
       
       double thetaLcosDeg = Math.toDegrees(thetaLcosRad);
       
       return thetaLcosDeg;
   
   }
   public double getTargetPosition(double wavelength, int targetPortIndex){
   
       /* Calculates the needed steering angle off the LCoS as well as the phase modulation
       Port index will be -4 (P1) to +4 (P9) where 0 (center) is (P5) and the local drop port will be P9 */
      
   
       /* Calculate the final beam angle
       Assuming the lens is one focal length away from the output ports */
       double targetPositionMM = targetPortIndex * PORT_PITCH_MM;
       double thetaOutRad = targetPositionMM / FOCAL_LENGTH_Y_MM; 
      
       // LCoS Steers the beam to make the beam angle match the final required angle
       double thetaLcosRad = thetaOutRad;
      
       /* Calculate the required LCoS phase ramp (Phase shift required for each pixel)
       Phase shift equation (Delta_phi) = k * P_pixel * theta_lcos
       Where k = 2pi / lambda */
        
       // Conversion for consistency
       double wavelengthMM = wavelength * 1e-6;
       
       double k = 2 * Math.PI  / wavelengthMM;
       
       double deltaPhiRad = k * LCOS_PIXEL_PITCH_MM * thetaLcosRad;
       
       // Showing the phase shift as a fraction of the whole 2pi cycle
       double requiredPhaseRampPerPixel = Math.toDegrees(deltaPhiRad) / 360;
       
       double thetaLcosDeg = Math.toDegrees(thetaLcosRad);
       
       return targetPositionMM;
      
   }
   public double requiredPhaseRampPerPixel(double wavelength, int targetPortIndex){
   
       /* Calculates the needed steering angle off the LCoS as well as the phase modulation
       Port index will be -4 (P1) to +4 (P9) where 0 (center) is (P5) and the local drop port will be P9 */
         
      
        /* Calculate the final beam angle
        Assuming the lens is one focal length away from the output ports */
        double targetPositionMM = targetPortIndex * PORT_PITCH_MM;
        double thetaOutRad = targetPositionMM / FOCAL_LENGTH_Y_MM; 
         
        // LCoS Steers the beam to make the beam angle match the final required angle
        double thetaLcosRad = thetaOutRad;
         
        /* Calculate the required LCoS phase ramp (Phase shift required for each pixel)
        Phase shift equation (Delta_phi) = k * P_pixel * theta_lcos
        Where k = 2pi / lambda */
        
       // Conversion for consistency
       double wavelengthMM = wavelength * 1e-6;
       
       double k = 2 * Math.PI  / wavelengthMM;
       
       double deltaPhiRad = k * LCOS_PIXEL_PITCH_MM * thetaLcosRad;
       
       // Showing the phase shift as a fraction of the whole 2pi cycle
       double requiredPhaseRampPerPixel = Math.toDegrees(deltaPhiRad) / 360;
       
       double thetaLcosDeg = Math.toDegrees(thetaLcosRad);
       
       return requiredPhaseRampPerPixel;
      
   }

}

