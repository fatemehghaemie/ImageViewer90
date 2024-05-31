import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.RescaleOp;

public class ImageViewerGUI extends JFrame implements ActionListener {

    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;

    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = "/home/...";
    File file;
    BufferedImage originalImage;


    ImageViewerGUI() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        selectFileButton = new JButton("Choose Image");
        selectFileButton.addActionListener(this);
        showImageButton = new JButton("Show Image");
        showImageButton.addActionListener(this);
        brightnessButton = new JButton("Brightness");
        brightnessButton.addActionListener(this);
        grayscaleButton = new JButton("Gray scale");
        grayscaleButton.addActionListener(this);
        resizeButton = new JButton("Resize");
        resizeButton.addActionListener(this);
        closeButton = new JButton("Exit");
        closeButton.addActionListener(this);

        mainPanel.add(selectFileButton);
        mainPanel.add(showImageButton);
        mainPanel.add(brightnessButton);
        mainPanel.add(grayscaleButton);
        mainPanel.add(resizeButton);
        mainPanel.add(closeButton);

        this.add(mainPanel);
    }

    public void resizePanel() {
        JFrame panel = new JFrame();
        panel.setVisible(true);

        panel.setBounds(200,100,600,700);
        JButton result = new JButton("result");
        result.setBounds(400,60,100,50);
        widthTextField = new JTextField();
        widthTextField.setBounds(200, 250, 230, 40);
        panel.add(widthTextField);
        heightTextField= new JTextField();
        heightTextField.setBounds(200, 350, 230, 40);
        panel.add(heightTextField);

        JButton back = new JButton("Back");
        back.setBounds(95,60,100,50);
        panel.setLayout(null);
        panel.add(result);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);

            }
        });

        Image scaledImage = originalImage.getScaledInstance(widthTextField.getWidth(), heightTextField.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(widthTextField.getWidth(), heightTextField.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = resizedImage.createGraphics();
        g2D.drawImage(scaledImage, 0, 0, null);
        g2D.dispose();

        ImageIcon imageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel(imageIcon);
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame tempFrame = new JFrame();

                tempFrame.add(imageLabel);
                tempFrame.setTitle("your image after resize");
                int newHeight =heightTextField.getHeight();
                int newWidth  = widthTextField.getWidth();

                tempFrame.setSize( newWidth, newHeight);

                tempFrame.setLayout(null);
                tempFrame.setVisible(true);
            }
        });
    }


    public void brightnessPanel() {

        JFrame panel = new JFrame();
        panel.setVisible(true);

        panel.setBounds(200,100,600,700);
        JButton result = new JButton("result");
        result.setBounds(400,60,100,50);
        brightnessTextField = new JTextField();
        brightnessTextField.setBounds(200, 250, 230, 40);
        panel.add(brightnessTextField);

        JButton back = new JButton("Back");
        back.setBounds(95,60,100,50);
        panel.setLayout(null);
        panel.add(result);
        panel.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);

            }
        });

        BufferedImage brightenedImage = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        float scaleFactor = brightnessTextField.getAlignmentX() ;
        Graphics2D graphics2D = brightenedImage.createGraphics();
        RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
        rescaleOp.filter(originalImage, brightenedImage);
        graphics2D.drawImage(brightenedImage, 0, 0, null);
        graphics2D.dispose();

        ImageIcon imageIcon = new ImageIcon(brightenedImage);
        JLabel imageLabel = new JLabel(imageIcon);
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame tempFrame = new JFrame();
                tempFrame.add(imageLabel);
                tempFrame.setTitle("after adjusting the brightness");
                tempFrame.setSize(brightenedImage.getWidth(), brightenedImage.getHeight());
                tempFrame.setVisible(true);
            }
        });


    }


    public void chooseFileImage() {
        JFileChooser fileChooser = new JFileChooser(filePath);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showOriginalImage() {
        JPanel tempPanel = new JPanel();
        ImageIcon imageIcon = new ImageIcon(originalImage);
        JLabel imageLabel = new JLabel(imageIcon);
        tempPanel.add(imageLabel);

        JFrame tempFrame = new JFrame();
        tempFrame.add(tempPanel);
        tempFrame.setTitle("your original image");
        tempFrame.setSize(originalImage.getWidth(), originalImage.getHeight());
        tempFrame.setVisible(true);
    }



    public void grayscaleImage() {
        BufferedImage grayImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics  f = grayImage.getGraphics();
        f.drawImage(originalImage, 0, 0, null);
        f.dispose();

        ImageIcon imageIcon = new ImageIcon(grayImage);
        JLabel imageLabel = new JLabel(imageIcon);

        JFrame tempFrame = new JFrame();
        tempFrame.add(imageLabel);
        tempFrame.setTitle("your grayscale Image");
        tempFrame.setSize(grayImage.getWidth(), grayImage.getHeight());
        tempFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectFileButton) {
            chooseFileImage();
        } else if (e.getSource() == showImageButton) {
            showOriginalImage();
        } else if (e.getSource() == brightnessButton) {
            brightnessPanel();
        }else if (e.getSource() == grayscaleButton) {
            grayscaleImage();
        } else if (e.getSource() == resizeButton) {
            resizePanel();
        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageViewerGUI());
    }
}