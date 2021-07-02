package hr.fer.oprpp1.lsystems.impl;

import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.lsystems.impl.commands.*;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;

import java.awt.*;
import java.util.Arrays;

/**
 * Class that represents implementation of {@link LSystemBuilder} that defines how Lindenmayern systems will be drawn.
 *
 * @author Mihael Rodek
 */
public class LSystemBuilderImpl implements LSystemBuilder {

    /**
     * Dictionary with registered productions
     */
    Dictionary productions;

    /**
     * Dictionary with registered actions(commands)
     */
    Dictionary actions;

    /**
     * Length of the unit
     */
    private double unitLength = 0.1;

    /**
     * Length used to scale
     */
    private double unitLengthDegreeScaler = 1;

    /**
     * Origin Vector2D
     */
    private Vector2D origin = new Vector2D(0, 0);

    /**
     * Angle to move for
     */
    private double angle = 0;

    /**
     * Current axiom
     */
    private String axiom = "";

    /**
     * Default constructor.
     */

    public LSystemBuilderImpl() {
        this.actions = new Dictionary();
        this.productions = new Dictionary();
    }

    @Override
    public LSystem build() {

        return new LSystem() {

            @Override
            public void draw(int i, Painter painter) {
                double newShiftLength = unitLength * Math.pow(unitLengthDegreeScaler, i);
                TurtleState turtleState = new TurtleState(origin, new Vector2D(1, 0).rotated(angle).normalized(),
                        Color.BLACK, newShiftLength);
                Context context = new Context();
                context.pushState(turtleState);
                char[] depth = generate(i).toCharArray();
                for (char c : depth) {
                    Command exec = (Command) LSystemBuilderImpl.this.actions.get(c);
                    if (exec == null) continue;
                    exec.execute(context, painter);
                }
            }

            @Override
            public String generate(int i) {
                String gen = axiom;
                StringBuilder sb = new StringBuilder();
                for (; i > 0; i--) {
                    for (int k = 0; k < gen.length(); k++) {
                        char help = gen.charAt(k);
                        String production = (String) LSystemBuilderImpl.this.productions.get(help);
                        if (production == null)
                            sb.append(gen.charAt(k));
                        else
                            sb.append(production);
                    }
                    gen = sb.toString();
                    sb.setLength(0);
                }
                return gen;
            }
        };
    }

    @Override
    public LSystemBuilder setUnitLength(double v) {
        unitLength = v;
        return this;
    }

    @Override
    public LSystemBuilder setOrigin(double v, double v1) {
        origin = new Vector2D(v, v1);
        return this;
    }

    @Override
    public LSystemBuilder setAngle(double v) {
        angle = v;
        return this;
    }

    @Override
    public LSystemBuilder setAxiom(String s) {
        axiom = s;
        return this;
    }

    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        productions.put(c, s);
        return this;
    }

    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double v) {
        unitLength = v;
        return this;
    }


    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        String[] splitter = s.split("\\s+");
        try {
            switch (splitter[0]) {
                case "draw" -> actions.put(c, new DrawCommand(Double.parseDouble(splitter[1])));
                case "skip" -> actions.put(c, new SkipCommand(Double.parseDouble(splitter[1])));
                case "scale" -> actions.put(c, new ScaleCommand(Double.parseDouble(splitter[1])));
                case "rotate" -> actions.put(c, new RotateCommand(Double.parseDouble(splitter[1]) * Math.PI / 180));
                case "push" -> actions.put(c, new PushCommand());
                case "pop" -> actions.put(c, new PopCommand());
                case "color" -> actions.put(c, new ColorCommand(Color.decode("#" + splitter[1])));
                default -> throw new RuntimeException("Nepoznata akcija!");
            }
        } catch (Exception ex) {
            System.err.println("Couldn't parse given command");
            System.exit(1);
        }
        return this;
    }


    @Override
    public LSystemBuilder configureFromText(String[] strings) {
        for (String line : strings) {
            if (!line.equals("")) {

                String[] splitter = line.split("\\s+");
                try {
                    switch (splitter[0]) {
                        case "origin" -> {
                            checkLength(splitter.length, 3);
                            origin = new Vector2D(Double.parseDouble(splitter[1]), Double.parseDouble(splitter[2]));
                        }
                        case "angle" -> {
                            checkLength(splitter.length, 2);
                            this.angle = Double.parseDouble(splitter[1]);
                        }
                        case "unitLength" -> {
                            checkLength(splitter.length, 2);
                            this.unitLength = Double.parseDouble(splitter[1]);
                        }
                        case "unitLengthDegreeScaler" -> {
                            if (splitter.length != 2 && splitter.length != 3 && splitter.length != 4)
                                throw new IllegalArgumentException();

                            String[] unitDegree = Arrays.copyOfRange(splitter, 1, splitter.length);
                            String[] unitDegreeSplitter = String.join("", unitDegree).split("/");

                            if (unitDegreeSplitter.length != 1 && unitDegreeSplitter.length != 2)
                                throw new IllegalArgumentException();

                            double secondNumber;
                            if (unitDegreeSplitter.length == 2)
                                secondNumber = Double.parseDouble(unitDegreeSplitter[1]);
                            else secondNumber = 1;

                            this.unitLengthDegreeScaler = Double.parseDouble(unitDegreeSplitter[0]) / secondNumber;
                        }
                        case "command" -> {
                            if (splitter.length != 3 && splitter.length != 4)
                                throw new IllegalArgumentException();
                            checkLength(splitter[1].length(), 1);

                            char symbol = splitter[1].charAt(0);
                            String command = splitter[2];
                            if (splitter.length == 4)
                                command += " " + splitter[3];
                            this.registerCommand(symbol, command);
                        }
                        case "axiom" -> {
                            checkLength(splitter.length, 2);
                            this.axiom = splitter[1];
                        }
                        case "production" -> {
                            checkLength(splitter.length, 3);
                            checkLength(splitter[1].length(), 1);
                            this.registerProduction(splitter[1].charAt(0), splitter[2]);
                        }
                        default -> throw new RuntimeException("Illegal directive");
                    }
                } catch (RuntimeException ex) {
                    if (ex.getMessage() != null) System.err.println("Couldn't parse given command");
                    else System.err.println(ex.getMessage());
                    System.exit(1);
                }
            }
        }
        return this;
    }

    /**
     * Method used to check if length of input is different than compared number
     *
     * @param len             length of input to check
     * @param compareToNumber number to compare with
     * @throws IllegalArgumentException if len is not equal to compareToNumber
     */

    public void checkLength(int len, int compareToNumber) {
        if (len != compareToNumber)
            throw new IllegalArgumentException("Illegal number of arguments");
    }


}
