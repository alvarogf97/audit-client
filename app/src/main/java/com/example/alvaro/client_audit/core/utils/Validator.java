package com.example.alvaro.client_audit.core.utils;

import java.util.regex.Pattern;

public class Validator {

    private static Pattern ValidIpAddressRegex = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    public static boolean validate(String name, String ip, int port){
        boolean res = false;

        if(name != "" && ip != ""){
            return ValidIpAddressRegex.matcher(ip).matches();
        }
        return res;
    }
}
