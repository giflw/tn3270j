package com.itquasar.multiverse.tn3270j;

import com.j3270.base.J3270;
import com.j3270.base.ProcessPiper;
import com.j3270.base.Timeout;

import java.io.Console;

import static java.util.concurrent.TimeUnit.SECONDS;

public class J3270Shell {

    public static void main(String[] args) throws Exception {
        String executable = args[0];
        J3270 j3270 = new J3270(new ProcessPiper(executable));
        Console console = System.console();
        String line = null;
        System.out.println("TN3270j is ready to send actions to x3270");
        System.out.println("-----------------------------------------");
        while ((line = console.readLine()) != null) {
            System.out.println("Action: " + line);
            System.out.println(
                    j3270.perform(line, new Timeout(3, SECONDS))
            );
            if (line.toLowerCase().trim().startsWith("exit")) {
                break;
            }
        }
    }
}
