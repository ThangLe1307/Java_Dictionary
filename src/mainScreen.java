import javax.swing.*;
import java.awt.event.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class mainScreen extends JFrame {
    private boolean isEToV=true;

    private boolean isFavOpen=false;

    public List<String> favList=new ArrayList<String>();

    public List <Word> current;

    public List<Word> EVwords;
    public List<Word> VEwords;


    private JPanel mainScreen;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextArea text;
    private JButton addButton;
    private JButton deleteButton;
    private JButton addToFavoriteButton;
    private JButton searchButton;
    private JButton favoriteButton;
    private JButton historyButton;
    private JButton switchLanguageButton;
    private JPanel topPannel;
    private JPanel midPannel;
    private JPanel botPannel;







    public mainScreen() {

        EVwords= read("Anh_Viet.xml");
        VEwords=read("Viet_Anh.xml");
        current=EVwords;

        switchLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchLang();

            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToDict();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFromDict();
            }
        });
        addToFavoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToFav();
            }
        });
        favoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFav();
            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistory();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchInDict();
            }
        });
    }

    private void searchInDict() {
        String s,key;
        Word word=null;

        StringBuilder builder = new StringBuilder();

        key= textField1.getText();

        Optional<Word> val=current.stream().filter(w -> w.getText().equalsIgnoreCase(key)).findFirst();

        if (val.isPresent())
        {
            word=val.get();
            System.out.println(word.text);
            for (int i=0;i<word.meaning.size();i++)
            {
                builder.append(word.meaning.get(i)).append('\n');
            }

            s=builder.toString();
            text.setText(s);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Tu khong ton tai");
        }






    }

    private void showHistory() {






    }

    private void showFav() {

        if (!isFavOpen)
        {
            favoriteScreen fs=new favoriteScreen();
            fs.setContentPane(fs.panel);

            fs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fs.setBounds(600,200,600,400);
            fs.setVisible(true);
            fs.setResizable(false);
            isFavOpen=true;


            //đọc từ file fav.txt để lấy danh sách các từ yêu thích
            try {
                FileReader fr = new FileReader("fav.txt");
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    fs.model.addElement(line);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            fs.wordsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String s;
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2)
                        s=fs.wordsList.getSelectedValue();





                }
            });






            fs.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                @Override
                public void windowClosing(WindowEvent e) {
                    isFavOpen=false;

                }

                @Override
                public void windowClosed(WindowEvent e) {

                }

                @Override
                public void windowIconified(WindowEvent e) {

                }

                @Override
                public void windowDeiconified(WindowEvent e) {

                }

                @Override
                public void windowActivated(WindowEvent e) {

                }

                @Override
                public void windowDeactivated(WindowEvent e) {

                }
            });






        }
    }

    private void addToFav() {




    }

    private void deleteFromDict() {
    }

    private void addToDict() {
    }

    private void switchLang() {
        if (isEToV)
        {
            label1.setText("Từ điển Việt Anh");
            isEToV=false;
            current=VEwords;
            System.out.println(current.get(0).text);

        }
        else
        {
            label1.setText("Từ điển Anh Việt");
            isEToV=true;
            current=EVwords;
            System.out.println(current.get(0).text);
        }
    }


    public static List<Word> read(String fileName) {
        List<Word> words = new ArrayList<Word>();

        try {
            File inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String text = eElement.getElementsByTagName("word").item(0).getTextContent();
                    String meaningText = eElement.getElementsByTagName("meaning").item(0).getTextContent();
                    String[] meanings = meaningText.split("\n");

                    List<String> meaningList = new ArrayList<>();
                    for (String meaning : meanings) {
                        meaning = meaning.trim();
                        if (!meaning.isEmpty()) {
                            meaningList.add(meaning);
                        }
                    }

                    Word word = new Word(text, meaningList);
                    words.add(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return words;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        mainScreen ms =new mainScreen();
        ms.setContentPane(ms.mainScreen);
        ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ms.setBounds(600,200,600,400);
        ms.setVisible(true);
        ms.setResizable(false);

















    }
}
