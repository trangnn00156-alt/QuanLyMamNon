package com.qlmn.model;

public class LopHoc {
    private String maLop;
    private String tenLop;
    private String khoi;
    private int siSo;
    private String maGV;

    public LopHoc() {}

    public LopHoc(String maLop, String tenLop, String khoi, int siSo, String maGV) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.khoi = khoi;
        this.siSo = siSo;
        this.maGV = maGV;
    }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }

    public String getKhoi() { return khoi; }
    public void setKhoi(String khoi) { this.khoi = khoi; }

    public int getSiSo() { return siSo; }
    public void setSiSo(int siSoToiDa) { this.siSo = siSo; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
}
