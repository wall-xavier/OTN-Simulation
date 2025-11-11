package OTN.System.Startup;

import java.io.IOException;
import java.util.List;
import OTN.Commands.Tokens.Tokenizer;
import OTN.System.FileSystem.FileSystemSetup;
import OTN.Commands.Tokens.Token;

public class Boot {

    String config;

    public Boot(){
        
        String config = loadConfig();
        TokenizeConfig(config);

    }

    public String loadConfig(){

        try{
        
            config = FileSystemSetup.getConfigFile();
        }

        catch(IOException e){

            System.out.println(e);

        }

        return config;
    }

    public void TokenizeConfig(String preToken){

        Tokenizer toTokenize = new Tokenizer(preToken);

        List<Token> tokens = toTokenize.tokenize();

        System.out.println("--- TOKEN STREAM ---");
        
        System.out.println(tokens); 

        System.out.println("--------------------");
    }

}
