package utils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.Cleaner;
import java.nio.charset.StandardCharsets;

public class Logger {

    private static Logger instance;
    private final JFrame frame;
    private final JTextArea txtArea;
    private Logger() {
        frame = new JFrame("AOC Terminal");
        txtArea = new JTextArea(24,80);
        txtArea.setEditable(false);
        txtArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtArea.setForeground(Color.WHITE);
        txtArea.setBackground(Color.BLACK);

        JScrollPane sp = new JScrollPane(txtArea);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.getContentPane().add(sp);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }

    public void log(final String msg) {
        txtArea.append("[LOG] ");
        txtArea.append(msg + "\n");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());
    }

    public void close() {
        frame.dispose();
    }
}
