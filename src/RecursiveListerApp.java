import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveListerApp extends JFrame {
    private JButton startButton, quitButton;
    private JTextArea textArea;
    private JLabel titleLabel;

    public RecursiveListerApp() {
        setTitle("Recursive Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        startButton = new JButton("Start");
        quitButton = new JButton("Quit");
        textArea = new JTextArea(20, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        titleLabel = new JLabel("Recursive File Lister");

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileChooser();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Directories", "dir");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            listFiles(selectedDirectory);
        }
    }
        private void listFiles(File directory) {
            textArea.setText("");

            if (directory.isDirectory()) {
                appendToTextArea("Listing files in: " + directory.getAbsolutePath());
                listFilesRecursive(directory);
            } else {
                appendToTextArea("Selected path is not a directory");
            }
        }

        private void listFilesRecursive(File directory) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        listFilesRecursive(file);
                    } else {
                        appendToTextArea(file.getAbsolutePath());
                    }
                }
            }
        }

        private void appendToTextArea(String text) {
            textArea.append(text + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new RecursiveListerApp().setVisible(true);
                }
            });

    }
}