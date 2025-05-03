package nl.rug.oop.rts.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.controller.*;
import nl.rug.oop.rts.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that displays the graph and handles interactions with the graph.
 */
public class GraphPanel extends JPanel implements GraphListener {
    private Graph graph;
    private Image backgroundImage;
    private Image nodeImage;
    private Image selectedNodeImage;
    private Image armyMenImage;
    private Image armyElvesImage;
    private Image armyDwarvesImage;
    private Image armyIsengardImage;
    private Image armyMordorImage;

    @Getter
    @Setter
    private JButton removeNodeButton;
    @Getter
    @Setter
    private JButton addNodeButton;
    @Getter
    @Setter
    private JButton addEdgeButton;
    @Getter
    @Setter
    private JButton removeEdgeButton;
    @Getter
    @Setter
    private JButton addArmyButton;
    @Getter
    @Setter
    private JButton simulateButton;
    @Getter
    @Setter
    private JButton addEventButton;
    @Getter
    @Setter
    private JButton removeEventButton;
    @Getter
    @Setter
    private JButton saveButton;

    @Getter
    @Setter
    private int nodeWidth;
    @Getter
    @Setter
    private int nodeHeight;

    /**
     * Method that initialises the graph panel.
     *
     * @param graph that is used for initiation
     */
    public GraphPanel(Graph graph) {
        setBackground(Color.white);
        loadImages();
        this.nodeHeight = 100;
        this.nodeWidth = 100;
        this.graph = graph;
        graph.addListener(this);
        MouseGraphMoved mouseGraphMoved = new MouseGraphMoved(graph, this);
        addMouseListener(mouseGraphMoved);
        addMouseMotionListener(mouseGraphMoved);
        buttonMenuGraph(graph);
    }

    /**
     * This method calls all image loaders to initialise all image textures.
     */
    private void loadImages() {
        loadBackgroundImage();
        loadNodeImage();
        loadSelectedNodeImage();
        loadArmyImage();
    }

    /**
     * This method finds and sets the background image to the corresponding texture.
     */
    private void loadBackgroundImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            backgroundImage = ImageIO.read(classLoader.getResourceAsStream("images/maps/lotrMap.jpg"));
        } catch (IOException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * Method that draws nodes, whilst checking if any is selected.
     *
     * @param g of graphics class, provides methods for drawing on screen
     */
    private void drawNode(Graphics g) {
        for (Node node : graph.getNodes()) {
            int nodeX = node.getX();
            int nodeY = node.getY();
            g.drawImage(nodeImage, nodeX - 5, nodeY - 5, nodeWidth + 10, nodeHeight + 10, this);
            if (node.isSelected()) {
                g.drawImage(selectedNodeImage, nodeX - 5, nodeY - 5, nodeWidth + 10, nodeHeight + 10, this);
            }
            g.setColor(Color.white);
            g.drawString(node.getName(), node.getX() + 40 - node.getName().length() * 2, node.getY() + 50);

            drawArmyNode(g, node);
        }
    }

    /**
     * This method finds and sets the node image to the corresponding texture.
     */
    private void loadNodeImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            nodeImage = ImageIO.read(classLoader.getResourceAsStream("images/nodes/node2.png"));
        } catch (IOException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * This method finds and sets the selected node image to the corresponding texture.
     */
    private void loadSelectedNodeImage() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            selectedNodeImage = ImageIO.read(classLoader.getResourceAsStream("images/nodes/node3.png"));
        } catch (IOException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * Method that draws edges, whilst checking if any is selected.
     *
     * @param g of graphics class, provides methods for drawing on screen
     */
    private void drawEdge(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();
        float[] dashPattern = {10.0f, 10.0f};
        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));

        for (Edge edge : graph.getEdges()) {
            Node start = edge.getStart();
            Node end = edge.getEnd();

            g2d.setColor(Color.BLACK);
            if (graph.getSelectedEdge() == edge) {
                g2d.setColor(Color.GREEN);
                g2d.drawLine(start.getX() + 1, start.getY(), end.getX() + 1, end.getY());
            }
            g2d.drawLine(start.getX(), start.getY(), end.getX(), end.getY());

            drawArmyEdge(g, edge);
        }
    }

    /**
     * Method that draws a circle on the node where there is an army.
     *
     * @param g    of graphics class, to draw
     * @param node the node that will be added armies
     */
    private void drawArmyNode(Graphics g, Node node) {
        List<Army> armyList = node.getArmyList();
        Image armyImage = null;
        int offSet = 0;
        if (!armyList.isEmpty()) {
            for (Army army : armyList) {

                int circleRadius = 15;
                int xCoord = node.getX() + offSet - circleRadius;
                int yCoord = node.getY() + nodeHeight - circleRadius;
                armyImage = getFactionColour(army.getFaction());

                circleRadius *= 2;
                g.fillOval(xCoord, yCoord, circleRadius, circleRadius);
                g.drawImage(armyImage, xCoord, yCoord, circleRadius, circleRadius, this);
                offSet += circleRadius / 2;
            }
        }
    }

    /**
     * Method that draws a circle on the edge where there is an army.
     *
     * @param g    of graphics class, to draw
     * @param edge the edge that will be added armies
     */
    private void drawArmyEdge(Graphics g, Edge edge) {
        List<Army> armyList = edge.getArmyList();
        Image armyImage = null;
        int offSet = 0;
        if (!armyList.isEmpty()) {
            for (Army army : armyList) {

                armyImage = getFactionColour(army.getFaction());
                int circleRadius = 15;
                int xCoord = (edge.getStart().getX() + edge.getEnd().getX()) / 2 + offSet - circleRadius;
                int yCoord = (edge.getStart().getY() + edge.getEnd().getY()) / 2 - circleRadius;
                circleRadius *= 2;

                g.drawImage(armyImage, xCoord, yCoord, circleRadius, circleRadius, this);
                offSet += circleRadius / 2;
            }
        }
    }

    /**
     * This method finds and sets the selected army image to the corresponding texture.
     */
    private void loadArmyImage() {
        Image armyImage = null;
        String directory = "images/factions/";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            armyMenImage = ImageIO.read(classLoader.getResourceAsStream(directory + "men.png"));
            armyElvesImage = ImageIO.read(classLoader.getResourceAsStream(directory + "elves.png"));
            armyDwarvesImage = ImageIO.read(classLoader.getResourceAsStream(directory + "dwarves.png"));
            armyMordorImage = ImageIO.read(classLoader.getResourceAsStream(directory + "mordor.png"));
            armyIsengardImage = ImageIO.read(classLoader.getResourceAsStream(directory + "isengard.png"));

        } catch (IOException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * Method that returns the texture associated with a faction.
     *
     * @param faction that has a texture
     * @return the colour
     */
    private Image getFactionColour(Faction faction) {
        switch (faction) {
            case Men:
                return armyMenImage;
            case Elves:
                return armyElvesImage;
            case Dwarves:
                return armyDwarvesImage;
            case Mordor:
                return armyMordorImage;
            case Isengard:
                return armyIsengardImage;
            default:
                return armyMenImage;
        }
    }

    /**
     * This method creates the buttons and adds them to the menu.
     *
     * @param graph the graph that will suffer changes from these buttons.
     */
    public void buttonMenuGraph(Graph graph) {
        this.graph = graph;
        this.addNodeButton = new JButton("Add Node");
        addNodeButton.addActionListener(new AddNodeAction(graph));
        this.removeNodeButton = new JButton("Remove Node");
        removeNodeButton.addActionListener(new RemoveNodeAction(graph));
        this.addEdgeButton = new JButton("Add Edge");
        addEdgeButton.addActionListener(new AddEdgeAction(graph));
        this.removeEdgeButton = new JButton("Remove Edge");
        removeEdgeButton.addActionListener(new RemoveEdgeAction(graph));
        this.addArmyButton = new JButton("Add Army");
        addArmyButton.addActionListener(new AddArmyAction(graph));
        this.simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(new Simulation(graph));
        this.addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(new AddEventAction(graph));
        this.removeEventButton = new JButton("Remove Event");
        removeEventButton.addActionListener(new RemoveEventAction(graph));
        this.saveButton = new JButton("Save Game");
        saveButton.addActionListener(new SaveAction(graph));

        List<JButton> jButtonList = new ArrayList<>(Arrays.asList(
                addNodeButton, removeNodeButton, addEdgeButton, removeEdgeButton, addArmyButton, simulateButton,
                addEventButton, removeEventButton, saveButton
        ));
        List<JButton> setEnabledNodeList = new ArrayList<>(Arrays.asList(
                removeNodeButton, addEdgeButton, addArmyButton
        ));
        List<JButton> setEnabledEdgeList = new ArrayList<>(Collections.singletonList(
                removeEdgeButton
        ));
        List<JButton> setEnabledNodeEdgeList = new ArrayList<>(Arrays.asList(
                addEventButton, removeEventButton
        ));

        setEnabledNode(setEnabledNodeList);
        setEnabledEdge(setEnabledEdgeList);
        setEnabledNodeEdge(setEnabledNodeEdgeList);
        addButtons(jButtonList);
    }

    /**
     * Method that adds buttons to the menu.
     *
     * @param jButtonList the list of buttons
     */
    public void addButtons(List<JButton> jButtonList) {
        for (JButton jButton : jButtonList) {
            add(jButton);
        }
    }

    /**
     * Method that enables all buttons when a node is selected.
     *
     * @param jButtonList the button list that hold this properly.
     */
    public void setEnabledNode(List<JButton> jButtonList) {
        for (JButton jButton : jButtonList) {
            jButton.setEnabled(graph.getSelectedNode() != null);
        }
    }

    /**
     * Method that enables all buttons when an edge is selected.
     *
     * @param jButtonList the button list that hold this properly.
     */
    public void setEnabledEdge(List<JButton> jButtonList) {
        for (JButton jButton : jButtonList) {
            jButton.setEnabled(graph.getSelectedEdge() != null);
        }
    }

    /**
     * Method that enables all buttons when an edge or node is selected.
     *
     * @param jButtonList the button list that hold this properly.
     */
    public void setEnabledNodeEdge(List<JButton> jButtonList) {
        for (JButton jButton : jButtonList) {
            jButton.setEnabled(graph.getSelectedNode() != null || graph.getSelectedEdge() != null);
        }
    }

    /**
     * Paint the UI.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        drawEdge(g);
        drawNode(g);
    }

    /**
     * Update the UI.
     */
    public void updated() {
        repaint();
        updateMenuButtons();
    }

    /**
     * Method that updates the buttons by setting them enabled (on/off) whenever a node/edge is selected.
     */
    private void updateMenuButtons() {
        removeNodeButton.setEnabled(graph.getSelectedNode() != null);
        addEdgeButton.setEnabled(graph.getSelectedNode() != null);
        removeEdgeButton.setEnabled(graph.getSelectedEdge() != null);
        addArmyButton.setEnabled(graph.getSelectedNode() != null);
        addEventButton.setEnabled(graph.getSelectedNode() != null || graph.getSelectedEdge() != null);
        removeEventButton.setEnabled(graph.getSelectedNode() != null || graph.getSelectedEdge() != null);
    }

    @Override
    public void graphUpdated() {
        updated();
    }
}
