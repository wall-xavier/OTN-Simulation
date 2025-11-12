package OTN.Commands.Parse;

import OTN.Commands.Tokens.Token;
import OTN.Commands.Parse.ParseTree.*;
import java.util.List;

public class Parser {

    List<Token> tokens;
    int index = 0;
    
    public Parser(List<Token> toReadTokens){

        tokens = toReadTokens;

    }

    public ModifierNode parseMod(){

        if(peek() != null && peek().type == Token.types.INTLIT){

            ModifierNode modNode = new ModifierNode(consume());

            return modNode;

        }

        else{

            return null;

        }
    }

    public DeviceNode parse(){

        DeviceNode devNode = null;

        while(peek() != null){

            if(peek().type == Token.types.KEYWORD){
                consume();
                ModifierNode modNode = parseMod();
                if(modNode != null){
                    devNode = new DeviceNode(modNode);
                }

                else{
                    System.out.println("Error in parsing.");
                }

                if(peek() != null && peek().type == Token.types.SEMICOLON){

                    consume();

                }

                else{

                    System.out.println("Error in parsing.");

                }
            }

        }

        return devNode;

    }

    private Token peek(){

    if (index >= tokens.size()){ 
        return null;

    } 
    else {
    
        return tokens.get(index); 
    
    }
}

    /*
    private Token peek(int peekAhead){

        int peekIndex = index + peekAhead;
        
        if (peekIndex >= tokens.size()){
            return null;
        } 
        else {
    
            return tokens.get(peekIndex); 
       
        }
    }
    */

    private Token consume(){

        return tokens.get(index++);

    }

}
