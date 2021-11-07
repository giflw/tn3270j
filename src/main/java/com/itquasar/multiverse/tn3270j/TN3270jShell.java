package com.itquasar.multiverse.tn3270j;

import bsh.Interpreter;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

public class TN3270jShell {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: tn3270jShell IMPLEMENTATION_ID S3270_EXECUTABLE");
            System.exit(1);
        }
        String implementationId = args[0];
        List<String> executable = Arrays.asList(args[1]);
        TN3270j tn3270j = TN3270jFactory.create(implementationId, new ProcessBuilder(executable), WaitMode.Seconds);
        Interpreter interpreter = new Interpreter();

        interpreter.set("tn3270j", tn3270j);

        for (WaitMode mode : WaitMode.values()) {
            interpreter.set(mode.name(), mode);
        }

        Console console = System.console();
        System.out.println("TN3270j Shell started:");
        String prompt = "TN3270j> ";
        System.out.print(prompt);
        String line = null;
        boolean debug = false;
        while ((line = console.readLine()) != null) {
            if (line.equals("exit")) {
                tn3270j.close();
                break;
            }
            if (line.equals("debug")) {
                debug = !debug;
                System.out.println("DEBUG is " + (debug ? "enabled" : "disabled") + '.');
                System.out.print(prompt);
                continue;
            }
            if (!line.trim().isEmpty()) {
                if (!line.startsWith("tn3270j.")) {
                    line = "tn3270j." + line;
                }
                Object eval = null;
                try {
                    eval = interpreter.eval(line);
                } catch (Throwable e) {
                    System.out.println(e.getMessage());
                    if (debug) {
                        e.printStackTrace();
                    }
                }
                if (eval != null) {
                    System.out.println(eval);
                }
                if (debug && tn3270j.started() && !line.contains("screenshot")) {
                    System.out.println(tn3270j.screenshot());
                }
            }
            System.out.print(prompt);
        }
    }
}
