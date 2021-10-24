package com.simple.mcghealth.entities;

public class BaiViet {
    private int anh;
    private String tieude;
    private String url;

    public BaiViet(String str, int i, String str2) {
        this.tieude = str;
        this.anh = i;
        this.url = str2;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
