package com.qlmn.service;

import com.qlmn.model.TaiKhoan;
import com.qlmn.util.DBConnection;

import java.sql.*;

public class TaiKhoanService {

    /**
     * Kiểm tra đăng nhập từ SQLite
     * @param tenDangNhap tên đăng nhập
     * @param matKhau mật khẩu
     * @return đối tượng TaiKhoan nếu hợp lệ, null nếu sai
     */
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("VaiTro")
                );
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
        }
        return null;
    }
}
