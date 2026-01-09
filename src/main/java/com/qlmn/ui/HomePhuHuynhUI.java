package com.qlmn.ui;

import com.qlmn.util.SharedData;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePhuHuynhUI extends JFrame {
    private JButton btnHocSinh, btnHocPhi, btnHoSo, btnDangXuat;

    public HomePhuHuynhUI() {
        setTitle("HỆ THỐNG MẦM NON - DÀNH CHO PHỤ HUYNH");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        addEvents();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250));

        // Header
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(52, 152, 219));
        pnlHeader.setPreferredSize(new Dimension(0, 120));
        JLabel lblMsg = new JLabel("Cổng Thông Tin Phụ Huynh");
        lblMsg.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblMsg.setForeground(Color.WHITE);
        pnlHeader.add(lblMsg);
        add(pnlHeader, BorderLayout.NORTH);

        // Menu Buttons
        JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        pnlMenu.setOpaque(false);

        btnHocSinh = createMenuButton("CON CỦA TÔI", "🧒");
        btnHocPhi = createMenuButton("HỌC PHÍ", "💰");
        btnHoSo = createMenuButton("HỒ SƠ CỦA TÔI", "👤"); // NÚT MỚI BỔ SUNG
        btnDangXuat = createMenuButton("ĐĂNG XUẤT", "🚪");

        pnlMenu.add(btnHocSinh);
        pnlMenu.add(btnHocPhi);
        pnlMenu.add(btnHoSo);
        pnlMenu.add(btnDangXuat);
        add(pnlMenu, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, String icon) {
        JButton btn = new JButton("<html><center><font size='6'>"+icon+"</font><br>"+text+"</center></html>");
        btn.setPreferredSize(new Dimension(150, 150));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        return btn;
    }

    private void addEvents() {
        btnHocSinh.addActionListener(e -> new HocSinhUI("PhuHuynh").setVisible(true));
        
        btnHoSo.addActionListener(e -> {
            if (SharedData.nguoiDungDangNhap != null) {
                new ThongTinCaNhanUI(SharedData.nguoiDungDangNhap).setVisible(true);
            }
        });

        btnDangXuat.addActionListener(e -> {
            this.dispose();
            new DangNhapUI().setVisible(true);
        });
    }
}