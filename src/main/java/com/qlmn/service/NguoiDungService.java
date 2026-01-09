package com.qlmn.service;

import com.qlmn.model.NguoiDung;
import com.qlmn.util.DBConnection;

import java.sql.*;

public class NguoiDungService {

    private String hoTen;

    /**
     * Kiểm tra đăng nhập.
     * Trả về đối tượng NguoiDung nếu đúng tài khoản/mật khẩu, ngược lại trả về null.
     */
    public NguoiDung kiemTraDangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String vaiTro = rs.getString("VaiTro");
                String hoTen = rs.getString("HoTen");
                return new NguoiDung(tenDangNhap, matKhau, vaiTro, hoTen);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
        }

        return null; // không tìm thấy
    }
}

