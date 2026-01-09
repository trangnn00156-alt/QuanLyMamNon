package com.qlmn.ui;

import com.qlmn.util.SharedData;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainAdminUI extends JFrame {
    private final String vaiTro;
    private JLabel lblWelcome;
    private JButton btnHocSinh, btnGiaoVien, btnLopHoc, btnHocPhi, btnPhuHuynh, btnDangXuat;

    public MainAdminUI(String vaiTro) {
        this.vaiTro = vaiTro;

        setTitle("HỆ THỐNG MẦM NON - QUẢN TRỊ VIÊN");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initModernUI();
        addEvents();
    }

    private void initModernUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250)); // Màu nền xám nhạt hiện đại

        // ===== 1. Header: Banner chào mừng =====
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1));
        pnlHeader.setBackground(new Color(41, 128, 185)); // Màu xanh thương hiệu
        pnlHeader.setPreferredSize(new Dimension(850, 120));
        pnlHeader.setBorder(new EmptyBorder(10, 20, 10, 20));

        String tenAdmin = (SharedData.nguoiDungDangNhap != null) ? SharedData.nguoiDungDangNhap.getHoTen() : "ADMINISTRATOR";
        lblWelcome = new JLabel("XIN CHÀO, " + tenAdmin.toUpperCase());
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 22));
        
        JLabel lblSubTitle = new JLabel("Hệ thống quản lý thông tin trường mầm non");
        lblSubTitle.setForeground(new Color(236, 240, 241));
        lblSubTitle.setFont(new Font("Tahoma", Font.ITALIC, 14));

        pnlHeader.add(lblWelcome);
        pnlHeader.add(lblSubTitle);
        add(pnlHeader, BorderLayout.NORTH);

        // ===== 2. Menu Cards (Center) =====
        JPanel pnlMenu = new JPanel(new GridLayout(2, 3, 20, 20));
        pnlMenu.setBackground(new Color(245, 246, 250));
        pnlMenu.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Tạo các nút theo phong cách Card
        btnHocSinh = createMenuButton("HỌC SINH", "👤", new Color(52, 152, 219));
        btnGiaoVien = createMenuButton("GIÁO VIÊN", "👩‍🏫", new Color(46, 204, 113));
        btnLopHoc = createMenuButton("LỚP HỌC", "🏫", new Color(155, 89, 182));
        btnHocPhi = createMenuButton("HỌC PHÍ", "💰", new Color(241, 196, 15));
        btnPhuHuynh = createMenuButton("PHỤ HUYNH", "👪", new Color(26, 188, 156));
        btnDangXuat = createMenuButton("ĐĂNG XUẤT", "🚪", new Color(231, 76, 60));

        pnlMenu.add(btnHocSinh);
        pnlMenu.add(btnGiaoVien);
        pnlMenu.add(btnLopHoc);
        pnlMenu.add(btnHocPhi);
        pnlMenu.add(btnPhuHuynh);
        pnlMenu.add(btnDangXuat);

        add(pnlMenu, BorderLayout.CENTER);

        // ===== 3. Footer =====
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFooter.setBackground(Color.WHITE);
        JLabel lblFooter = new JLabel("Phiên bản 1.0 - Đồ án Kiểm thử Phần mềm © 2024    ");
        lblFooter.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblFooter.setForeground(Color.GRAY);
        pnlFooter.add(lblFooter);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    // Hàm tiện ích tạo nút bấm kiểu Card hiện đại
    private JButton createMenuButton(String text, String icon, Color color) {
        JButton btn = new JButton("<html><center><font size='6'>" + icon + "</font><br><br><b>" + text + "</b></center></html>");
        btn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(color); // Chữ cùng màu với icon
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Tạo viền màu của Card
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createMatteBorder(0, 0, 5, 0, color) // Thanh màu dưới chân nút
        ));

        // Hiệu ứng Hover (Khi di chuột vào nút)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 240, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }

    private void addEvents() {
        btnHocSinh.addActionListener(e -> new HocSinhUI(vaiTro).setVisible(true));
        btnGiaoVien.addActionListener(e -> new GiaoVienUI(vaiTro).setVisible(true));
        btnLopHoc.addActionListener(e -> new LopHocUI(vaiTro).setVisible(true));
        btnHocPhi.addActionListener(e -> new HocPhiUI(vaiTro).setVisible(true));        btnPhuHuynh.addActionListener(e -> new PhuHuynhUI(vaiTro).setVisible(true));

        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất khỏi hệ thống?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new DangNhapUI().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MainAdminUI("Admin").setVisible(true));
    }
}