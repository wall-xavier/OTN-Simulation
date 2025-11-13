/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.Commands.Tokens;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    String src;
    int index = 0;

    public Tokenizer(String sourceCode){

        src = sourceCode;

    }

    public List<Token> tokenize() {

    StringBuilder buffer = new StringBuilder("");
    List<Token> tokens = new ArrayList<>();
    
    while (peek() != null) {

        if (Character.isWhitespace(peek())) {
            consume();
            continue;
        }
        
        else if (Character.isAlphabetic(peek())) {
            
            buffer.append(consume()); 
            
            while (peek() != null && Character.isLetterOrDigit(peek())) {
                buffer.append(consume());
            }

            String value = buffer.toString();
            
            if (value.equals("WSS")) {

                tokens.add(new Token(Token.types.KEYWORD, value));
            
            } else {
        
                System.out.println("Invalid Identifier/Syntax: " + value);
                buffer.setLength(0);
                break; 
            }
            
            buffer.setLength(0); 
            continue;
        }

        else if (Character.isDigit(peek())) {
            
            buffer.append(consume());
            
            while (peek() != null && Character.isDigit(peek())) {
             
                buffer.append(consume());
            
            }

            
            tokens.add(new Token(Token.types.INTLIT, buffer.toString()));
            buffer.setLength(0);
            continue;
       
        }

        else if (peek() == ';') {
            
            tokens.add(new Token(Token.types.SEMICOLON, ";"));
            consume();
            continue;
       
        }

        else {
            System.out.println("Syntax Error! Unrecognized character: " + peek());
            consume();
        }
    }

    return tokens;
}

    private Character peek(){

        if(index + 1 >= src.length()){

            return null;

        } else{

            return src.charAt(index);
        }

    }

    /*
    private Character peek(int peekAhead){

        if(index + peekAhead >= src.length()){

            return null;

        } else{

            return src.charAt(index);
        }

    }
    */

    private char consume(){

        return src.charAt(index++);

    }
    
}
