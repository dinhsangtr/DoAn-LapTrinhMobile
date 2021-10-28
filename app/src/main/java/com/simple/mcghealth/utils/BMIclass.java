package com.simple.mcghealth.utils;

public class BMIclass {
    public static String getCmt(int bmi) {
        if (bmi < 18.5) {
            return "Bạn gầy";
        } else if (18.5 <= bmi && bmi < 25) {
            return "Bạn cân đối";
        } else if (bmi >= 25) {
            return "Bạn thừa cân";
        }
        return "";
    }
}