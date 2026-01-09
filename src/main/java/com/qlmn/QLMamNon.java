package com.qlmn;

import com.formdev.flatlaf.FlatLightLaf;
import com.qlmn.ui.DangNhapUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class QLMamNon {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   UNG DUNG QUAN LY TRUONG MAM NON - KHOI CHAY");
        System.out.println("=================================================");

        try {
            // Cài đặt giao diện hiện đại FlatLaf
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Không thể cài giao diện FlatLaf!");
        }

        // Khởi chạy giao diện Đăng nhập trước
        SwingUtilities.invokeLater(() -> {
            new DangNhapUI().setVisible(true);
        });
    }
}
