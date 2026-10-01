package com.example.paszport;

public class PassportValidator {

    public boolean isDataComplete(String imie, String nazwisko) {
        if (imie == null || nazwisko == null) return false;
        return !imie.trim().isEmpty() && !nazwisko.trim().isEmpty();
    }

    public String buildMessage(String imie, String nazwisko, String kolorOczu) {
        return imie + " " + nazwisko + " kolor oczu " + kolorOczu;
    }

    public String getPhotoFileName(String numer) {
        return numer + "-zdjecie.jpg";
    }

    public String getFingerprintFileName(String numer) {
        return numer + "-odcisk.jpg";
    }
}
