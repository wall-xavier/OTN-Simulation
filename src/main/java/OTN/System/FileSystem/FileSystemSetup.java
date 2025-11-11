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
