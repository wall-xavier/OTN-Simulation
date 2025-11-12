package OTN.System.Startup;

import java.io.IOException;
import java.util.List;
import OTN.Commands.Tokens.Tokenizer;
import OTN.System.FileSystem.FileSystemSetup;
import OTN.Commands.Tokens.Token;
import OTN.Commands.Parse.Parser;
import OTN.Commands.Parse.ParseTree.DeviceNode;
import OTN.Commands.CommandGeneration.*;


public class Boot {

    String config;

    public Boot(){
        
        String config = loadConfig();
        List<Token> tokens = TokenizeConfig(config);
        Parser parser = new Parser(tokens);
        DeviceNode tree = parser.parse();
        CommandGenerator generate = new CommandGenerator(tree);
        generate.generator();

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

    public List <Token> TokenizeConfig(String preToken){

        Tokenizer toTokenize = new Tokenizer(preToken);

        List<Token> tokens = toTokenize.tokenize();

        return tokens;
    }
}
