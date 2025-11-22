/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.System.Startup;

import java.io.IOException;
import java.util.List;
import OTN.Commands.Tokens.Tokenizer;
import OTN.System.FileSystem.FileSystemSetup;
import OTN.Commands.Tokens.Token;
import OTN.Commands.Parse.Parser;
import OTN.Commands.Parse.ParseTree.StatementNode;
import OTN.Commands.CommandGeneration.*;


public class Boot {

    String config;

    public Boot(){
        
        config = loadConfig();
        List<Token> tokens = TokenizeConfig(config);
        Parser parser = new Parser(tokens);
        List<StatementNode> tree = parser.parse();
        CommandGenerator generate = new CommandGenerator(tree);
        StringBuilder output = generate.generator();

        System.out.println(output.toString());

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
