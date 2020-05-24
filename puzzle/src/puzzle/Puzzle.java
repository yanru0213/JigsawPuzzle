package puzzle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;

public class Puzzle extends JFrame {

    JPanel menuP;
    JPanel gameP;
    JPanel gameoverP;
    String types[] = {"Number", "Picture"};
    String levels[] = {"Easy(3x3)", "Normal(5x5)", "Hard(7x7)"};
    int levelRow[] = {3, 5, 7};
    int moveTimes = 0;
    String Time = "00:00";
    String data[] = {" ", " ", " "};
    JButton[] button;
    int order[][];
    String orderColor[][];
    GridLayout gridLayout;
    int Row;
    int number;
    String color[] = {"#f37152", "#eab34b", "#7d7d1e", "#15a4b4"};
    String[] imgType = {"easy", "normal", "difficult"};
    int imageNum;
    JLabel information2;
    Timer timer;

    public Puzzle() {
        super("Klotski");
        setResizable(false); //不可改變大小
        setSize(400, 300);
        setLocationRelativeTo(null); //視窗置中
        ImageIcon icon = new ImageIcon(("img/pageicon.png"));
        setIconImage(icon.getImage()); //設定icon
//menu畫面
        menuP = new JPanel();
        menuP.setBackground(Color.decode("#e9e5d0"));
        add(menuP);
        menuP.setLayout(new GridLayout(4, 1));
        Panel p1 = new Panel(new FlowLayout(FlowLayout.CENTER));
        menuP.add(p1);
        JLabel title = new JLabel("<html><h1>Klotski Game</h1></html>");
        p1.add(title);
        Panel p2 = new Panel(new FlowLayout(FlowLayout.CENTER));
        menuP.add(p2);
        JLabel nameLabel = new JLabel("Enter Your Name：");
        p2.add(nameLabel);
        JTextField nameText = new JTextField(10);
        p2.add(nameText);
        Panel p3 = new Panel(new FlowLayout(FlowLayout.CENTER));
        menuP.add(p3);
        JLabel typeLabel = new JLabel("Type：");
        p3.add(typeLabel);
        JComboBox type = new JComboBox(types);
        p3.add(type);
        type.setBackground(Color.decode("#15a4b4"));
        type.setForeground(Color.decode("#3f3534"));
        JLabel blank = new JLabel("   ");
        p3.add(blank);
        JLabel levelLabel = new JLabel("Level：");
        p3.add(levelLabel);
        JComboBox level = new JComboBox(levels);
        p3.add(level);
        level.setBackground(Color.decode("#eab34b"));
        level.setForeground(Color.decode("#3f3534"));
        Panel p4 = new Panel(new FlowLayout(FlowLayout.CENTER));
        menuP.add(p4);
        JButton start = new JButton("START");
        p4.add(start);
        start.setBackground(Color.decode("#f37152"));
        start.setBorderPainted(false);
        start.setForeground(Color.decode("#3f3534"));
//menu頁面的按鈕
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nameText.getText().equals("")) {
                    data[0] = "player1";
                } else {
                    data[0] = nameText.getText();
                }
                data[1] = types[type.getSelectedIndex()];
                data[2] = levels[level.getSelectedIndex()];
                moveTimes = 0;
                remove(menuP);
                //game畫面
                gameP = new JPanel();
                gameP.setBackground(Color.decode("#e9e5d0"));
                BorderLayout borderLayout = new BorderLayout();
                gameP.setLayout(borderLayout);
                setSize(600, 600);
                setLocationRelativeTo(null);
                add(gameP);
                Panel p5 = new Panel(new GridLayout(2, 1));
                gameP.add(p5, BorderLayout.NORTH);
                Panel p51 = new Panel(new FlowLayout(FlowLayout.CENTER));
                p5.add(p51);
                JLabel title2 = new JLabel("<html><h1>Klotski Game</h1></html>");
                p51.add(title2);
                Panel p52 = new Panel(new GridLayout(2, 1));
                p5.add(p52);
                Panel p521 = new Panel(new FlowLayout(FlowLayout.CENTER));
                p52.add(p521);
                JLabel information = new JLabel("Player：" + data[0] + "     Type：" + data[1] + "     Level：" + data[2]);
                p521.add(information);
                Panel p522 = new Panel(new FlowLayout(FlowLayout.CENTER));
                p52.add(p522);
                information2 = new JLabel("Moves：" + moveTimes + "     Time：" + Time);
                p522.add(information2);
                JPanel klotski;
//images puzzle
                if (type.getSelectedIndex() == 1) {
                    Row = levelRow[level.getSelectedIndex()];
                    number = Row * Row;
                    gridLayout = new GridLayout(Row, Row);
                    order = new int[Row][Row];
                    imageNum = level.getSelectedIndex();
                    for (int i = 0; i < Row; i++) {
                        for (int j = 0; j < Row; j++) {
                            order[i][j] = 1000;
                        }
                    }
                    klotski = new JPanel(gridLayout);
                    klotski.setSize(400, 400);
                    button = new JButton[number];
                    for (int i = 0; i < number - 1; i++) {
                        while (true) {
                            int randomNum;
                            randomNum = (int) (Math.random() * (number - 1));
                            boolean comfirm = false;
                            for (int a = 0; a < Row; a++) {
                                for (int b = 0; b < Row; b++) {
                                    if (randomNum == order[a][b]) {
                                        comfirm = true;
                                    }
                                }
                            }
                            if (comfirm) {
                                comfirm = false;
                            } else {
                                button[randomNum] = new JButton("");
                                button[randomNum].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        String emptyName = button[number - 1].getName();
                                        String empty[] = emptyName.split("");
                                        int emptyR = Integer.valueOf(empty[0]);
                                        int emptyC = Integer.valueOf(empty[1]);
                                        char emptyRow = emptyName.charAt(0);//空白按鈕行
                                        char emptyCol = emptyName.charAt(1);//空白按鈕列
                                        JButton clickButton = (JButton) e.getSource();
                                        String clickName = clickButton.getName();
                                        String click[] = clickName.split("");
                                        int clickR = Integer.valueOf(click[0]);
                                        int clickC = Integer.valueOf(click[1]);
                                        char clickRow = clickName.charAt(0);
                                        char clickCol = clickName.charAt(1);
                                        //判斷是否相鄰
                                        if (Math.abs(clickRow - emptyRow) + Math.abs(clickCol - emptyCol) == 1) {
                                            //將被單擊的圖片移動到空白按鈕上
                                            moveTimes++;
                                            information2.setText("Moves：" + moveTimes + "     Time：" + Time);
                                            button[number - 1].setIcon(clickButton.getIcon());
                                            button[number - 1].setEnabled(true);
                                            clickButton.setIcon(new ImageIcon("img/picture/" + imgType[imageNum] + "/" + number + ".jpg"));
                                            clickButton.setEnabled(false);
                                            button[number - 1] = clickButton;
                                            int n = order[emptyR][emptyC];
                                            order[emptyR][emptyC] = order[clickR][clickC];
                                            order[clickR][clickC] = n;
                                        }
                                        int count = 0;
                                        boolean countRight = true;
                                        for (int i = 0; i < Row; i++) {
                                            for (int j = 0; j < Row; j++) {
                                                if (order[i][j] == count) {
                                                    count += 1;
                                                } else {
                                                    countRight = false;
                                                }
                                            }
                                        }
                                        if (countRight) {
                                            timer.cancel();
                                            JOptionPane.showMessageDialog(null, "Congratulations! You finished the Klotski.", "GameOver", JOptionPane.PLAIN_MESSAGE);
                                            remove(gameP);
                                            setSize(230, 270);
                                            setLocationRelativeTo(null);
                                            //gameoverP畫面
                                            gameoverP = new JPanel();
                                            gameoverP.setBackground(Color.decode("#e9e5d0"));
                                            gameoverP.setLayout(new BorderLayout());
                                            Panel p9 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                            gameoverP.add(p9, BorderLayout.NORTH);
                                            JLabel title3 = new JLabel("<html><h2>---Play Result---</h2></html>");
                                            p9.add(title3);
                                            Panel p10 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                            gameoverP.add(p10, BorderLayout.CENTER);
                                            Panel p101 = new Panel(new GridLayout(5, 1, 250, 10));
                                            p10.add(p101);
                                            JLabel result1 = new JLabel("Player：" + data[0]);
                                            p101.add(result1);
                                            JLabel result2 = new JLabel("Type：" + data[1]);
                                            p101.add(result2);
                                            JLabel result3 = new JLabel("Level：" + data[2]);
                                            p101.add(result3);
                                            JLabel result4 = new JLabel("Moves：" + moveTimes);
                                            p101.add(result4);
                                            JLabel result5 = new JLabel("Time：" + Time);
                                            p101.add(result5);
                                            Panel p11 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                            gameoverP.add(p11, BorderLayout.SOUTH);
                                            JButton menu2 = new JButton("MENU");
                                            p11.add(menu2);
                                            menu2.setBackground(Color.decode("#eab34b"));
                                            menu2.setBorderPainted(false);
                                            menu2.setForeground(Color.decode("#3f3534"));
                                            add(gameoverP);
                                            //gameover頁面的按鈕
                                            menu2.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    remove(gameoverP);
                                                    setSize(400, 300);
                                                    setLocationRelativeTo(null);
                                                    nameText.setText("");
                                                    add(menuP);
                                                }
                                            });
                                        }
                                    }
                                });
                                button[randomNum].setBackground(Color.decode("#e9e5d0"));
                                ImageIcon icon = new ImageIcon("img/picture/" + imgType[imageNum] + "/" + (randomNum + 1) + ".jpg");
                                button[randomNum].setIcon(icon);
                                //button[randomNum].setBorder(BorderFactory.createLineBorder(Color.decode("#3f3534")));
                                button[randomNum].setBorderPainted(false);
                                int x = i / Row;
                                int y = i % Row;
                                button[randomNum].setName(x + "" + y);
                                order[x][y] = randomNum;
                                klotski.add(button[randomNum]);
                                break;
                            }
                        }
                    }
                    button[number - 1] = new JButton("");
                    button[number - 1].setName((Row - 1) + "" + (Row - 1));
                    button[number - 1].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String emptyName = button[number - 1].getName();
                            String empty[] = emptyName.split("");
                            int emptyR = Integer.valueOf(empty[0]);
                            int emptyC = Integer.valueOf(empty[1]);
                            char emptyRow = emptyName.charAt(0);//空白按鈕行
                            char emptyCol = emptyName.charAt(1);//空白按鈕列
                            JButton clickButton = (JButton) e.getSource();
                            String clickName = clickButton.getName();
                            String click[] = clickName.split("");
                            int clickR = Integer.valueOf(click[0]);
                            int clickC = Integer.valueOf(click[1]);
                            char clickRow = clickName.charAt(0);
                            char clickCol = clickName.charAt(1);
                            //判斷是否相鄰
                            if (Math.abs(clickRow - emptyRow) + Math.abs(clickCol - emptyCol) == 1) {
                                //將被單擊的圖片移動到空白按鈕上
                                moveTimes++;
                                information2.setText("Moves：" + moveTimes + "     Time：" + Time);
                                button[number - 1].setIcon(clickButton.getIcon());
                                button[number - 1].setEnabled(true);
                                clickButton.setIcon(new ImageIcon("img/picture/" + imgType[imageNum] + "/" + number + ".jpg"));
                                clickButton.setEnabled(false);
                                button[number - 1] = clickButton;
                                int n = order[emptyR][emptyC];
                                order[emptyR][emptyC] = order[clickR][clickC];
                                order[clickR][clickC] = n;
                            }
                            int count = 0;
                            boolean countRight = true;
                            for (int i = 0; i < Row; i++) {
                                for (int j = 0; j < Row; j++) {
                                    if (order[i][j] == count) {
                                        count += 1;
                                    } else {
                                        countRight = false;
                                    }
                                }
                            }
                            if (countRight) {
                                timer.cancel();
                                JOptionPane.showMessageDialog(null, "Congratulations! You finished the Klotski.", "GameOver", JOptionPane.PLAIN_MESSAGE);
                                remove(gameP);
                                setSize(230, 270);
                                setLocationRelativeTo(null);
                                //gameoverP畫面
                                gameoverP = new JPanel();
                                gameoverP.setBackground(Color.decode("#e9e5d0"));
                                gameoverP.setLayout(new BorderLayout());
                                Panel p9 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                gameoverP.add(p9, BorderLayout.NORTH);
                                JLabel title3 = new JLabel("<html><h2>---Play Result---</h2></html>");
                                p9.add(title3);
                                Panel p10 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                gameoverP.add(p10, BorderLayout.CENTER);
                                Panel p101 = new Panel(new GridLayout(5, 1, 250, 10));
                                p10.add(p101);
                                JLabel result1 = new JLabel("Player：" + data[0]);
                                p101.add(result1);
                                JLabel result2 = new JLabel("Type：" + data[1]);
                                p101.add(result2);
                                JLabel result3 = new JLabel("Level：" + data[2]);
                                p101.add(result3);
                                JLabel result4 = new JLabel("Moves：" + moveTimes);
                                p101.add(result4);
                                JLabel result5 = new JLabel("Time：" + Time);
                                p101.add(result5);
                                Panel p11 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                gameoverP.add(p11, BorderLayout.SOUTH);
                                JButton menu2 = new JButton("MENU");
                                p11.add(menu2);
                                menu2.setBackground(Color.decode("#eab34b"));
                                menu2.setBorderPainted(false);
                                menu2.setForeground(Color.decode("#3f3534"));
                                add(gameoverP);
                                //gameover頁面的按鈕
                                menu2.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        remove(gameoverP);
                                        setSize(400, 300);
                                        setLocationRelativeTo(null);
                                        nameText.setText("");
                                        add(menuP);
                                    }
                                });
                            }
                        }
                    });
                    ImageIcon icon2 = new ImageIcon("img/picture/" + imgType[imageNum] + "/" + number + ".jpg");
                    button[number - 1].setIcon(icon2);
                    //button[number - 1].setBorder(BorderFactory.createLineBorder(Color.decode("#3f3534")));
                    button[number - 1].setBorderPainted(false);
                    button[number - 1].setEnabled(false);
                    order[Row - 1][Row - 1] = number - 1;
                    klotski.add(button[number - 1]);
                    gameP.add(klotski, BorderLayout.CENTER);
//number puzzle
                } else {
                    Row = levelRow[level.getSelectedIndex()];
                    number = Row * Row;
                    gridLayout = new GridLayout(Row, Row);
                    order = new int[Row][Row];
                    orderColor = new String[Row][Row];
                    for (int i = 0; i < Row; i++) {
                        for (int j = 0; j < Row; j++) {
                            order[i][j] = 1000;
                        }
                    }
                    klotski = new JPanel(gridLayout);
                    klotski.setSize(400, 400);
                    button = new JButton[number];
                    for (int i = 0; i < number - 1; i++) {
                        while (true) {
                            int randomNum;
                            randomNum = (int) (Math.random() * (number - 1));
                            boolean comfirm = false;
                            for (int a = 0; a < Row; a++) {
                                for (int b = 0; b < Row; b++) {
                                    if (randomNum == order[a][b]) {
                                        comfirm = true;
                                    }
                                }
                            }
                            if (comfirm) {
                                comfirm = false;
                            } else {
                                button[randomNum] = new JButton("" + (randomNum + 1));
                                button[randomNum].addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        String emptyName = button[number - 1].getName();
                                        String empty[] = emptyName.split("");
                                        int emptyR = Integer.valueOf(empty[0]);
                                        int emptyC = Integer.valueOf(empty[1]);
                                        char emptyRow = emptyName.charAt(0);//空白按鈕行
                                        char emptyCol = emptyName.charAt(1);//空白按鈕列
                                        JButton clickButton = (JButton) e.getSource();
                                        String clickName = clickButton.getName();
                                        String click[] = clickName.split("");
                                        int clickR = Integer.valueOf(click[0]);
                                        int clickC = Integer.valueOf(click[1]);
                                        char clickRow = clickName.charAt(0);
                                        char clickCol = clickName.charAt(1);
                                        //判斷是否相鄰
                                        if (Math.abs(clickRow - emptyRow) + Math.abs(clickCol - emptyCol) == 1) {
                                            moveTimes++;
                                            information2.setText("Moves：" + moveTimes + "     Time：" + Time);
                                            //將被單擊的圖片移動到空白按鈕上
                                            Font f = new Font("Arial", Font.BOLD, 30);
                                            button[number - 1].setFont(f);
                                            button[number - 1].setText("" + (order[clickR][clickC] + 1));
                                            button[number - 1].setBackground(Color.decode(orderColor[clickR][clickC]));
                                            button[number - 1].setForeground(Color.decode("#3f3534"));
                                            button[number - 1].setEnabled(true);
                                            clickButton.setBorder(BorderFactory.createLineBorder(Color.decode("#3f3534")));
                                            clickButton.setBackground(Color.decode("#FFFFFF"));
                                            clickButton.setEnabled(false);
                                            clickButton.setText("");
                                            button[number - 1] = clickButton;
                                            int n = order[emptyR][emptyC];
                                            order[emptyR][emptyC] = order[clickR][clickC];
                                            order[clickR][clickC] = n;
                                            String s = orderColor[emptyR][emptyC];
                                            orderColor[emptyR][emptyC] = orderColor[clickR][clickC];
                                            orderColor[clickR][clickC] = s;
                                        }
                                        int count = 0;
                                        boolean countRight = true;
                                        for (int i = 0; i < Row; i++) {
                                            for (int j = 0; j < Row; j++) {
                                                if (order[i][j] == count) {

                                                } else {
                                                    countRight = false;
                                                }
                                                count++;
                                            }
                                        }
                                        if (countRight) {
                                            timer.cancel();
                                            JOptionPane.showMessageDialog(null, "Congratulations! You finished the Klotski.", "GameOver", JOptionPane.PLAIN_MESSAGE);
                                            remove(gameP);
                                            setSize(230, 270);
                                            setLocationRelativeTo(null);
                                            //gameoverP畫面
                                            gameoverP = new JPanel();
                                            gameoverP.setBackground(Color.decode("#e9e5d0"));
                                            gameoverP.setLayout(new BorderLayout());
                                            Panel p9 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                            gameoverP.add(p9, BorderLayout.NORTH);
                                            JLabel title3 = new JLabel("<html><h2>---Play Result---</h2></html>");
                                            p9.add(title3);
                                            Panel p10 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                            gameoverP.add(p10, BorderLayout.CENTER);
                                            Panel p101 = new Panel(new GridLayout(5, 1, 250, 10));
                                            p10.add(p101);
                                            JLabel result1 = new JLabel("Player：" + data[0]);
                                            p101.add(result1);
                                            JLabel result2 = new JLabel("Type：" + data[1]);
                                            p101.add(result2);
                                            JLabel result3 = new JLabel("Level：" + data[2]);
                                            p101.add(result3);
                                            JLabel result4 = new JLabel("Moves：" + moveTimes);
                                            p101.add(result4);
                                            JLabel result5 = new JLabel("Time：" + Time);
                                            p101.add(result5);
                                            Panel p11 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                            gameoverP.add(p11, BorderLayout.SOUTH);
                                            JButton menu2 = new JButton("MENU");
                                            p11.add(menu2);
                                            menu2.setBackground(Color.decode("#eab34b"));
                                            menu2.setBorderPainted(false);
                                            menu2.setForeground(Color.decode("#3f3534"));
                                            add(gameoverP);
                                            //gameover頁面的按鈕
                                            menu2.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    remove(gameoverP);
                                                    setSize(400, 300);
                                                    setLocationRelativeTo(null);
                                                    nameText.setText("");
                                                    add(menuP);
                                                }
                                            });
                                        }
                                    }
                                });
                                int c = (int) (Math.random() * 4);
                                Font f = new Font("Arial", Font.BOLD, 30);
                                button[randomNum].setFont(f);
                                button[randomNum].setBackground(Color.decode(color[c]));
                                button[randomNum].setBorder(BorderFactory.createLineBorder(Color.decode("#3f3534")));
                                button[randomNum].setForeground(Color.decode("#3f3534"));
                                int x = i / Row;
                                int y = i % Row;
                                button[randomNum].setName(x + "" + y);
                                order[x][y] = randomNum;
                                orderColor[x][y] = color[c];
                                klotski.add(button[randomNum]);
                                break;
                            }
                        }
                    }
                    button[number - 1] = new JButton("");
                    button[number - 1].setName((Row - 1) + "" + (Row - 1));
                    button[number - 1].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String emptyName = button[number - 1].getName();
                            String empty[] = emptyName.split("");
                            int emptyR = Integer.valueOf(empty[0]);
                            int emptyC = Integer.valueOf(empty[1]);
                            char emptyRow = emptyName.charAt(0);//空白按鈕行
                            char emptyCol = emptyName.charAt(1);//空白按鈕列
                            JButton clickButton = (JButton) e.getSource();
                            String clickName = clickButton.getName();
                            String click[] = clickName.split("");
                            int clickR = Integer.valueOf(click[0]);
                            int clickC = Integer.valueOf(click[1]);
                            char clickRow = clickName.charAt(0);
                            char clickCol = clickName.charAt(1);
                            //判斷是否相鄰
                            if (Math.abs(clickRow - emptyRow) + Math.abs(clickCol - emptyCol) == 1) {
                                moveTimes++;
                                information2.setText("Moves：" + moveTimes + "     Time：" + Time);
                                //將被單擊的圖片移動到空白按鈕上
                                Font f = new Font("Arial", Font.BOLD, 30);
                                button[number - 1].setFont(f);
                                button[number - 1].setText("" + (order[clickR][clickC] + 1));
                                button[number - 1].setBackground(Color.decode(orderColor[clickR][clickC]));
                                button[number - 1].setForeground(Color.decode("#3f3534"));
                                button[number - 1].setEnabled(true);
                                clickButton.setBorder(BorderFactory.createLineBorder(Color.decode("#3f3534")));
                                clickButton.setBackground(Color.decode("#FFFFFF"));
                                clickButton.setEnabled(false);
                                clickButton.setText("");
                                button[number - 1] = clickButton;
                                int n = order[emptyR][emptyC];
                                order[emptyR][emptyC] = order[clickR][clickC];
                                order[clickR][clickC] = n;
                                String s = orderColor[emptyR][emptyC];
                                orderColor[emptyR][emptyC] = orderColor[clickR][clickC];
                                orderColor[clickR][clickC] = s;
                            }

                            int count = 0;
                            boolean countRight = true;
                            for (int i = 0; i < Row; i++) {
                                for (int j = 0; j < Row; j++) {
                                    if (order[i][j] == count) {

                                    } else {
                                        countRight = false;
                                    }
                                    count++;
                                }
                            }
                            if (countRight) {
                                timer.cancel();
                                JOptionPane.showMessageDialog(null, "Congratulations! You finished the Klotski.", "GameOver", JOptionPane.PLAIN_MESSAGE);
                                remove(gameP);
                                setSize(230, 270);
                                setLocationRelativeTo(null);
                                //gameoverP畫面
                                gameoverP = new JPanel();
                                gameoverP.setBackground(Color.decode("#e9e5d0"));
                                gameoverP.setLayout(new BorderLayout());
                                Panel p9 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                gameoverP.add(p9, BorderLayout.NORTH);
                                JLabel title3 = new JLabel("<html><h2>---Play Result---</h2></html>");
                                p9.add(title3);
                                Panel p10 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                gameoverP.add(p10, BorderLayout.CENTER);
                                Panel p101 = new Panel(new GridLayout(5, 1, 250, 10));
                                p10.add(p101);
                                JLabel result1 = new JLabel("Player：" + data[0]);
                                p101.add(result1);
                                JLabel result2 = new JLabel("Type：" + data[1]);
                                p101.add(result2);
                                JLabel result3 = new JLabel("Level：" + data[2]);
                                p101.add(result3);
                                JLabel result4 = new JLabel("Moves：" + moveTimes);
                                p101.add(result4);
                                JLabel result5 = new JLabel("Time：" + Time);
                                p101.add(result5);
                                Panel p11 = new Panel(new FlowLayout(FlowLayout.CENTER));
                                gameoverP.add(p11, BorderLayout.SOUTH);
                                JButton menu2 = new JButton("MENU");
                                p11.add(menu2);
                                menu2.setBackground(Color.decode("#eab34b"));
                                menu2.setBorderPainted(false);
                                menu2.setForeground(Color.decode("#3f3534"));
                                add(gameoverP);
                                //gameover頁面的按鈕
                                menu2.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        remove(gameoverP);
                                        setSize(400, 300);
                                        setLocationRelativeTo(null);
                                        nameText.setText("");
                                        add(menuP);
                                    }
                                });
                            }
                        }
                    });
                    button[number - 1].setBackground(Color.decode("#FFFFFF"));
                    button[number - 1].setBorder(BorderFactory.createLineBorder(Color.decode("#3f3534")));
                    button[number - 1].setEnabled(false);
                    order[Row - 1][Row - 1] = number - 1;
                    orderColor[Row - 1][Row - 1] = "#FFFFFF";
                    klotski.add(button[number - 1]);
                    gameP.add(klotski, BorderLayout.CENTER);
                }
                Panel p6 = new Panel(new FlowLayout(FlowLayout.LEFT));
                gameP.add(p6, BorderLayout.WEST);
                if (type.getSelectedIndex() == 1) {
                    JLabel model = new JLabel(new ImageIcon("img/picture/model.jpg"));
                    p6.add(model);
                } else {

                }
                Panel p8 = new Panel(new FlowLayout(FlowLayout.CENTER));
                gameP.add(p8, BorderLayout.SOUTH);
                JButton pass = new JButton("PASS");
                p8.add(pass);
                pass.setBackground(Color.decode("#f37152"));
                pass.setBorderPainted(false);
                pass.setForeground(Color.decode("#3f3534"));
                JLabel blank2 = new JLabel("      ");
                p8.add(blank2);
                JButton menu = new JButton("MENU");
                p8.add(menu);
                menu.setBackground(Color.decode("#eab34b"));
                menu.setBorderPainted(false);
                menu.setForeground(Color.decode("#3f3534"));
//Time計時                
                timer = new Timer();
                Time = "00:00";
                timer.schedule(new Timing(), 0, 1000);
//game頁面的按鈕
                menu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        timer.cancel();
                        remove(gameP);
                        setSize(400, 300);
                        setLocationRelativeTo(null);
                        nameText.setText("");
                        add(menuP);
                    }
                });
                pass.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        timer.cancel();
                        JOptionPane.showMessageDialog(null, "Congratulations! You finished the Klotski.", "GameOver", JOptionPane.PLAIN_MESSAGE);
                        remove(gameP);
                        setSize(230, 270);
                        setLocationRelativeTo(null);
                        gameoverP = new JPanel();
                        add(gameoverP);
//gameoverP畫面
                        gameoverP.setBackground(Color.decode("#e9e5d0"));
                        gameoverP.setLayout(new BorderLayout());
                        Panel p9 = new Panel(new FlowLayout(FlowLayout.CENTER));
                        gameoverP.add(p9, BorderLayout.NORTH);
                        JLabel title3 = new JLabel("<html><h2>---Play Result---</h2></html>");
                        p9.add(title3);
                        Panel p10 = new Panel(new FlowLayout(FlowLayout.CENTER));
                        gameoverP.add(p10, BorderLayout.CENTER);
                        Panel p101 = new Panel(new GridLayout(5, 1, 250, 10));
                        p10.add(p101);
                        JLabel result1 = new JLabel("Player：" + data[0]);
                        p101.add(result1);
                        JLabel result2 = new JLabel("Type：" + data[1]);
                        p101.add(result2);
                        JLabel result3 = new JLabel("Level：" + data[2]);
                        p101.add(result3);
                        JLabel result4 = new JLabel("Moves：" + moveTimes);
                        p101.add(result4);
                        JLabel result5 = new JLabel("Time：" + Time);
                        p101.add(result5);
                        Panel p11 = new Panel(new FlowLayout(FlowLayout.CENTER));
                        gameoverP.add(p11, BorderLayout.SOUTH);
                        JButton menu2 = new JButton("MENU");
                        p11.add(menu2);
                        menu2.setBackground(Color.decode("#eab34b"));
                        menu2.setBorderPainted(false);
                        menu2.setForeground(Color.decode("#3f3534"));
//gameover頁面的按鈕
                        menu2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                remove(gameoverP);
                                setSize(400, 300);
                                setLocationRelativeTo(null);
                                nameText.setText("");
                                add(menuP);
                            }
                        });
                    }
                });
            }
        });

    }

    class Timing extends java.util.TimerTask {

        int t = 0;
        int m = 0;
        int s = 0;

        @Override
        public void run() {
            t++;
            m = t / 60;
            s = t % 60;
            if (m < 10) {
                Time = "0" + m + ":";
            } else {
                Time = m + ":";
            }
            if (s < 10) {
                Time += "0" + s;
            } else {
                Time += s;
            }
            information2.setText("Moves：" + moveTimes + "     Time：" + Time);
        }
    }
}
