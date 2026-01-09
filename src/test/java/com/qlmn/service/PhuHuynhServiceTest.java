package com.qlmn.service;

import com.qlmn.model.PhuHuynh;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Kiem thu lop PhuHuynhService
 * CRUD: Them, Lay DS, Sua, Xoa
 */
public class PhuHuynhServiceTest {

    private PhuHuynhService service;

    public PhuHuynhServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("=== BAT DAU CHAY TEST PHU HUYNH ===");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== KET THUC TOAN BO TEST PHU HUYNH ===");
    }

    @BeforeEach
    public void setUp() {
        service = new PhuHuynhService();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("------");
    }

    private String taoMaNgauNhien() {
        return "PH" + UUID.randomUUID().toString().substring(0, 6);
    }

    @Test
    public void testGetAllPhuHuynh() {
        System.out.println("Kiem thu ham getAllPhuHuynh()");
        List<PhuHuynh> ds = service.getAllPhuHuynh();
        assertNotNull(ds, "Danh sach phu huynh khong duoc null");
        System.out.println("So phu huynh hien co trong DB: " + ds.size());
    }

    @Test
    public void testThemPhuHuynh() {
        System.out.println("Kiem thu ham themPhuHuynh()");
        String maNgauNhien = taoMaNgauNhien();

        PhuHuynh phMoi = new PhuHuynh(
            maNgauNhien,
            "Nguyen Van Test",
            "Nam",
            "0909123456",
            "test" + maNgauNhien + "@gmail.com",
            "123 Duong Test, Quan 1"
        );

        boolean ketQua = service.themPhuHuynh(phMoi);
        assertTrue(ketQua, "Them phu huynh moi phai thanh cong");
        System.out.println("Da them phu huynh voi ma: " + maNgauNhien);
    }

    @Test
    public void testSuaPhuHuynh() {
        System.out.println("Kiem thu ham suaPhuHuynh()");

        // Tao truoc 1 phu huynh de test sua
        String maNgauNhien = taoMaNgauNhien();
        PhuHuynh ph = new PhuHuynh(
            maNgauNhien,
            "Nguyen Van Sua",
            "Nam",
            "0909333444",
            "suaph" + maNgauNhien + "@gmail.com",
            "Dia chi cu"
        );
        boolean daThem = service.themPhuHuynh(ph);
        assertTrue(daThem, "Them phu huynh truoc khi sua phai thanh cong");

        // Thuc hien sua
        ph.setHoTen("Nguyen Van Sua Moi");
        ph.setDiaChi("123 Duong Moi, Quan 2");
        boolean ketQua = service.suaPhuHuynh(ph);
        assertTrue(ketQua, "Sua phu huynh phai thanh cong");
        System.out.println("Da sua phu huynh voi ma: " + maNgauNhien);
    }

    @Test
    public void testXoaPhuHuynh() {
        System.out.println("Kiem thu ham xoaPhuHuynh()");

        // Tao truoc 1 phu huynh de test xoa
        String maNgauNhien = taoMaNgauNhien();
        PhuHuynh ph = new PhuHuynh(
            maNgauNhien,
            "Nguyen Van Xoa",
            "Nam",
            "0911222333",
            "xoaph" + maNgauNhien + "@gmail.com",
            "123 Test Xoa"
        );
        boolean daThem = service.themPhuHuynh(ph);
        assertTrue(daThem, "Them phu huynh truoc khi xoa phai thanh cong");

        // Thuc hien xoa
        boolean ketQua = service.xoaPhuHuynh(maNgauNhien);
        assertTrue(ketQua, "Xoa phu huynh phai thanh cong");
        System.out.println("Da xoa phu huynh voi ma: " + maNgauNhien);
    }
}
