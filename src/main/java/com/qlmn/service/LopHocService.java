package com.qlmn.service;

import com.qlmn.model.LopHoc;
import com.qlmn.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LopHocService {

    // Lấy tất cả lớp học
    public List<LopHoc> getAllLopHoc() {
        List<LopHoc> ds = new ArrayList<>();
        String sql = "SELECT MaLop, TenLop, Khoi, SiSo, MaGV FROM LopHoc";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LopHoc lop;
                lop = new LopHoc(
                        rs.getString("MaLop"),
                        rs.getString("TenLop"),
                        rs.getString("Khoi"),
                        rs.getInt("SiSo"),
                        rs.getString("MaGV")
                );
                ds.add(lop);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách lớp học: " + e.getMessage());
        }

        return ds;
    }

    // Thêm lớp học
    public boolean themLopHoc(LopHoc lop) {
        String sql = "INSERT INTO LopHoc(MaLop, TenLop, Khoi, SiSo, MaGV) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lop.getMaLop());
            ps.setString(2, lop.getTenLop());
            ps.setString(3, lop.getKhoi());
            ps.setInt(4, lop.getSiSo());
            ps.setString(5, lop.getMaGV());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm lớp học: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật lớp học
    public boolean capNhatLopHoc(LopHoc lop) {
        String sql = "UPDATE LopHoc SET TenLop = ?, Khoi = ?, SiSo = ?, MaGV = ? WHERE MaLop = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lop.getTenLop());
            ps.setString(2, lop.getKhoi());
            ps.setInt(3, lop.getSiSo());
            ps.setString(4, lop.getMaGV());
            ps.setString(5, lop.getMaLop());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật lớp học: " + e.getMessage());
            return false;
        }
    }

    // Xóa lớp học
    public boolean xoaLopHoc(String maLop) {
        String sql = "DELETE FROM LopHoc WHERE MaLop = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maLop);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa lớp học: " + e.getMessage());
            return false;
        }
    }
  public List<LopHoc> findByMaLop(String maLop) {
    List<LopHoc> ds = new ArrayList<>();
    String sql = "SELECT * FROM LopHoc WHERE MaLop LIKE ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, "%" + maLop + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            ds.add(new LopHoc(
                rs.getString("MaLop"), 
                rs.getString("TenLop"),
                rs.getString("Khoi"), 
                rs.getInt("SiSo"), 
                rs.getString("MaGV")
            ));
        }
    } catch (SQLException e) {
    }
    return ds;
}

}
