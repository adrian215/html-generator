package parser;

import parser.exceptions.BadTokenException;
import parser.expressions.*;
import parser.literals.Operator;
import parser.literals.Terminal;
import tokenizer.Token;
import tokenizer.TokenType;
import utils.ExpressionSupplier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static tokenizer.TokenType.*;

/**
 * Created by Adrian on 2015-05-13.
 */
public class Parser {
    private final List<Token> tokens;
    private Iterator<Token> tokenIterator;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        tokenIterator = tokens.iterator();
        advance();
    }

    public Expression parse() throws BadTokenException {
        return script();
    }

    private Expression script() throws BadTokenException {
        Expression exp;
        start();
        exp = block();
        stop();
        return exp;
    }

    private boolean accept(TokenType expected) {
        return getToken().getType() == expected;
    }

    private void check(PairResult<Boolean, Expression> operationParam, ExpressionSupplier<Expression> supplier) {
        Expression result;
        if(operationParam.getFirstValue().booleanValue() == false){
            try {
                result = supplier.get();
                operationParam.setFirstValue(Boolean.TRUE);
                operationParam.setSecondValue(result);
            }catch (BadTokenException e) {
                // wywolana nie ta funkcja, nie oznaczaj jako skonczone
            }
        }
    }

    private Operator mapOperator(TokenType type) throws BadTokenException {
        switch (type){
            case PLUS:
                return Operator.PLUS;
            case MINUS:
                return Operator.MINUS;
            case MULLTIPLICATION:
                return Operator.MULTIPLICATION;
            case DIVISION:
                return Operator.DIVISION;
            case OR:
                return Operator.OR;
            case AND:
                return Operator.AND;
            case EQUALS:
                return Operator.EQUALS;
            default:
                throw new BadTokenException();
        }
    }

    private Expression block() throws BadTokenException {
        List<Expression> expressions = new ArrayList<>();

        if (accept(OPEN_CURLY_BRACES)) {
            advance();
            while (!accept(CLOSE_CURLY_BRACES)) {
                Expression exp = statement();
                expressions.add(exp);
                if (accept(SEMICOLON)) {
                    advance();
                } else
                    throw new BadTokenException();
            }
            if (accept(CLOSE_CURLY_BRACES))
                advance();
            else
                throw new BadTokenException();
        } else {
            Expression exp = statement();
            expressions.add(exp);
        }

        return new BlockExpression(expressions);
    }

    private Expression statement() throws BadTokenException {
        PairResult<Boolean, Expression> operationResult = new PairResult<Boolean, Expression>(Boolean.FALSE);
        check(operationResult, this::assign);
        check(operationResult, this::declaration);
        check(operationResult, this::conditional);
        if(operationResult.getFirstValue().booleanValue() == true) {
            return operationResult.getSecondValue();
        }
        else
            throw new BadTokenException();
    }

    private Expression conditional() throws BadTokenException {
        Expression condition;
        Expression statements;
        if(accept(IF))
        {
            advance();
            if(accept(OPEN_BRACKET))
                advance();
            else
                throw new BadTokenException();

            condition = booleanOperation();

            if(accept(CLOSE_BRACKET))
                advance();
            else
                throw new BadTokenException();

            statements = block();

            return new ConditionalExpression(condition, statements);

        }
        else
            throw new BadTokenException();
    }

    private DeclarationExpression declaration() throws BadTokenException {
        String declarationName;
        List<String> declarationParams;
        Expression statements;
        if (accept(HASH)) {
            advance();
            declarationName = getName();
            if(accept(OPEN_BRACKET))
                advance();
            else
                throw new BadTokenException();

            declarationParams = declarationParams();

            if(accept(CLOSE_BRACKET))
                advance();
            else
                throw new BadTokenException();
            statements = block();
        }
        else
            throw new BadTokenException();
        return new DeclarationExpression(declarationName, declarationParams, statements);
    }

    private List<String> declarationParams() throws BadTokenException {
        List<String> params = new ArrayList<>();
        String var = variableName();
        params.add(var);
        while(!accept(CLOSE_BRACKET)){
            if(accept(COMMA))
                advance();
            else
                throw new BadTokenException();
            String param = variableName();
            params.add(param);
        }
        return params;
    }

    private Expression assign() throws BadTokenException {
        String variableName = variableName();
        Expression value;
        if(accept(ASSIGN)) {
            advance();
            value = operation();
        }
        else
            throw new BadTokenException();
        return new AssignExpression(variableName, value);
    }

    private Expression operation() throws BadTokenException {
        Expression firstArgument = weakArgument();
        List<Operator> operators = new ArrayList<>();
        List<Expression> weakArguments = new ArrayList<>();

        while(accept(PLUS) || accept(MINUS)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            weakArguments.add(weakArgument());
        }
        return new OperationExpression(firstArgument, operators, weakArguments);
    }

    private OperationExpression weakArgument() throws BadTokenException {
        Expression firstArgument = terminal();
        List<Operator> operators = new ArrayList<>();
        List<Expression> strongArguments = new ArrayList<>();

        while(accept(MULLTIPLICATION) || accept(DIVISION)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            strongArguments.add(terminal());
        }
        return new OperationExpression(firstArgument, operators, strongArguments);
    }

    private Expression terminal() throws BadTokenException {
        Expression result;
        if (accept(NUMERIC)) {
            result = new Terminal(getToken().getParameter());
            advance();
            return result;
        }
        else if(accept(QUOTES)){
            advance();
            if(accept(OTHER)) {
                result = new Terminal(getToken().getParameter());
                advance();
            }
            else
                throw new BadTokenException();

            if(accept(QUOTES)){
                advance();
                return result;
            }
            else
                throw new BadTokenException();
        }
        else
            return variableCall();
    }

    //    TODO implement
    private Expression booleanOperation() throws BadTokenException {
        Expression firstArgument = weakBoolArgument();
        List<Operator> operators = new ArrayList<>();
        List<Expression> weakArguments = new ArrayList<>();

        while(accept(OR)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            weakArguments.add(weakBoolArgument());
        }
        return new OperationExpression(firstArgument, operators, weakArguments);
    }

    private Expression weakBoolArgument() throws BadTokenException {
        Expression firstArgument = strongBoolArgument();
        List<Operator> operators = new ArrayList<>();
        List<Expression> strongArguments = new ArrayList<>();

        while(accept(AND)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            strongArguments.add(strongBoolArgument());
        }
        return new OperationExpression(firstArgument, operators, strongArguments);
    }

    private Expression strongBoolArgument() throws BadTokenException {
        Expression firstArgument = terminal();
        List<Operator> operators = new ArrayList<>();
        List<Expression> strongArguments = new ArrayList<>();

        while(accept(EQUALS)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            strongArguments.add(terminal());
        }
        return new OperationExpression(firstArgument, operators, strongArguments);
    }

    private Expression variableCall() throws BadTokenException {
        String variableName;
        variableName = variableName();
        return new VariableExpression(variableName);
    }

    private void checkQuotes() throws BadTokenException {
        if(accept(QUOTES))
            advance();
        else
            throw new BadTokenException();
    }

    private String variableName() throws BadTokenException {
        String variableName;
        if (accept(DOLLAR)) {
            advance();
            variableName = getName();
        } else
            throw new BadTokenException();
        return variableName;
    }

    private String getName() throws BadTokenException {
        String name;
        if (accept(OTHER)) {
            name = getToken().getParameter();
            advance();
            return name;
        } else
            throw new BadTokenException();
    }

    private void start() throws BadTokenException {
        if (accept(START))
            advance();
        else
            throw new BadTokenException();
    }

    private void stop() throws BadTokenException {
        if (accept(STOP))
            advance();
        else
            throw new BadTokenException();
    }

    private void advance() {
        if(tokenIterator.hasNext())
            currentToken = tokenIterator.next();
    }

    private Token getToken() {
        return currentToken;
    }

    private class PairResult<X, Y>{
        private X firstValue;
        private Y secondValue;

        public PairResult(X firstValue) {
            this.firstValue = firstValue;
        }

        public X getFirstValue() {
            return firstValue;
        }

        public Y getSecondValue() {
            return secondValue;
        }

        public void setFirstValue(X firstValue) {
            this.firstValue = firstValue;
        }

        public void setSecondValue(Y secondValue) {
            this.secondValue = secondValue;
        }
    }

}
