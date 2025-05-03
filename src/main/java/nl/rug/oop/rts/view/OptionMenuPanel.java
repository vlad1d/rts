package nl.rug.oop.rts.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.controller.GraphListener;
import nl.rug.oop.rts.controller.RenameAction;
import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Class for the option menu.
 */
public class OptionMenuPanel extends JPanel implements GraphListener {
    @Getter
    @Setter
    private JLabel nameLabel;
    @Getter
    @Setter
    private JLabel nameField;
    @Getter
    @Setter
    private JButton renameButton;
    @Getter
    @Setter
    private JTextField textField;
    private Graph graph;
    private Image backgroundImage;

    /**
     * Allows the creation of the option menu panel.
     *
     * @param graph with nodes and edges that can be renamed in the option menu
     */
    public OptionMenuPanel(Graph graph) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        loadBackgroundImage();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        setBackground(Color.white);
        this.graph = graph;
        graph.addListener(this);

        nameLabel = new JLabel();
        nameLabel.setText("Option Menu");
        nameField = new JLabel("Nothing selected");
        this.add(nameLabel, gbc);
        gbc.gridy++;
        this.add(nameField, gbc);
        gbc.gridy++;
        textField = new JTextField(8);
        this.add(textField, gbc);

        this.renameButton = new JButton("Rename");
        renameButton.addActionListener(new RenameAction(graph, textField));
        renameButton.setEnabled(graph.getSelectedNode() != null || graph.getSelectedEdge() != null);
        gbc.gridy++;
        this.add(renameButton, gbc);

    }

    /**
     * Method that updates the nameField accordingly to whatever is selected.
     */
    public void updated() {
        Node node = graph.getSelectedNode();
        Edge edge = graph.getSelectedEdge();
        if (edge != null) {
            Node startNode = edge.getStart();
            Node endNode = edge.getEnd();
            nameField.setText(startNode.getName() + " + " + endNode.getName() + " [" + edge.getName() + "]");
        }
        if (node != null) {
            nameField.setText(node.getName());
        }

        if (edge == null && node == null) {
            nameField.setText("Nothing selected");
        }
    }

    /**
     * This method finds and sets the background image to the corresponding texture.
     */
    private void loadBackgroundImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            backgroundImage = ImageIO.read(classLoader.getResourceAsStream("images/maps/mapTexture.jpg"));
        } catch (IOException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * Method that updates the graph and buttons menu.
     */
    public void graphUpdated() {
        updated();
        updateButtonsOptionMenu();
    }

    /**
     * Method that changes the availability of the rename button depending whether nodes or edges are selected.
     */
    public void updateButtonsOptionMenu() {
        renameButton.setEnabled(graph.getSelectedNode() != null || graph.getSelectedEdge() != null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        }
    }

}
