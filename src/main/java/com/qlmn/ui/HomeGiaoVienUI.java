package com.qlmn.ui;

import com.qlmn.util.SharedData;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeGiaoVienUI extends JFrame {
    private final String vaiTro = "GiaoVien";
    private JButton btnQuanLyLop, btnQuanLyHocSinh, btnThongTinCaNhan, btnDangXuat;

    public HomeGiaoVienUI() {
        setTitle("HỆ THỐNG MẦM NON - DÀNH CHO GIÁO VIÊN");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initModernUI();
        addEvents();
    }

    private void initModernUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250));

        // --- 1. Header: Banner chào mừng ---
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1));
        pnlHeader.setBackground(new Color(39, 174, 96)); // Màu xanh lá đậm chuyên nghiệp
        pnlHeader.setPreferredSize(new Dimension(800, 130));
        pnlHeader.setBorder(new EmptyBorder(20, 30, 10, 30));

        String tenGV = (SharedData.nguoiDungDangNhap != null) ? SharedData.nguoiDungDangNhap.getHoTen() : "Giáo Viên";
        JLabel lblWelcome = new JLabel("XIN CHÀO CÔ, " + tenGV.toUpperCase());
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        JLabel lblSub = new JLabel("Chúc cô một ngày làm việc tràn đầy năng lượng!");
        lblSub.setForeground(new Color(232, 245, 233));
        lblSub.setFont(new Font("Tahoma", Font.ITALIC, 14));

        pnlHeader.add(lblWelcome);
        pnlHeader.add(lblSub);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. Menu Cards: Chức năng chính ---
        JPanel pnlMenu = new JPanel(new GridLayout(1, 4, 15, 0)); // Sắp xếp hàng ngang
        pnlMenu.setBackground(new Color(245, 246, 250));
        pnlMenu.setBorder(new EmptyBorder(50, 40, 50, 40));

        btnQuanLyLop = createFeatureButton("LỚP CỦA TÔI", "🏫", new Color(46, 204, 113));
        btnQuanLyHocSinh = createFeatureButton("HỌC SINH", "🧒", new Color(52, 152, 219));
        btnThongTinCaNhan = createFeatureButton("HỒ SƠ", "📑", new Color(155, 89, 182));
        btnDangXuat = createFeatureButton("ĐĂNG XUẤT", "🚪", new Color(231, 76, 60));

        pnlMenu.add(btnQuanLyLop);
        pnlMenu.add(btnQuanLyHocSinh);
        pnlMenu.add(btnThongTinCaNhan);
        pnlMenu.add(btnDangXuat);

        add(pnlMenu, BorderLayout.CENTER);

        // --- 3. Footer ---
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBackground(Color.WHITE);
        JLabel lblFooter = new JLabel("Hệ thống Quản lý Mầm Non v1.0 - Góc làm việc của Giáo Viên");
        lblFooter.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblFooter.setForeground(Color.GRAY);
        pnlFooter.add(lblFooter);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JButton createFeatureButton(String text, String icon, Color color) {
        JButton btn = new JButton("<html><center><font size='7'>" + icon + "</font><br><br><b>" + text + "</b></center></html>");
        btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(44, 62, 80));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Viền thẻ Card
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createMatteBorder(0, 0, 4, 0, color)
        ));

        // Hiệu ứng Hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(248, 249, 249)); }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(Color.WHITE); }
        });

        return btn;
    }

    private void addEvents() {
        // Mở quản lý lớp học (Giáo viên chỉ được xem)
        btnQuanLyLop.addActionListener(e -> new LopHocUI(vaiTro).setVisible(true));

        // Mở quản lý học sinh (Giáo viên được thêm/sửa nhưng không được xóa)
        btnQuanLyHocSinh.addActionListener(e -> new HocSinhUI(vaiTro).setVisible(true));

        // Xem hồ sơ cá nhân
        btnThongTinCaNhan.addActionListener(e -> {
            if (SharedData.nguoiDungDangNhap != null) {
                new ThongTinCaNhanUI(SharedData.nguoiDungDangNhap).setVisible(true);
            }
        });

        // Đăng xuất
        btnDangXuat.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Cô có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.dispose();
                new DangNhapUI().setVisible(true);
            }
        });
    }
}