/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.System.FileSystem;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class FileSystemSetup {
    
    public static String getConfigFile() throws IOException{

        String configFile = "OTN/System/FileSystem/Startup-Config";
        InputStream inputStream = FileSystemSetup.class.getClassLoader().getResourceAsStream(configFile);
        
        if(inputStream == null){

            throw new IllegalArgumentException("Resource not found: " + configFile);

        }

        try (InputStream is = inputStream) {
            byte[] bytes = is.readAllBytes();
            
            // Convert the bytes to a String using the correct encoding
            return new String(bytes, StandardCharsets.UTF_8);
        }

    }

}
