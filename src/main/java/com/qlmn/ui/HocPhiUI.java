package com.qlmn.ui;

import com.qlmn.model.HocSinh;
import com.qlmn.service.HocSinhService;
import com.qlmn.util.DBConnection;
import com.qlmn.util.SharedData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class HocPhiUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai;
    private String vaiTro;

    public HocPhiUI(String vaiTro) {
        this.vaiTro = vaiTro;
        setTitle("QUẢN LÝ HÓA ĐƠN HỌC PHÍ - Vai trò: " + vaiTro);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        phanQuyenChucNang();
        napDuLieu();
    }

    private void initUI() {
        // Cấu trúc cột dựa trên bảng HoaDon và HocPhi bạn cung cấp
        String[] cols = {"Mã HĐ", "Mã Học Sinh", "Mã Học Phí", "Ngày Thanh Toán", "Số Tiền", "Phụ Phí", "Ghi Chú"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel nút bấm
        JPanel pnlControl = new JPanel();
        btnThem = new JButton("Thêm Hóa Đơn");
        btnSua = new JButton("Sửa Hóa Đơn");
        btnXoa = new JButton("Xóa Hóa Đơn");
        btnTaiLai = new JButton("Làm Mới");

        pnlControl.add(btnThem);
        pnlControl.add(btnSua);
        pnlControl.add(btnXoa);
        pnlControl.add(btnTaiLai);
        add(pnlControl, BorderLayout.SOUTH);

        // Sự kiện
        btnTaiLai.addActionListener(e -> napDuLieu());
        btnXoa.addActionListener(e -> xoaHoaDon());
        // Các sự kiện Them/Sua bạn có thể code tương tự như HocSinhUI
    }

    private void phanQuyenChucNang() {
        if ("PhuHuynh".equalsIgnoreCase(vaiTro)) {
            // Phụ huynh chỉ xem, không được thấy nút thao tác
            btnThem.setVisible(false);
            btnSua.setVisible(false);
            btnXoa.setVisible(false);
        } else if ("GiaoVien".equalsIgnoreCase(vaiTro)) {
            // Giáo viên có thể thêm/sửa nhưng không được xóa
            btnThem.setVisible(true);
            btnSua.setVisible(true);
            btnXoa.setEnabled(false);
        } else {
            // Admin: Toàn quyền (Mặc định visible/enabled)
        }
    }

    private void napDuLieu() {
    model.setRowCount(0);
    
    // 1. Kiểm tra session đăng nhập
    if (SharedData.nguoiDungDangNhap == null) {
        System.err.println("LOG LỖI: SharedData.nguoiDungDangNhap đang bị NULL!");
        return;
    }

    String userId = SharedData.nguoiDungDangNhap.getTenDangNhap();
    String role = SharedData.nguoiDungDangNhap.getVaiTro();
    
    System.out.println("=== BẮT ĐẦU KIỂM TRA LOG HỌC PHÍ ===");
    System.out.println("Người đang dùng: " + userId);
    System.out.println("Vai trò: " + role);

    // 2. Chuẩn bị câu lệnh SQL
    String sql = "";
    if ("Admin".equalsIgnoreCase(role)) {
        sql = "SELECT * FROM HoaDon";
    } else if ("PhuHuynh".equalsIgnoreCase(role)) {
        // Lệnh JOIN theo cấu trúc ảnh bạn gửi
        sql = "SELECT h.* FROM HoaDon h " +
              "INNER JOIN HocSinh s ON h.MaHS = s.MaHS " +
              "WHERE UPPER(s.MaPH) = UPPER(?)";
    } else {
        sql = "SELECT h.* FROM HoaDon h " +
              "INNER JOIN HocSinh s ON h.MaHS = s.MaHS " +
              "WHERE s.MaLop = (SELECT MaLop FROM LopHoc WHERE MaGV = ?)";
    }

    System.out.println("SQL thực thi: " + sql);

    try (Connection conn = DBConnection.getConnection()) {
        if (conn == null) {
            System.err.println("LOG LỖI: Không thể kết nối Database (Connection NULL)!");
            return;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (!"Admin".equalsIgnoreCase(role)) {
                ps.setString(1, userId);
                System.out.println("Tham số truyền vào SQL (userId): " + userId);
            }

            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                Object[] row = {
                    rs.getString("MaHD"),
                    rs.getString("MaHS"),
                    rs.getString("MaHP"),
                    rs.getDate("NgayThanhToan"),
                    String.format("%,.0f", rs.getDouble("SoTienThanhToan")),
                    String.format("%,.0f", rs.getDouble("PhuPhi")),
                    rs.getString("GhiChu")
                };
                model.addRow(row);
            }
            
            System.out.println("Kết quả: Tìm thấy " + count + " dòng dữ liệu.");
            if (count == 0) {
                System.out.println("GỢI Ý: Kiểm tra bảng HocSinh xem cột MaPH có giá trị '" + userId + "' chưa?");
            }

        }
    } catch (SQLException e) {
        System.err.println("LOG LỖI SQL: " + e.getMessage());
        e.printStackTrace();
    }
    System.out.println("=== KẾT THÚC KIỂM TRA LOG ===");
}
    private void xoaHoaDon() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        
        String maHD = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn " + maHD + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM HoaDon WHERE MaHD = ?")) {
                ps.setString(1, maHD);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn!");
                    napDuLieu();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage());
            }
        }
    }
    private void themHoaDon() {
    String maHD = JOptionPane.showInputDialog(this, "Nhập Mã Hóa Đơn mới:");
    if (maHD == null || maHD.trim().isEmpty()) return;
    
   
    String sql = "INSERT INTO HoaDon (MaHD, MaHS, MaHP, NgayThanhToan, SoTienThanhToan, PhuPhi, GhiChu) VALUES (?, 'HS001', 'HP01', GETDATE(), 500000, 0, 'Admin thêm mới')";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maHD);
        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
            napDuLieu();
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi thêm: " + ex.getMessage());
    }
}

private void suaHoaDon() {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!");
        return;
    }
    String maHD = model.getValueAt(row, 0).toString();
    String ghiChuMoi = JOptionPane.showInputDialog(this, "Cập nhật ghi chú cho " + maHD + ":");
    
    String sql = "UPDATE HoaDon SET GhiChu = ? WHERE MaHD = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, ghiChuMoi);
        ps.setString(2, maHD);
        if (ps.executeUpdate() > 0) {
            napDuLieu();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
}