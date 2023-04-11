package br.bkraujo.utils;

public abstract class ThreadUtils {

    public static void sleep(int millis) {
        try { Thread.sleep(millis); } catch (Exception ignored){}
    }

}
