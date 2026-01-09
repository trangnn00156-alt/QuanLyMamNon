package com.qlmn.model;

import java.time.LocalDate;

public class HocSinh {
    private String maHocSinh;
    private String tenHocSinh;
    private int namSinh; // kiểu int cho cột NamSinh trong DB
    private String gioiTinh;
    private String maLop;
    private String maPH; // mã phụ huynh

    // ✅ Constructor chính
    public HocSinh(String maHocSinh, String tenHocSinh, int namSinh, String gioiTinh, String maLop, String maPH) {
        this.maHocSinh = maHocSinh;
        this.tenHocSinh = tenHocSinh;
        this.namSinh = namSinh;
        this.gioiTinh = gioiTinh;
        this.maLop = maLop;
        this.maPH = maPH;
    }

    public HocSinh() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

    // ✅ Getter
    public String getMaHocSinh() { return maHocSinh; }
    public String getTenHocSinh() { return tenHocSinh; }
    public int getNamSinh() { return namSinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getMaLop() { return maLop; }
    public String getMaPH() { return maPH; }

    // ✅ Tính tuổi (theo năm hiện tại)
    public int tinhTuoi() {
        int namHienTai = LocalDate.now().getYear();
        return namHienTai - namSinh;
    }

    // ✅ Hiển thị dễ đọc
    @Override
    public String toString() {
        return maHocSinh + " - " + tenHocSinh +
                " | Năm sinh: " + namSinh +
                " | Giới tính: " + gioiTinh +
                " | Lớp: " + maLop +
                " | Phụ huynh: " + maPH;
    }

    public void setMaHocSinh(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
