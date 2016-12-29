package com.alan.example;

import static java.lang.System.out;

/**
 * @author Alan Dávila<br>
 *         e-mail ustargab@gmail.com<br>
 *         27 dic. 2016 20:17
 */
public class ImportTargetListener {
    public void handleMessage(ImportTarget importTarget) {
        out.println(importTarget.toString());
    }
}
