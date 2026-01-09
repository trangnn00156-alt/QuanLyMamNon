package com.qlmn.service;

import com.qlmn.model.NguoiDung;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NguoiDungServiceTest {

    private static NguoiDungService service;

    @BeforeAll
    public static void setUp() {
        service = new NguoiDungService();
    }

    @Test
    public void testKiemTraDangNhap() {
        System.out.println("Kiem thu dang nhap");
        String tenDangNhap = "admin";
        String matKhau = "123";

        NguoiDung nd = service.kiemTraDangNhap(tenDangNhap, matKhau);
        assertNotNull(nd, "Tai khoan khong ton tai hoac sai mat khau");
        System.out.println("Dang nhap thanh cong: " + nd.getTenDangNhap());
    }
}
