package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Class used for demonstration of {@link ObjectStack} stack. It demonstrates
 * how to calculates postfix annotation using stack. Program accepts a string as
 * command line arguments and writes the result of given expression in console.
 *
 * @author Mihael Rodek
 */
public class StackDemo {

    /**
     * Main method that starts the program.
     * <p>
     * Program gets command line arguments,that represents postfix annotation that needs to be
     * calculated, if there is more than a one argument, program
     * stops with appropriate message
     */
    public static void main(String[] args) {

        if (args.length == 1) {
            String[] commandLineArguments = args[0].split("\\s+");
            calculate(commandLineArguments, new ObjectStack());
        } else
            System.out.println("Invalid number of arguments.");
    }

    /**
     * Method that calculates the given expression. Method goes through each element
     * and checks if it is number or an operator. If it is number it pushes it on
     * stack. If it is an operator it performs it. Any unacceptable state ends
     * program with appropriate message.
     *
     * @param elements Array of elements that were entered through command line,
     *                 array is divided by whitespaces
     * @param stack    Stack used to push and pop elements while calculating the
     *                 expression
     * @throws NumberFormatException if element it not a number nor an operator
     * @throws EmptyStackException   if stack is empty and operation couldn't be
     *                               performed
     */

    public static void calculate(String[] elements, ObjectStack stack) {

        for (String el : elements) {
            try {
                if (!checkOperator(el)) {
                    try {
                        int number = Integer.parseInt(el);
                        stack.push(number);
                    } catch (NumberFormatException ex) {
                        System.out.println("Sorry but '" + el + "' is not a number. Check the input sequence.");
                    }
                } else {
                    int second = (int) stack.pop();
                    int first = (int) stack.pop();
                    switch (el) {
                        case "+" -> stack.push(first + second);
                        case "-" -> stack.push(first - second);
                        case "*" -> stack.push(first * second);
                        case "%" -> stack.push(first % second);
                        case "/" -> {
                            if (second == 0) {
                                System.out.println("Cannot divide with zero.");
                                System.exit(1);
                            } else
                                stack.push(first / second);
                        }
                    }

                }

            } catch (EmptyStackException ex) {
                System.out.println("Cannot calculate expression.");
                System.exit(1);
            }
        }

        if (stack.size() != 1)
            System.out.println("Error, only result should be on stack, but stack size is greater than 1.");
        else
            System.out.println("Expression evaluates to " + stack.pop() + ".");
    }

    /**
     * Method checks if given string is an operator. <br>
     * Acceptable operands are +,-,*,/ and %
     *
     * @param el String that needs to be checked
     * @return true if it is operator, otherwise false
     */

    public static boolean checkOperator(String el) {
        return el.equals("*") || el.equals("%") || el.equals("+") || el.equals("-") || el.equals("/");
    }
}
