
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

class InfixToPostfixStack {
    static String[] operators = {"+","-", "*", "/", "^"};
    static List<String> operatorsList = Arrays.asList(operators);
    public static void main( String[] args ) {
        int numberOfTests = 0;
        Scanner scan = new Scanner(System.in);
        numberOfTests = scan.nextInt();
        List<String> expressions = new ArrayList<String>();
        for (int i = 0; i < numberOfTests; i++) {
            String exp = scan.nextLine();
            exp = scan.nextLine();
            expressions.add(exp);
        }

        List<String> postFixExpressions = new ArrayList<String>();
        for (int i = 0; i < numberOfTests; i++) {
            String postFix = toPostFix(expressions.get(i));
            postFixExpressions.add(postFix);
            System.out.println("Postfix: " + postFix);
        }

        postFixExpressions.forEach(new Consumer<String>() {
            @Override
            public void accept( String postfixExp ) {
                Stack stack = new Stack();
                String[] postfixExpSplit = postfixExp.split(" ");
                for (String token : postfixExpSplit) {
                    if(isOperand(token)) {
                        String operand1 = stack.pop();
                        String operand2 = stack.pop();
                        String value =  evaluate(operand1, operand2, token);
                        stack.push(value);
                    } else {
                        stack.push(token);
                    }
                }
                String value = stack.pop();
                System.out.println("Evaluated Value: " + value);
            }
        });
    }

    private static String evaluate( String operand1, String operand2, String token ) {
        Integer value = 0;
        switch(token) {
            case "+":
                value = (Integer.parseInt(operand1) + Integer.parseInt(operand2));
                return value.toString();

            case "-":
                value = (Integer.parseInt(operand2) - Integer.parseInt(operand1));
                return value.toString();
            case "*":
                value = (Integer.parseInt(operand1) * Integer.parseInt(operand2));
                return value.toString();
            case "/":
                Float floatValue = (Float.parseFloat(operand2) / Float.parseFloat(operand1));
                return new Integer(floatValue.intValue()).toString();
            case "^":
                Double doubleValue = Math.pow(Double.parseDouble(operand2), Double.parseDouble(operand1));
                return new Double(doubleValue.intValue()).toString();
            default:
                throw new IllegalArgumentException("Invlaid operator : " +  token);

        }
    }

    private static String toPostFix( String infix ) {
        Stack stack = new Stack();
        StringBuilder postFixExp = new StringBuilder();

        String[] infixCharacters = infix.split(" ");
        int length = infixCharacters.length;
        for(int i=0; i < length; i++) {
            String op = infixCharacters[i];
            if(isOperand(op) ||  op.equals("(") || op.equals(")")) {
                //push if precedence is more than last operator or if stack is empty
                if(stack.isEmpty() || op.equals("(")) {
                    stack.push(op);
                } else if(op.equals(")")) {
                    while(!stack.peek().equals("(")){
                        String popped = stack.pop();
                        postFixExp.append(popped);
                        postFixExp.append(" ");
                    }
                    stack.pop();
                } else if(stack.peek().equals("(")) {
                    stack.push(op);
                } else if  (getPrecedence(stack.peek()) <  getPrecedence(op)) {
                    stack.push(op);
                } else if (getPrecedence(stack.peek()) >= getPrecedence(op)) {
                   while( !stack.peek().equals("(") && getPrecedence(stack.peek()) >= getPrecedence(op)) {
                       String popped = stack.pop();
                       postFixExp.append(popped);
                       postFixExp.append(" ");
                   }
                   stack.push(op);
                }

            } else {
                postFixExp.append(op);
                postFixExp.append(" ");
            }
        }
        return postFixExp.toString();
    }

    private static int getPrecedence( String operand ) {
        if(operand.equals("+") || operand.equals("-")) {
            return 1;
        } else if (operand.equals("*") || operand.equals("/")) {
            return 2;
        } else if (operand.equals("^")) {
            return 3;
        } else {
            throw new IllegalArgumentException("Unexpected operand : " + operand);
        }
    }

    private static boolean isOperand( String op ) {
        return operatorsList.contains(op);
    }
}

class Stack {
    int location = 0;
    String[] array = new String[100];

    public void push(String character) {
        System.out.println("Pushed :" + character);
        array[location] = character;
        location++;
    }

    public String pop() {
        if(location == 0) {
            throw new IllegalStateException("Empty Stack");
        }
        location--;
        System.out.println("Popped :" + array[location]);
        return array[location];
    }

    public String peek() {
        if(location == 0) {
            throw new IllegalStateException("Empty Stack");
        }
        int tempLocation = location - 1;
        return array[tempLocation];
    }

    public boolean isEmpty() {
        if(location == 0) {
            return true;
        }
        return false;
    }
}

