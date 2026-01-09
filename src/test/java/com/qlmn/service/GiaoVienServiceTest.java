package com.qlmn.service;

import com.qlmn.model.GiaoVien;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Kiem thu cho lop GiaoVienService
 * @author G
 */
public class GiaoVienServiceTest {

    @Test
    public void testLayDanhSachGiaoVien() {
        System.out.println("=== Kiem thu: Lay danh sach giao vien ===");
        GiaoVienService service = new GiaoVienService();
        List<GiaoVien> ds = service.getAll();
        assertNotNull(ds, "Danh sach giao vien khong duoc null");
        System.out.println("So giao vien hien co trong DB: " + ds.size());
    }

    @Test
    public void testThemGiaoVien() {
        System.out.println("=== Kiem thu: Them giao vien moi ===");
        GiaoVienService service = new GiaoVienService();
        String maGVNgauNhien = "GV" + UUID.randomUUID().toString().substring(0, 5);

        GiaoVien gvMoi = new GiaoVien(
                maGVNgauNhien,
                "Nguyen Van Test",
                "1985-05-10",
                "Nam",
                "0123456789",
                "giaovien" + maGVNgauNhien + "@example.com",
                "Dai hoc"
        );

        boolean ketQua = service.themGiaoVien(gvMoi);
        assertTrue(ketQua, "Them giao vien moi phai thanh cong");
        System.out.println("Da them giao vien voi ma: " + maGVNgauNhien);
    }

    @Test
    public void testSuaGiaoVien() {
        System.out.println("=== Kiem thu: Sua thong tin giao vien ===");
        GiaoVienService service = new GiaoVienService();

        // Tao giao vien moi truoc khi sua
        String maGV = "GV" + UUID.randomUUID().toString().substring(0, 5);
        GiaoVien gvMoi = new GiaoVien(
                maGV, "Nguyen Van B", "1986-03-12", "Nam",
                "0911222333", "nguyenvanb@example.com", "Cao dang"
        );
        service.themGiaoVien(gvMoi);

        // Thay doi thong tin
        gvMoi.setHoTen("Nguyen Van B Updated");
        gvMoi.setTrinhDo("Dai hoc");

        boolean ketQua = service.suaGiaoVien(gvMoi);
        assertTrue(ketQua, "Sua giao vien phai thanh cong");
        System.out.println("Da sua giao vien voi ma: " + maGV);
    }

    @Test
    public void testXoaGiaoVien() {
        System.out.println("=== Kiem thu: Xoa giao vien ===");
        GiaoVienService service = new GiaoVienService();

        // Tao truoc 1 giao vien de xoa
        String maGV = "GV" + UUID.randomUUID().toString().substring(0, 5);
        GiaoVien gvMoi = new GiaoVien(
                maGV, "Nguyen Van C", "1990-02-20", "Nam",
                "0988777666", "nguyenvanc@example.com", "Dai hoc"
        );
        service.themGiaoVien(gvMoi);

        // Thuc hien xoa
        boolean ketQua = service.xoaGiaoVien(maGV);
        assertTrue(ketQua, "Xoa giao vien phai thanh cong");
        System.out.println("Da xoa giao vien voi ma: " + maGV);
    }
}
