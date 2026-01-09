package com.qlmn.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.qlmn.model.NguoiDung;
import com.qlmn.service.NguoiDungService;
import com.qlmn.util.SharedData;
import java.awt.*;

public class DangNhapUI extends JFrame {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;

    public DangNhapUI() {
        // --- 1. Cấu hình cơ bản ---
        setTitle("HỆ THỐNG QUẢN LÝ MẦM NON");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- 2. Panel chính chứa toàn bộ (Dùng BorderLayout) ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        setContentPane(mainPanel);

        // --- 3. Panel phía trên (Header/Logo giả lập) ---
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1));
        pnlHeader.setBackground(new Color(70, 130, 180)); // Màu xanh SteelBlue
        pnlHeader.setPreferredSize(new Dimension(450, 150));

        JLabel lblIcon = new JLabel("🔐", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        lblIcon.setForeground(Color.WHITE);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);

        pnlHeader.add(lblIcon);
        pnlHeader.add(lblTitle);
        mainPanel.add(pnlHeader, BorderLayout.NORTH);

        // --- 4. Panel ở giữa (Chứa các ô nhập liệu) ---
        JPanel pnlBody = new JPanel(null); // Dùng null layout để tùy chỉnh vị trí chính xác
        pnlBody.setBackground(Color.WHITE);
        pnlBody.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setBounds(50, 30, 150, 25);
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlBody.add(lblUser);

        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setBounds(50, 60, 330, 40);
        txtTenDangNhap.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtTenDangNhap.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180)));
        pnlBody.add(txtTenDangNhap);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setBounds(50, 120, 150, 25);
        lblPass.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnlBody.add(lblPass);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(50, 150, 330, 40);
        txtMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtMatKhau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180)));
        pnlBody.add(txtMatKhau);

        // --- 5. Nút đăng nhập ---
        btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setBounds(50, 230, 330, 50);
        btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDangNhap.setBackground(new Color(70, 130, 180));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlBody.add(btnDangNhap);

        mainPanel.add(pnlBody, BorderLayout.CENTER);

        // --- 6. Xử lý sự kiện ---
        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        
        // Nhấn Enter để đăng nhập luôn
        txtMatKhau.addActionListener(e -> xuLyDangNhap());
    }

    private void xuLyDangNhap() {
        String user = txtTenDangNhap.getText().trim();
        String pass = new String(txtMatKhau.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NguoiDungService service = new NguoiDungService();
        NguoiDung nd = service.kiemTraDangNhap(user, pass);
if (nd != null) {
        SharedData.nguoiDungDangNhap = nd;
        JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        // Chuyển hướng theo vai trò
        if ("Admin".equalsIgnoreCase(nd.getVaiTro())) {
            new MainAdminUI(nd.getVaiTro()).setVisible(true);
        } 
        else if ("PhuHuynh".equalsIgnoreCase(nd.getVaiTro())) {
            new HomePhuHuynhUI().setVisible(true);
        } 
        else if ("GiaoVien".equalsIgnoreCase(nd.getVaiTro())) {
            new HomeGiaoVienUI().setVisible(true);
        }

        // Đóng cửa sổ đăng nhập sau khi đã mở trang Home tương ứng
        this.dispose(); 
    } else {
        JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }}
    public static void main(String[] args) {
        // Cài đặt giao diện giống hệ điều hành (Windows/macOS)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new DangNhapUI().setVisible(true);
        });
    }
}