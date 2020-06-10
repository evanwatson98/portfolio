package com.example.keepin_It_Fresh;

import java.io.*;

public class GlobalVars {
    private static GlobalVars instance = null;

    public String userid;
    public String username;

    public static GlobalVars getInstance() {
        if(instance == null) {
            instance = new GlobalVars();
        }
        return instance;
    }
}
