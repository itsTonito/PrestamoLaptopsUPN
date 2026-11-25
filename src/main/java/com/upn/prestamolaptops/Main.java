package com.upn.prestamolaptops;

import com.upn.prestamolaptops.service.PrestamoService;
import com.upn.prestamolaptops.ui.PrestamoLaptopsFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrestamoService service = new PrestamoService();
                PrestamoLaptopsFrame frame = new PrestamoLaptopsFrame(service);
                frame.setVisible(true);
            }
        });
    }
}
