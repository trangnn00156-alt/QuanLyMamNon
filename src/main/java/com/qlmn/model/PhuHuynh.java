package com.qlmn.model;

public class PhuHuynh {
    private String maPH;
    private String hoTen;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String diaChi;

    // 1. Constructor đầy đủ tham số
    public PhuHuynh(String maPH, String hoTen, String gioiTinh, String sdt, String email, String diaChi) {
        this.maPH = maPH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
    }

    // 2. Constructor mặc định (BẮT BUỘC PHẢI CÓ để Service chạy được)
    public PhuHuynh() {
    }

    // 3. Getter & Setter chuẩn
    public String getMaPH() { return maPH; }
    public void setMaPH(String maPH) { this.maPH = maPH; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    @Override
    public String toString() {
        return maPH + " - " + hoTen + " | GT: " + gioiTinh +
               " | SDT: " + sdt + " | Email: " + email +
               " | Dia chi: " + diaChi;
    }
}