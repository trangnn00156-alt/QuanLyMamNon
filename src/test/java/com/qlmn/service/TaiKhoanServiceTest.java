/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.qlmn.service;

import com.qlmn.model.TaiKhoan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LENOVO
 */
public class TaiKhoanServiceTest {
    
    public TaiKhoanServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of dangNhap method, of class TaiKhoanService.
     */
    @Test
    public void testDangNhap() {
    System.out.println("dangNhap");
    TaiKhoanService service = new TaiKhoanService();
    TaiKhoan tk = service.dangNhap("admin", "123");
    assertNotNull(tk, "Đăng nhập thất bại hoặc tài khoản không tồn tại");
}
}