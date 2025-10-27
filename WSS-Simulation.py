import numpy as nm
import os
import platform
import time

# Constants

#Optical Constants
C_BAND_CENTER_NM = 1550 # C-Band in NM
GRATING_LINES_PER_MM = 500
GRATING_PITCH_NM = 1e6 / GRATING_LINES_PER_MM
GRATING_ORDER = 1
INPUT_ANGLE_DEG = 10 # Angle of the light as it hits the grating

# Physical Constants
FOCAL_LENGTH_X_MM = 100
FOCAL_LENGTH_Y_MM = 95
PORT_PITCH_MM = .25 # Distance between output ports 250 Micrometers
LCOS_PIXEL_PITCH_MM = .008 # Size of one LCoS pixel 8 Micrometers

# Convert angles from degrees to radians
theta_i_rad = nm.deg2rad(INPUT_ANGLE_DEG)


# Site Constants
sites = ["Las Vegas", "Salt Lake City", "Los Angeles" , "Dallas" , "Billings", "Casper", "Santa Fe", "Denver", "Local (Drop At This ROADM)"]


# WSS Functionality

#Input fiber bouncing off gradient
def calculate_dispersion_position(wavelength_nm):
    # Calculates the spatial position (X-coordinate) where the wavelength lands on the LCoS
    # We will set the center of the LCoS (X = 0) to be where the 1550 nm wavelength hits.

    # Grating dispersion equation m*lambda = Lambda * (sin(theta_i) + sin(theta_d))
    # m = grating_order

    # Calculate the diffraction angle of the given wavelength (theta_d)
    try:
        sin_theta_d = (GRATING_ORDER * wavelength_nm) / GRATING_PITCH_NM - nm.sin(theta_i_rad)
        theta_d_rad = nm.arcsin(sin_theta_d)
    except ValueError:
        return "Error: wavelength is not supported by grating (Internal reflection, or impossible angle)"

    # Calculate the diffraction angle of the center wavelength

    sin_theta_d_center = (GRATING_ORDER * C_BAND_CENTER_NM) / GRATING_PITCH_NM - nm.sin(theta_i_rad)
    theta_d_rad_center = nm.arcsin(sin_theta_d_center)

    # Calculate the spatial position of the wavelength
    x_position_nm = FOCAL_LENGTH_X_MM * (theta_d_rad - theta_d_rad_center)

    return x_position_nm , nm.rad2deg(theta_d_rad)

def calculate_lcos_steering(wavelength_nm, target_port_index):
    # Calculates the needed steering angle off the LCoS as well as the phase modulation
    # Port index will be -4 (P1) to +4 (P9) where 0 (center) is (P5) and the local drop port will be P9

    # Calculate the final beam angle
    # Assuming the lens is one focal length away from the output ports
    target_position_mm = target_port_index * PORT_PITCH_MM
    theta_out_rad = target_position_mm / FOCAL_LENGTH_Y_MM

    # LCoS Steers the beam to make the beam angle match the final required angle
    theta_lcos_rad = theta_out_rad
    
    # Calculate the required LCoS phase ramp (Phase shift required for each pixel)
    # Phase shift equation (Delta_phi) = k * P_pixel * theta_lcos
    # Where k = 2pi / lambda

    # Conversion for consistency
    wavelength_mm = wavelength_nm * 1e-6

    k = 2 * nm.pi / wavelength_mm
    delta_phi_rad = k * LCOS_PIXEL_PITCH_MM * theta_lcos_rad

    # Showing the phase shift was a fraction of the whole 2pi cycle
    required_phase_ramp_per_pixel = nm.rad2deg(delta_phi_rad) / 360.0

    return (nm.rad2deg(theta_lcos_rad), target_position_mm, required_phase_ramp_per_pixel)

def run_wss_simulation(wavelength, port_num):

    destination_site = sites[port_num - 1]
    print("-"*50)
    print(f"SIMULATION: Wavelength: {wavelength:.2f} nm, Target Destination: {destination_site}, Port Number: P{port_num}")
    print("-"*50)

    port_index = port_num - 5

    # Get grating dispersion angles
    x_pos, theta_d_deg = calculate_dispersion_position(wavelength)

    if isinstance(x_pos, str):
        print(f"Grating Operation: {x_pos}")
        return
    
    print(f"---- 1: Grating Dispersion: (Separating Wavelengths) ----")
    print(f"-> Input Grating Angle (Grating-In): {INPUT_ANGLE_DEG:.2f}°")
    print(f"-> Diffraction Angle (Grating-Out): {theta_d_deg:.4f}°")
    print(f"-> LCoS Landing Position (x-position): {x_pos:.4f}mm")

    # LCoS Steering
    theta_lcos_deg, target_pos, phase_per_pixel = calculate_lcos_steering(wavelength, port_index)

    print(f"---- 2: LCoS Steering (Beam Routing) ----")
    print(f"-> Target Output (relative to center P5): {target_pos:.4f}mm")
    print(f"-> Required LCoS Steering Angle: {theta_lcos_deg:.4f}°")

    # LCoS Phase Profile
    print(f"---- 3: LCoS Phase Modulation ---- ")
    print(f"-> The LCoS must create a phase ramp corresponding to a tilt of {theta_lcos_deg:.4f}°")
    print(f"-> This translates to a phase shift of {phase_per_pixel:.6f} cycles (360°) per LCoS pixel.")
    print(f"-> This phase shift steers the {wavelength:.2f} nm channel from its landing spot at {x_pos:.4f} mm")
    print(f"-> to its output port at P{port_num} (Port-Index {port_index}).")
    print("-"*50)

# Quick function to make screen more readable
def clear_screen():

    try:
        # Detect the operating system
        current_os = platform.system()
        
        if current_os == "Windows":
            os.system('cls')  # Windows clear command
        else:
            os.system('clear')  # Linux/macOS clear command
    except Exception as e:
        print(f"Error clearing screen: {e}")

choice = 0

while(choice != 2):

    print("-"*25 + "Welcome to the WSS" + "-"*25)
    print("The purpose of this program is to simulate the inner workings")
    print("of a Wavelength Selective Switch. This is an LCoS based system.")
    print("The output will contain steering angles, and diffraction angles")
    print("all included to give you a better understanding of what is going on")
    print("insde of a WSS.")

    print()

    print("Please select an option:")
    print("-"*20)
    print("1: Simulate the WSS Switching Processes")
    print("2: Exit")
    choice = int(input("Selection >> "))

    if choice == 1:

        print("In order to route your photonic path we will need a wavelength")
        print("and a port that you would like to go to.")
        print("The allowed wavelengths you can use are in the range of 1530nm - 1565nm")
        print("The sites we can send to are:")
        i = 1
        for site in sites:

            print(f"P{i}. {site}")
            i += 1

        print("Please select your wavelength, numbers only (EX. 1547.58)")
        wavelength = float(input("Wavelength >> "))

        print("Please choose a site to send the traffic to number only (EX. 3)")
        port_selection = int(input("Site Selection >> "))

        print("Thank you now simulating wave switching...")
        time.sleep(5)

        clear_screen()

        run_wss_simulation(wavelength, port_selection)

