package com.qlmn.service;

import com.qlmn.model.HocSinh;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class HocSinhServiceTest {

    private HocSinhService service;

    @BeforeEach
    void setUp() {
        service = new HocSinhService();
        // Giả lập admin đăng nhập để vượt qua kiểm tra quyền (SharedData)
        com.qlmn.model.NguoiDung admin = new com.qlmn.model.NguoiDung();
        admin.setTenDangNhap("admin");
        admin.setVaiTro("Admin");
        com.qlmn.util.SharedData.nguoiDungDangNhap = admin;
    }

    // --- KIỂM THỬ HỘP TRẮNG (Khớp với logic 3-5 tuổi, năm hiện tại 2026) ---

    @Test
    public void testThemHocSinh_Path1_SaiTuoi() {
        System.out.println("=== Kiem thu Path 1: Tuoi khong hop le (2 tuoi) ===");
        // Năm 2026 - 2024 = 2 tuổi (Sai rule 3-5) -> Phải trả về false
        HocSinh hs = new HocSinh("TEST_FAIL", "Bé Nhỏ", 2024, "Nam", "L01", "PH01");
        
        boolean ketQua = service.themHocSinh(hs);
        assertFalse(ketQua, "He thong phai tu choi hoc sinh 2 tuoi");
    }

    @Test
    public void testThemHocSinh_Path2_ThanhCong() {
        System.out.println("=== Kiem thu Path 2: Thanh cong (4 tuoi) ===");
        String maHS = "HS" + UUID.randomUUID().toString().substring(0, 5);
        // Năm 2026 - 2022 = 4 tuổi (Đúng rule 3-5) -> Phải trả về true
        HocSinh hs = new HocSinh(maHS, "Nguyễn Văn Hợp Lệ", 2022, "Nam", "L01", "PH01");
        
        boolean ketQua = service.themHocSinh(hs);
        assertTrue(ketQua, "He thong phai them thanh cong hoc sinh 4 tuoi");
    }

    @Test
    public void testCapNhatHocSinh() {
        System.out.println("=== Kiem thu: Cap nhat hoc sinh ===");
        String maHS = "HS" + UUID.randomUUID().toString().substring(0, 5);
        // Tạo mới bé 5 tuổi (2021) để có dữ liệu trong DB trước khi sửa
        HocSinh hsMoi = new HocSinh(maHS, "Học Sinh Cũ", 2021, "Nữ", "L02", "PH02");
        service.themHocSinh(hsMoi);

        // Tiến hành cập nhật tên
        HocSinh hsSua = new HocSinh(maHS, "Học Sinh Đã Sửa", 2021, "Nữ", "L02", "PH02");
        boolean ketQua = service.capNhatHocSinh(hsSua);
        assertTrue(ketQua, "He thong phai cap nhat thanh cong");
    }

    @Test
    public void testXoaHocSinh() {
        System.out.println("=== Kiem thu: Xoa hoc sinh ===");
        String maHS = "HS" + UUID.randomUUID().toString().substring(0, 5);
        // Tạo mới bé 3 tuổi (2023) trước khi xóa
        HocSinh hsMoi = new HocSinh(maHS, "Học Sinh Xóa", 2023, "Nam", "L01", "PH01");
        service.themHocSinh(hsMoi);

        boolean ketQua = service.xoaHocSinh(maHS);
        assertTrue(ketQua, "He thong phai xoa thanh cong hoc sinh vua them");
    }
}