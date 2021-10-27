package com.simple.mcghealth.entities;


public class NguonDinhDuong {
    private int anh;
    private String lieuLuong;
    private String loiich;
    private String nguon;
    private String tieude;

    public NguonDinhDuong(String str, int i, String str2, String str3, String str4) {
        this.tieude = str;
        this.anh = i;
        this.nguon = str2;
        this.loiich = str3;
        this.lieuLuong = str4;
    }

    public String getTieude() {
        return this.tieude;
    }

    public void setTieude(String str) {
        this.tieude = str;
    }

    public int getAnh() {
        return this.anh;
    }

    public void setAnh(int i) {
        this.anh = i;
    }

    public String getNguon() {
        return this.nguon;
    }

    public void setNguon(String str) {
        this.nguon = str;
    }

    public String getLoiich() {
        return this.loiich;
    }

    public void setLoiich(String str) {
        this.loiich = str;
    }

    public String getLieuLuong() {
        return this.lieuLuong;
    }

    public void setLieuLuong(String str) {
        this.lieuLuong = str;
    }
}

