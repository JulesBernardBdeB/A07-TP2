package Vue;

import javax.swing.*;


/**
 * Cette classe est un JDialog qui a un text input pour l'utilisateur. Celui-ci entre son nom pour que son temps record
 * soit enregistr� dans highscores.txt.
 */
public class TextInputDialog extends JDialog {
    private JTextField textField;

    public TextInputDialog(JFrame parent) {
        super(parent, "Nouveau temps record!", true);
        textField = new JTextField(20);
        JButton okButton = new JButton("Entrer");
        okButton.addActionListener(e -> {
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Vous avez �tabli un nouveau record! F�licitation! \n Entrez votre nom: "));
        panel.add(textField);
        panel.add(okButton);
        getContentPane().add(panel);
        setLocationRelativeTo(parent);
    }

    public String getText() {
        return textField.getText();
    }
}
