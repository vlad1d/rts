package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Army;
import nl.rug.oop.rts.model.Faction;
import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;
import nl.rug.oop.rts.view.ArmyAdditionScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that adds an army to a node depending on what was selected from the panel.
 */
public class AddArmyAction implements ActionListener {
    private Graph graph;

    public AddArmyAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (graph.getSelectedNode() != null) {
            Faction selectedFaction = ArmyAdditionScreen.showFactions();
            if (selectedFaction != null) {
                Node selectedNode = graph.getSelectedNode();
                Army army = new Army(selectedFaction);
                selectedNode.addArmy(army);
                army.setLocation(selectedNode, null);
                graph.notifyListeners();
            }
        }
    }
}
