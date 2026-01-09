package com.qlmn.service;

import com.qlmn.model.GiaoVien;
import com.qlmn.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienService {

    // LẤY TẤT CẢ GIÁO VIÊN
    public List<GiaoVien> getAll() {
        List<GiaoVien> list = new ArrayList<>();
        String sql = "SELECT * FROM GiaoVien";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GiaoVien gv = new GiaoVien(
                        rs.getString("MaGV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"), // kiểu String
                        rs.getString("GioiTinh"),
                        rs.getString("SDT"),
                        rs.getString("Email"),
                        rs.getString("TrinhDo")
                );
                list.add(gv);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách giáo viên: " + e.getMessage());
        }

        return list;
    }

    // ======== THÊM GIÁO VIÊN ========
    public boolean themGiaoVien(GiaoVien gv) {
        String sql = "INSERT INTO GiaoVien (MaGV, HoTen, NgaySinh, GioiTinh, SDT, Email, TrinhDo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, gv.getMaGV());
            ps.setString(2, gv.getHoTen());
            ps.setString(3, gv.getNgaySinh());
            ps.setString(4, gv.getGioiTinh());
            ps.setString(5, gv.getSdt());
            ps.setString(6, gv.getEmail());
            ps.setString(7, gv.getTrinhDo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm giáo viên: " + e.getMessage());
            return false;
        }
    }

    // ======== CẬP NHẬT GIÁO VIÊN ========
    public boolean suaGiaoVien(GiaoVien gv) {
        String sql = "UPDATE GiaoVien SET HoTen=?, NgaySinh=?, GioiTinh=?, SDT=?, Email=?, TrinhDo=? WHERE MaGV=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, gv.getHoTen());
            ps.setString(2, gv.getNgaySinh());
            ps.setString(3, gv.getGioiTinh());
            ps.setString(4, gv.getSdt());
            ps.setString(5, gv.getEmail());
            ps.setString(6, gv.getTrinhDo());
            ps.setString(7, gv.getMaGV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi sửa giáo viên: " + e.getMessage());
            return false;
        }
    }

    // ======== XÓA GIÁO VIÊN ========
    public boolean xoaGiaoVien(String maGV) {
        String sql = "DELETE FROM GiaoVien WHERE MaGV=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maGV);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa giáo viên: " + e.getMessage());
            return false;
        }
    }
    
    public List<GiaoVien> findByName(String ten) {
    List<GiaoVien> ds = new ArrayList<>();
    // Sử dụng LIKE và % để tìm kiếm gần đúng (gõ "An" ra "Thanh An", "An Nhiên")
    String sql = "SELECT * FROM GiaoVien WHERE HoTen LIKE ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, "%" + ten + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            ds.add(new GiaoVien(
                rs.getString("MaGV"), 
                rs.getString("HoTen"),
                rs.getString("NgaySinh"), 
                rs.getString("GioiTinh"),
                rs.getString("SDT"), 
                rs.getString("Email"), 
                rs.getString("TrinhDo")
            ));
        }
    } catch (SQLException e) {
    }
    return ds;
}
}
