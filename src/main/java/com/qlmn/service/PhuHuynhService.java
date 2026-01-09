package com.qlmn.service;

import com.qlmn.model.PhuHuynh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.qlmn.util.DBConnection; // Đảm bảo có class này


public class PhuHuynhService {

    public List<PhuHuynh> getAllPhuHuynh() {
        List<PhuHuynh> ds = new ArrayList<>();
        String sql = "SELECT * FROM PhuHuynh";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PhuHuynh ph = new PhuHuynh(
                        rs.getString("MaPH"),
                        rs.getString("HoTen"),
                        rs.getString("GioiTinh"),
                        rs.getString("SDT"),
                        rs.getString("Email"), "123 Duong Test, Quan 1");
                ds.add(ph);
            }

        } catch (SQLException e) {
            System.out.println("Loi khi lay danh sach phu huynh: " + e.getMessage());
        }

        return ds;
    }
    public boolean suaPhuHuynh(PhuHuynh ph) {
    String sql = "UPDATE PhuHuynh SET HoTen=?, GioiTinh=?, SDT=?, Email=?, DiaChi=? WHERE MaPH=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, ph.getHoTen());
        ps.setString(2, ph.getGioiTinh());
        ps.setString(3, ph.getSdt());
        ps.setString(4, ph.getEmail());
        ps.setString(5, ph.getDiaChi());
        ps.setString(6, ph.getMaPH());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Loi khi sua phu huynh: " + e.getMessage());
        return false;
    }
}

public boolean xoaPhuHuynh(String maPH) {
    String sql = "DELETE FROM PhuHuynh WHERE MaPH=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maPH);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Loi khi xoa phu huynh: " + e.getMessage());
        return false;
    }
}


    public boolean themPhuHuynh(PhuHuynh ph) {
        String sql = "INSERT INTO PhuHuynh (MaPH, HoTen, GioiTinh, SDT, Email, DiaChi) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ph.getMaPH());
            ps.setString(2, ph.getHoTen());
            ps.setString(3, ph.getGioiTinh());
            ps.setString(4, ph.getSdt());
            ps.setString(5, ph.getEmail());
            ps.setString(6, ph.getDiaChi());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Loi khi them phu huynh: " + e.getMessage());
            return false;
        }
    }
    
    public List<PhuHuynh> findPhuHuynh(String keyword) {
    List<PhuHuynh> ds = new ArrayList<>();
    // Tìm theo Mã PH hoặc Họ Tên
    String sql = "SELECT * FROM PhuHuynh WHERE MaPH LIKE ? OR HoTen LIKE ?";

    try (Connection conn = com.qlmn.util.DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        String findVal = "%" + keyword + "%";
        ps.setString(1, findVal);
        ps.setString(2, findVal);
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            PhuHuynh ph = new PhuHuynh();
            ph.setMaPH(rs.getString("MaPH"));
            ph.setHoTen(rs.getString("HoTen"));
            ph.setGioiTinh(rs.getString("GioiTinh"));
            ph.setSdt(rs.getString("Sdt"));
            ph.setEmail(rs.getString("Email"));
            ph.setDiaChi(rs.getString("DiaChi"));
            ds.add(ph);
        }
    } catch (Exception e) {
        System.out.println("Loi findPhuHuynh: " + e.getMessage());
    }
    return ds;
}
}
