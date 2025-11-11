package OTN.Commands.Tokens;

public class Token{

    enum types {
        KEYWORD,
        INTLIT,
        SEMICOLON};

    types type;
    String value;

    public Token(types type, String value){

        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {

        return "[" + type + ": \"" + value + "\"]\n";
    
    }
}
