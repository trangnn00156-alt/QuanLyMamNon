package com.qlmn.model;

import javax.swing.Icon;

public class NguoiDung {
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private String hoTen;

    public NguoiDung() {
    }

    /**
     *
     * @param tenDangNhap
     * @param matKhau
     * @param vaiTro
     */
    public NguoiDung(String tenDangNhap, String matKhau, String vaiTro, String hoTen) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.hoTen   = hoTen;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
    public String getHoTen() {
        return hoTen;
    }


    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
     public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }


    @Override
    public String toString() {
        return "NguoiDung{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                '}';
    }

  

   
}
