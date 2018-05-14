package com.csjohnlennon.desafioandroid.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Data {

    public static String formataDataBR(String data) {

        String dataFormatada = "";

        try {
            DateFormat inputFormatter1  = new SimpleDateFormat("yyyy-MM-dd");
            Date date1                  = inputFormatter1.parse(data);

            DateFormat outputFormatter1 = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada              = outputFormatter1.format(date1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataFormatada;

    }


}