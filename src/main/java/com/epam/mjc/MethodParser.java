package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    // private String signatureString;

    /**
     * Parses string that represents a method signature and stores all it's members
     * into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {

        String[] parts = signatureString.split("[(]");
        if(parts.length!=2){
            throw new IllegalArgumentException();
        }

        MethodSignature methodSignature;
        String[] part0 = parts[0].split("\\s+");

        if (parts[1].endsWith(")")) {
            parts[1] = parts[1].substring(0,parts[1].length()-1);
        }
        List<MethodSignature.Argument> arguments = parseArguments(parts[1]);

        if(part0.length == 2) {
            methodSignature = new MethodSignature(part0[1], arguments);
            if (isAccessModifier(part0[0])) {
                throw new RuntimeException("There is return type in the provided signature");
            }
            else {
                methodSignature.setReturnType(part0[0]);
            }
        }
        else if(part0.length == 3) {
            methodSignature = new MethodSignature(part0[2], arguments);
            if (isAccessModifier(part0[0])) {
                methodSignature.setAccessModifier(part0[0]);
                methodSignature.setReturnType(part0[1]);
            }
            else {
                methodSignature.setAccessModifier(part0[1]);
                methodSignature.setReturnType(part0[0]);
            }
        }
        else {
            throw new RuntimeException("Unexpected method signature");
        }

        return methodSignature;
    }

    private boolean isAccessModifier(String s) {
        return s.equals("public") || s.equals("private") || s.equals("protected");
    }

    private List<MethodSignature.Argument> parseArguments(String argumentsString) {
        List<MethodSignature.Argument> arguments = new ArrayList<>();
        if (!argumentsString.isEmpty()) {
            String[] args = argumentsString.split(",");
            for (String arg : args) {
                String[] parts = arg.trim().split("\\s+");
                String argType = parts[0];
                String argName = parts[1];
                arguments.add(new MethodSignature.Argument(argType, argName));
            }
        }
        return arguments;
    }

    public static void main(String[] args) {
        MethodParser mp = new MethodParser();
        mp.parseFunction("Vector3 distort(int x, int y, int z, float magnitude)");
    }
}


