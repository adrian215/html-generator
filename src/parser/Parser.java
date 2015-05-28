package parser;

import parser.exceptions.BadTokenException;
import parser.expressions.*;
import parser.expressions.implementations.*;
import parser.literals.Operator;
import parser.operations.*;
import parser.expressions.implementations.CallExpression;
import parser.operations.implementations.*;
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
    private Iterator<Token> tokenIterator;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        tokenIterator = tokens.iterator();
        advance();
    }

    public Expression parse() throws BadTokenException {
        return new BlockExpression(script());
    }

    private List<Expression> script() throws BadTokenException {
        List<Expression> expressions = new ArrayList<>();
        while (!accept(END)) {
            if (accept(PRINT)) {
                expressions.add(print());
            } else {
                start();
                expressions.add(block());
                stop();
            }
        }
//        Expression exp;
//        start();
//        exp = block();
//        stop();
        return expressions;
    }

    private boolean accept(TokenType expected) {
        return getToken().getType() == expected;
    }

    private void check(PairResult<Boolean, Expression> operationParam, ExpressionSupplier<Expression> supplier) {
        Expression result;
        if(!operationParam.getFirstValue()){
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
                throw new BadTokenException(getParameter());
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
                    throw new BadTokenException(getParameter());
            }
            if (accept(CLOSE_CURLY_BRACES))
                advance();
            else
                throw new BadTokenException(getParameter());
        } else {
            Expression exp = statement();
            expressions.add(exp);
        }

        return new BlockExpression(expressions);
    }

    private Expression statement() throws BadTokenException {
        PairResult<Boolean, Expression> operationResult = new PairResult<>(Boolean.FALSE);
        check(operationResult, this::assign);
        check(operationResult, this::declaration);
        check(operationResult, this::conditional);
        check(operationResult, this::loop);
        check(operationResult, this::call);
        check(operationResult, this::print);
        if(operationResult.getFirstValue()) {
            return operationResult.getSecondValue();
        }
        else
            throw new BadTokenException(getParameter());
    }

    private Expression call() throws BadTokenException {
        String methodName;
        List<MathOperation> params;
        methodName = getName();
        if(accept(OPEN_BRACKET))
            advance();
        else
            throw new BadTokenException(getParameter());

        params = callParams();

        if(accept(CLOSE_BRACKET))
            advance();
        else
            throw new BadTokenException(getParameter());

        return new CallExpression(methodName, params);
    }

    private Expression print() throws BadTokenException {
        String result;
        if (accept(PRINT)) {
            result = getParameter();
            advance();
            return new PrintExpression(result);
        } else if (accept(STOP)) {
            stop();
            Expression printExpression = print();
            start();
            return printExpression;
        } else {
        }
            throw new BadTokenException(getParameter());
    }

    private String getParameter() {
        return getToken().getParameter();
    }

    private List<MathOperation> callParams() throws BadTokenException {
        List<MathOperation> params = new ArrayList<>();
        try {
            MathOperation firstParam = operation();
            params.add(firstParam);
        }catch (BadTokenException e) {
            //return empty list - no arguments
            return params;
        }
        while (accept(COMMA)){
            advance();
            params.add(operation());
        }
        return params;
    }

    private Expression loop() throws BadTokenException {
        MathOperation from, to;
        Expression statements;
        if(accept(FOR)){
            advance();
            if(accept(OPEN_BRACKET))
                advance();
            else
                throw new BadTokenException(getParameter());
            from = operation();
            if(accept(TO))
                advance();
            else
                throw new BadTokenException(getParameter());
            to = operation();
            if(accept(CLOSE_BRACKET))
                advance();
            else
                throw new BadTokenException(getParameter());
            statements = block();

            return new LoopExpression(from, to, statements);
        }
        else
            throw new BadTokenException(getParameter());
    }

    private Expression conditional() throws BadTokenException {
        BoolOperation condition;
        Expression statements;
        if(accept(IF))
        {
            advance();
            if(accept(OPEN_BRACKET))
                advance();
            else
                throw new BadTokenException(getParameter());

            condition = booleanOperation();

            if(accept(CLOSE_BRACKET))
                advance();
            else
                throw new BadTokenException(getParameter());

            statements = block();

            return new ConditionalExpression(condition, statements);

        }
        else
            throw new BadTokenException(getParameter());
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
                throw new BadTokenException(getParameter());

            declarationParams = declarationParams();

            if(accept(CLOSE_BRACKET))
                advance();
            else
                throw new BadTokenException(getParameter());
            statements = block();
        }
        else
            throw new BadTokenException(getParameter());
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
                throw new BadTokenException(getParameter());
            String param = variableName();
            params.add(param);
        }
        return params;
    }

    private Expression assign() throws BadTokenException {
        String variableName = variableName();
        MathOperation value;
        if(accept(ASSIGN)) {
            advance();
            value = operation();
        }
        else
            throw new BadTokenException(getParameter());
        return new AssignExpression(variableName, value);
    }

    private MathOperation operation() throws BadTokenException {
        MathOperation firstArgument = weakArgument();
        List<Operator> operators = new ArrayList<>();
        List<MathOperation> weakArguments = new ArrayList<>();

        while(accept(PLUS) || accept(MINUS)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            weakArguments.add(weakArgument());
        }
        return new OperationExpression(firstArgument, operators, weakArguments);
    }

    private MathOperation weakArgument() throws BadTokenException {
        MathOperation firstArgument = terminal();
        List<Operator> operators = new ArrayList<>();
        List<MathOperation> strongArguments = new ArrayList<>();

        while(accept(MULLTIPLICATION) || accept(DIVISION)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            strongArguments.add(terminal());
        }
        return new OperationExpression(firstArgument, operators, strongArguments);
    }

    private MathOperation terminal() throws BadTokenException {
        MathOperation result;
        if (accept(NUMERIC)) {
            result = new Const(getParameter());
            advance();
            return result;
        }
        else if(accept(QUOTES)){
            advance();
            if(accept(OTHER)) {
                result = new Const(getParameter());
                advance();
            }
            else
                throw new BadTokenException(getParameter());

            if(accept(QUOTES)){
                advance();
                return result;
            }
            else
                throw new BadTokenException(getParameter());
        }
        else
            return variableCall();
    }

    private BoolOperation booleanOperation() throws BadTokenException {
        BoolOperation firstArgument = weakBoolArgument();
        List<Operator> operators = new ArrayList<>();
        List<BoolOperation> weakArguments = new ArrayList<>();

        while(accept(OR)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            weakArguments.add(weakBoolArgument());
        }
        return new BoolOperationExpression(firstArgument, operators, weakArguments);
    }

    private BoolOperation weakBoolArgument() throws BadTokenException {
        BoolOperation firstArgument = strongBoolArgument();
        List<Operator> operators = new ArrayList<>();
        List<BoolOperation> strongArguments = new ArrayList<>();

        while(accept(AND)){
            operators.add(mapOperator(getToken().getType()));
            advance();
            strongArguments.add(strongBoolArgument());
        }
        return new BoolOperationExpression(firstArgument, operators, strongArguments);
    }

    private BoolOperation strongBoolArgument() throws BadTokenException {
        MathOperation firstArgument = terminal();
        Operator operator = mapOperator(getToken().getType());
        advance();
        MathOperation secondArgument = terminal();
        return new BoolEqual(firstArgument, operator, secondArgument);
    }

    private MathOperation variableCall() throws BadTokenException {
        String variableName;
        variableName = variableName();
        return new VariableExpression(variableName);
    }

    private void checkQuotes() throws BadTokenException {
        if(accept(QUOTES))
            advance();
        else
            throw new BadTokenException(getParameter());
    }

    private String variableName() throws BadTokenException {
        String variableName;
        if (accept(DOLLAR)) {
            advance();
            variableName = getName();
        } else
            throw new BadTokenException(getParameter());
        return variableName;
    }

    private String getName() throws BadTokenException {
        String name;
        if (accept(OTHER)) {
            name = getParameter();
            advance();
            return name;
        } else
            throw new BadTokenException(getParameter());
    }

    private void start() throws BadTokenException {
        if (accept(START))
            advance();
        else
            throw new BadTokenException(getParameter());
    }

    private void stop() throws BadTokenException {
        if (accept(STOP))
            advance();
        else
            throw new BadTokenException(getParameter());
    }

    private void advance() {
        if(tokenIterator.hasNext())
            currentToken = tokenIterator.next();
    }

    private boolean canAdvance(){
        return tokenIterator.hasNext();
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
