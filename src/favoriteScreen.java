import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JList;
import javax.swing.DefaultListModel;

public class favoriteScreen extends JFrame {
    public JList<String> wordsList ;

    public JPanel mainPannel;
    public JPanel panel;
    private JLabel label;

    public favoriteScreen() {



    wordsList= new JList<String>(new DefaultListModel<>());


    // Tạo panel chứa JList và button

    label=new JLabel();
    panel = new JPanel(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(wordsList);


    panel.add(label,BorderLayout.CENTER);
    label.setAlignmentX(CENTER_ALIGNMENT);

   panel.add(scrollPane, BorderLayout.CENTER);



    wordsList.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
        }
    });

}
}
