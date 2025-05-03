package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.*;
import nl.rug.oop.rts.view.EventDisplayScreen;
import nl.rug.oop.rts.view.SaveScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for saving.
 */
public class SaveAction implements ActionListener {
    private Graph graph;
    private String quoteComma = "\",\n";
    private String startBracket = "{\n";

    public SaveAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File file = SaveScreen.chooseFile();
        String json = createJson();
        if (file != null && !file.getName().endsWith(".json")) {
            String path = file.getAbsolutePath();
            String newPath = path + ".json";
            file = new File(newPath);
        }

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(json);
                fileWriter.flush();
                String message = "Saved successfully!";
                EventDisplayScreen.displayMessage(message);
            } catch (IOException i) {
                System.err.println("Error saving. " + i.getMessage());
            } catch (NullPointerException i) {
                System.err.println("Null pointer exception error. " + i.getMessage());
            }
        } else {
            String message = "Did not save to file. Try again!";
            EventDisplayScreen.displayMessage(message);
        }
    }

    /**
     * Method that creates the Json file.
     *
     * @return the json file as a string
     */
    public String createJson() {
        String json = "";
        json = append(json, 0, startBracket);
        json = createNode(json);
        json = createEdge(json);
        json = append(json, 0, "}");
        return json;
    }

    /**
     * Method that creates the Node part of the String.
     *
     * @param json file that is being edited
     * @return the json file
     */
    public String createNode(String json) {
        Integer spaces = 0;
        spaces += 2;
        json = graph.getNodes().isEmpty() ?
                append(json, spaces, "\"Nodes\": [") : append(json, spaces, "\"Nodes\": [\n");
        int nodeCount = 0;
        for (Node node : graph.getNodes()) {
            spaces += 2;
            json = append(json, spaces, startBracket);
            spaces += 2;
            json = append(json, spaces, "\"Id\": " + node.getId() + ",\n");
            json = append(json, spaces, "\"Name\": \"" + node.getName() + quoteComma);
            json = node.getArmyList().isEmpty() ?
                    append(json, spaces, "\"Armies\": [") : append(json, spaces, "\"Armies\": [\n");
            json = createArmyNode(json, spaces, node);
            json = append(json, node.getArmyList().isEmpty() ? 0 : spaces, "],\n");
            json = node.getEventList().isEmpty() ?
                    append(json, spaces, "\"Events\": [") : append(json, spaces, "\"Events\": [\n");
            json = createEventsNode(json, spaces, node);
            json = append(json, node.getEventList().isEmpty() ? 0 : spaces, "]\n");
            spaces -= 2;
            if (nodeCount != graph.getNodes().size() - 1) {
                json = append(json, spaces, "},\n");
            } else {
                json = append(json, spaces, "}\n");
            }
            nodeCount++;
            spaces -= 2;
        }
        json = append(json, graph.getNodes().isEmpty() ? 0 : spaces, "],\n");
        return json;
    }

    /**
     * Method that creates the Edge part of the String.
     *
     * @param json file that is being edited
     * @return the json file
     */
    public String createEdge(String json) {
        Integer spaces = 0;
        spaces += 2;
        json = graph.getEdges().isEmpty() ?
                append(json, spaces, "\"Edges\": [") : append(json, spaces, "\"Edges\": [\n");
        int edgeCount = 0;
        for (Edge edge : graph.getEdges()) {
            spaces += 2;
            json = append(json, spaces, startBracket);
            spaces += 2;
            json = append(json, spaces, "\"Id\": " + edge.getId() + ",\n");
            json = append(json, spaces, "\"Name\": \"" + edge.getName() + quoteComma);
            json = append(json, spaces, "\"Node1\": \"" + edge.getStart().getId() + quoteComma);
            json = append(json, spaces, "\"Node2\": \"" + edge.getEnd().getId() + quoteComma);
            json = edge.getArmyList().isEmpty() ?
                    append(json, spaces, "\"Armies\": [") : append(json, spaces, "\"Armies\": [\n");
            json = createArmyEdge(json, spaces, edge);
            json = append(json, edge.getArmyList().isEmpty() ? 0 : spaces, "],\n");
            json = edge.getEventList().isEmpty() ?
                    append(json, spaces, "\"Events\": [") : append(json, spaces, "\"Events\": [\n");
            json = createEventsEdge(json, spaces, edge);
            json = append(json, edge.getEventList().isEmpty() ? 0 : spaces, "]\n");
            spaces -= 2;
            if (edgeCount != graph.getEdges().size() - 1) {
                json = append(json, spaces, "},\n");
            } else {
                json = append(json, spaces, "}\n");
            }
            edgeCount++;
            spaces -= 2;
        }
        json = append(json, graph.getEdges().isEmpty() ? 0 : spaces, "]\n");
        return json;
    }

    /**
     * Method that completes the json string with the army part.
     *
     * @param json   that is being completed
     * @param spaces that have to be taken into account.
     * @param node   that is being analysed for army
     * @return the new json
     */
    public String createArmyNode(String json, Integer spaces, Node node) {
        int armyCount = 0;
        for (Army army : node.getArmyList()) {
            spaces += 2;
            json = append(json, spaces, "{\n");
            spaces += 2;
            json = append(json, spaces, "\"Faction\": \"" + army.getFaction() + quoteComma);
            json = append(json, spaces, "\"Team\": \"" + army.getTeam() + quoteComma);
            if (army.getUnitList().isEmpty()) {
                json = append(json, spaces, "\"Units\": [");
            } else {
                json = append(json, spaces, "\"Units\": [\n");
            }
            json = createUnit(json, spaces, army);
            json = append(json, army.getUnitList().isEmpty() ? 0 : spaces, "]\n");
            spaces -= 2;
            if (armyCount != node.getArmyList().size() - 1) {
                json = append(json, spaces, "},\n");
            } else {
                json = append(json, spaces, "}\n");
            }
            armyCount++;
            spaces -= 2;
        }
        return json;
    }

    /**
     * Method that completes the json string with the army part.
     *
     * @param json   that is being completed
     * @param spaces that have to be taken into account.
     * @param edge   that is being analysed for army
     * @return the new json
     */
    public String createArmyEdge(String json, Integer spaces, Edge edge) {
        int armyCount = 0;
        for (Army army : edge.getArmyList()) {
            spaces += 2;
            json = append(json, spaces, startBracket);
            spaces += 2;
            json = append(json, spaces, "\"Faction\": \"" + army.getFaction() + quoteComma);
            json = append(json, spaces, "\"Team\": \"" + army.getTeam() + quoteComma);
            if (army.getUnitList().isEmpty()) {
                json = append(json, spaces, "\"Units\": [");
            } else {
                json = append(json, spaces, "\"Units\": [\n");
            }
            json = createUnit(json, spaces, army);
            json = append(json, army.getUnitList().isEmpty() ? 0 : spaces, "]\n");
            spaces -= 2;
            if (armyCount != edge.getArmyList().size() - 1) {
                json = append(json, spaces, "},\n");
            } else {
                json = append(json, spaces, "}\n");
            }
            armyCount++;
            spaces -= 2;
        }
        return json;
    }

    /**
     * Method that creates the unit part of the json file.
     *
     * @param json   file that is being changed.
     * @param spaces that have to be taken into consideration
     * @param army   that has units
     * @return the new json file
     */
    public String createUnit(String json, Integer spaces, Army army) {
        int unitCount = 0;
        for (Unit unit : army.getUnitList()) {
            spaces += 2;
            json = append(json, spaces, startBracket);
            spaces += 2;
            json = append(json, spaces, "\"Name\": \"" + unit.getName() + quoteComma);
            json = append(json, spaces, "\"Strength\": " + unit.getDamage() + ",\n");
            json = append(json, spaces, "\"Health\": " + unit.getHealth() + "\n");

            spaces -= 2;
            if (unitCount != army.getUnitList().size() - 1) {
                json = append(json, spaces, "},\n");
            } else {
                json = append(json, spaces, "}\n");
            }
            unitCount++;
            spaces -= 2;
        }
        return json;
    }

    /**
     * Method that creates into the json file the events of each node.
     *
     * @param json   file that has to be changed
     * @param spaces that have to be taken into account
     * @param node   that has events
     * @return the changed json
     */
    public String createEventsNode(String json, Integer spaces, Node node) {
        int eventCount = 0;
        for (Event event : node.getEventList()) {
            spaces += 2;
            if (eventCount != node.getEventList().size() - 1) {
                json = append(json, spaces, "\"" + event + quoteComma);
            } else {
                json = append(json, spaces, "\"" + event + "\"\n");
            }
            eventCount++;
            spaces -= 2;
        }
        return json;
    }

    /**
     * Method that creates into the json file the events of each edge.
     *
     * @param json   file that has to be changed
     * @param spaces that have to be taken into account
     * @param edge   that has events
     * @return the changed json
     */
    public String createEventsEdge(String json, Integer spaces, Edge edge) {
        int eventCount = 0;
        for (Event event : edge.getEventList()) {
            spaces += 2;
            if (eventCount != edge.getEventList().size() - 1) {
                json = append(json, spaces, "\"" + event + quoteComma);
            } else {
                json = append(json, spaces, "\"" + event + "\"\n");
            }
            eventCount++;
            spaces -= 2;
        }
        return json;
    }

    /**
     * Append function, adds to the json file the "add" string and spaces.
     *
     * @param json   file that is being edited.
     * @param spaces the amount of spaces at the beginning of the file
     * @param add    the string that is being added
     * @return the edited json file
     */
    public String append(String json, int spaces, String add) {
        for (int i = 0; i < spaces; i++) {
            json += " ";
        }
        json = json + add;
        return json;
    }

}
