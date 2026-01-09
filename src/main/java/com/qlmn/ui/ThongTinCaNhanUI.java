package com.qlmn.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.qlmn.model.NguoiDung;

public class ThongTinCaNhanUI extends JFrame {
    private JLabel lblAvatar, lblTitle, lblUserDetail;
    private JButton btnSuaThongTin, btnDoiMatKhau, btnDangXuat;
    private NguoiDung nguoiDung;

    public ThongTinCaNhanUI(NguoiDung nd) {
        this.nguoiDung = nd;
        setTitle("HỒ SƠ CÁ NHÂN - " + nd.getVaiTro().toUpperCase());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Không thoát toàn hệ thống
        
        initModernUI();
        addEvents();
    }

    private void initModernUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250));

        // --- 1. LEFT SIDEBAR (Avatar & Actions) ---
        JPanel pnlLeft = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(280, 0));
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));

        // Giả lập Avatar
        lblAvatar = new JLabel("👤");
        lblAvatar.setFont(new Font("Segoe UI", Font.PLAIN, 100));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAvatar.setBorder(new EmptyBorder(40, 0, 10, 0));

        JLabel lblName = new JLabel(nguoiDung.getHoTen());
        lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRole = new JLabel(nguoiDung.getVaiTro());
        lblRole.setForeground(new Color(127, 140, 141));
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nút chức năng bên dưới Avatar
        btnSuaThongTin = createSidebarButton("Chỉnh sửa hồ sơ", new Color(52, 152, 219));
        btnDoiMatKhau = createSidebarButton("Đổi mật khẩu", new Color(46, 204, 113));
        btnDangXuat = createSidebarButton("Đăng xuất", new Color(231, 76, 60));

        pnlLeft.add(lblAvatar);
        pnlLeft.add(lblName);
        pnlLeft.add(lblRole);
        pnlLeft.add(Box.createVerticalStrut(30));
        pnlLeft.add(btnSuaThongTin);
        pnlLeft.add(Box.createVerticalStrut(10));
        pnlLeft.add(btnDoiMatKhau);
        pnlLeft.add(Box.createVerticalStrut(10));
        pnlLeft.add(btnDangXuat);

        add(pnlLeft, BorderLayout.WEST);

        // --- 2. RIGHT PANEL (Details Display) ---
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(new Color(245, 246, 250));
        pnlRight.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel pnlInfoCard = new JPanel(null);
        pnlInfoCard.setBackground(Color.WHITE);
        pnlInfoCard.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        lblTitle = new JLabel("CHI TIẾT TÀI KHOẢN");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setBounds(30, 20, 300, 30);
        lblTitle.setForeground(new Color(44, 62, 80));
        pnlInfoCard.add(lblTitle);

        // Hiển thị thông tin dạng dòng
        int startY = 80;
        addDetailRow(pnlInfoCard, "Tên đăng nhập:", nguoiDung.getTenDangNhap(), startY);
        addDetailRow(pnlInfoCard, "Họ và tên:", nguoiDung.getHoTen(), startY + 60);
        addDetailRow(pnlInfoCard, "Vai trò:", nguoiDung.getVaiTro(), startY + 120);
        addDetailRow(pnlInfoCard, "Trạng thái:", "Đang hoạt động", startY + 180);

        pnlRight.add(pnlInfoCard, BorderLayout.CENTER);
        add(pnlRight, BorderLayout.CENTER);
    }

    private void addDetailRow(JPanel panel, String label, String value, int y) {
        JLabel lblL = new JLabel(label);
        lblL.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblL.setBounds(30, y, 150, 20);
        lblL.setForeground(Color.GRAY);

        JLabel lblV = new JLabel(value);
        lblV.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblV.setBounds(30, y + 25, 350, 25);
        lblV.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));

        panel.add(lblL);
        panel.add(lblV);
    }

    private JButton createSidebarButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder());
        return btn;
    }

    private void addEvents() {
        // Đăng xuất
        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new DangNhapUI().setVisible(true);
                this.dispose();
            }
        });

        btnDoiMatKhau.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Tính năng đang được cập nhật!");
        });
        
        btnSuaThongTin.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Vui lòng liên hệ Admin để thay đổi thông tin cá nhân!");
        });
    }
}