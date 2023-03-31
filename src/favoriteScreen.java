import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JList;
import javax.swing.DefaultListModel;

public class favoriteScreen extends JFrame {
    public JList<String> wordsList ;

    public DefaultListModel<String> model;
    public JPanel mainPannel;
    public JPanel panel;
    private JLabel label;

    public favoriteScreen() {

    model=new DefaultListModel<>();


    model.addElement("Element 1");
    model.addElement("Element 2");
    model.addElement("Element 3");








    wordsList= new JList<String>(model);

    model.addElement("abcg");


    // Tạo panel chứa JList và button

    label=new JLabel();
    label.setText("Favorite Words List");


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
