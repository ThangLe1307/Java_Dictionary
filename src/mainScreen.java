import javax.swing.*;
import java.awt.event.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class mainScreen extends JFrame {
    private boolean isEToV=true;

    public DefaultListModel<String> Emodel=null;
    public DefaultListModel<String> Vmodel=null;
    public DefaultListModel<String> currentModel=null;

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



        //danh sach tu tieng anh yeu thich
        Emodel=new DefaultListModel<>();
        //danh sach tu tieng viet yeu thich
        Vmodel=new DefaultListModel<>();


        //đọc từ file fav.txt để lấy danh sách các từ yêu thích
        readFile("Vfav.txt",Vmodel);
        readFile("Efav.txt",Emodel);

        Emodel=az(Emodel);
        Vmodel=az(Vmodel);
        currentModel=Emodel;

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
                String key=textField1.getText();
                searchInDict(key);
            }
        });
    }

    private DefaultListModel<String> az(DefaultListModel<String> model) {
        Object[] items = model.toArray();
        Arrays.sort(items);

        model.removeAllElements();
        for (Object item : items) {
            model.addElement((String)item);
        }

        return model;

    }

    private void searchInDict(String key) {
        String s;
        Word word=null;

        StringBuilder builder = new StringBuilder();

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



    private void readFile(String fileName,DefaultListModel<String> model)
    {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                model.addElement(line);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void showFav() {

        if (!isFavOpen)
        {
//            Emodel.clear();
//            Vmodel.clear();

            favoriteScreen fs=new favoriteScreen();
            fs.setContentPane(fs.panel);
            fs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            fs.setBounds(600,200,600,400);
            fs.setVisible(true);
            fs.setResizable(false);
            isFavOpen=true;

            //set loại danh sách yêu thích
            fs.wordsList.setModel(currentModel);

            fs.wordsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String s;
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2) {
                        s = fs.wordsList.getSelectedValue();
                        textField1.setText(s);
                        searchInDict(s);

                    }

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
        String s;
        s=textField1.getText();
        if (Emodel.contains(s)) {
            JOptionPane.showMessageDialog(null,"Tu da ton tai trong danh sach yeu thich");
        }
        else {
            currentModel.addElement(s);
            Emodel=az(Emodel);
            Vmodel=az(Vmodel);
            JOptionPane.showMessageDialog(null,"Them Tham Cong!");
        }

    }

    private void deleteFromDict() {

        Word word=null;
        Optional<Word> val=current.stream().filter(w -> w.getText().equalsIgnoreCase(textField1.getText())).findFirst();
        if (val.isPresent())
        {
            word=val.get();
            EVwords.remove(word);
            JOptionPane.showMessageDialog(null,"Da Xoa");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Tu khong ton tai");
        }
        EVwords.remove(textField1.getText());
    }
    private void addToDict() {

    }

    private void switchLang() {
        if (isEToV)
        {

            label1.setText("Từ điển Việt Anh");
            isEToV=false;
            current=VEwords;
            currentModel=Vmodel;
        }
        else
        {
            label1.setText("Từ điển Anh Việt");
            isEToV=true;
            current=EVwords;
            currentModel=Emodel;
        }
    }


    public static void writeWordsToXml(List<Word> words, String filePath) {
        try {
            // create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // use the factory to create a new DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // create a new document
            Document doc = builder.newDocument();

            // create the root element and add it to the document
            Element rootElement = doc.createElement("dictionary");
            doc.appendChild(rootElement);

            // add a new record element for each word in the list
            for (Word<String> word : words) {
                Element record = doc.createElement("record");
                rootElement.appendChild(record);

                // add the word element
                Element wordElement = doc.createElement("word");
                wordElement.appendChild(doc.createTextNode(word.getText()));
                record.appendChild(wordElement);

                // add the meaning element with each meaning as a separate list item
                Element meaningElement = doc.createElement("meaning");
                for (String meaning : word.getMeaning()) {
                    meaningElement.appendChild(doc.createTextNode(meaning));
                }
                record.appendChild(meaningElement);
            }

            // use a Transformer to output the document
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
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

    private static void writeWords(String fileName, DefaultListModel<String> model) throws IOException {

        FileWriter writer = new FileWriter(fileName);

        for (int i = 0; i < model.size(); i++) {
            writer.write(model.getElementAt(i).toString() + "\n");
        }

        writer.close();
    }

    public static void main(String[] args) {
        mainScreen ms =new mainScreen();
        ms.setContentPane(ms.mainScreen);
        ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ms.setBounds(600,200,600,400);
        ms.setVisible(true);
        ms.setResizable(false);


        ms.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    writeWordsToXml(ms.EVwords,"Anh_Viet.xml");
                    writeWordsToXml(ms.VEwords,"Viet_Anh.xml");





                    writeWords("Efav.txt",ms.Emodel);
                    writeWords("Vfav.txt",ms.Vmodel);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
