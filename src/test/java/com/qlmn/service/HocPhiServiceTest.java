package com.qlmn.service;

import com.qlmn.model.HocPhi;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Kiem thu cho lop HocPhiService
 * @author G
 */
public class HocPhiServiceTest {

    @Test
    public void testLayDanhSachHocPhi() {
        System.out.println("=== Kiem thu: Lay danh sach hoc phi ===");
        HocPhiService service = new HocPhiService();
        List<HocPhi> ds = service.getAllHocPhi();
        assertNotNull(ds, "Danh sach hoc phi khong duoc null");
        System.out.println("So hoc phi hien co trong DB: " + ds.size());
    }

    @Test
    public void testThemHocPhi() {
        System.out.println("=== Kiem thu: Them hoc phi moi ===");
        HocPhiService service = new HocPhiService();

        String maHPNgauNhien = "HP" + UUID.randomUUID().toString().substring(0, 6);
        HocPhi hpMoi = new HocPhi();
        hpMoi.setMaHP(maHPNgauNhien);
        hpMoi.setMaLop("L01");
        hpMoi.setSoTien(600000);
        hpMoi.setHanDong(new Date());
        hpMoi.setMoTa("Hoc phi thang 11 nam 2025");

        boolean ketQua = service.themHocPhi(hpMoi);
        assertTrue(ketQua, "Them hoc phi phai thanh cong");
        System.out.println("Da them hoc phi voi ma: " + maHPNgauNhien);
    }

    @Test
    public void testCapNhatHocPhi() {
        System.out.println("=== Kiem thu: Cap nhat hoc phi ===");
        HocPhiService service = new HocPhiService();

        // Tao truoc 1 ban ghi moi
        String maHP = "HP" + UUID.randomUUID().toString().substring(0, 6);
        HocPhi hpMoi = new HocPhi();
        hpMoi.setMaHP(maHP);
        hpMoi.setMaLop("L02");
        hpMoi.setSoTien(700000);
        hpMoi.setHanDong(new Date());
        hpMoi.setMoTa("Hoc phi ban dau");
        service.themHocPhi(hpMoi);

        // Cap nhat thong tin
        hpMoi.setSoTien(800000);
        hpMoi.setMoTa("Hoc phi da cap nhat");

        boolean ketQua = service.capNhatHocPhi(hpMoi);
        assertTrue(ketQua, "Cap nhat hoc phi phai thanh cong");
        System.out.println("Da cap nhat hoc phi voi ma: " + maHP);
    }

    @Test
    public void testXoaHocPhi() {
        System.out.println("=== Kiem thu: Xoa hoc phi ===");
        HocPhiService service = new HocPhiService();

        // Tao truoc 1 ban ghi de xoa
        String maHP = "HP" + UUID.randomUUID().toString().substring(0, 6);
        HocPhi hpMoi = new HocPhi();
        hpMoi.setMaHP(maHP);
        hpMoi.setMaLop("L03");
        hpMoi.setSoTien(900000);
        hpMoi.setHanDong(new Date());
        hpMoi.setMoTa("Hoc phi de xoa");
        service.themHocPhi(hpMoi);

        // Thuc hien xoa
        boolean ketQua = service.xoaHocPhi(maHP);
        assertTrue(ketQua, "Xoa hoc phi phai thanh cong");
        System.out.println("Da xoa hoc phi voi ma: " + maHP);
    }
}
