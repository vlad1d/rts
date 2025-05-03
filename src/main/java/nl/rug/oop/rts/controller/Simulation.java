package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Simulation class that creates a simulation of the armies by moving them.
 */
public class Simulation implements ActionListener {
    private Graph graph;

    public Simulation(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Army> armyList = graph.getArmies();
        int counter = resolveBattleNodes();
        if (counter == 0) {
            moveArmies(armyList);
            resolveBattleNodes();
            resolveBattleEdges();
        }
    }

    /**
     * Method takes armies and checks whether they are on edge or node, moves them on the closest edge/node.
     *
     * @param armyList that is positioned on a node/edge and is about to move.
     */
    public void moveArmies(List<Army> armyList) {
        for (Army army : armyList) {
            Node node = army.getNode();
            Edge edge = army.getEdge();
            if (edge != null && node != null) {
                moveToNode(node, edge, army);
                node = army.getNode();
                edge = null;
                if (army.getNode() != null) {
                    resolveBattleNode(node);
                    checkEventsNode(node, armyList);
                }
                graph.notifyListeners();
            } else if (node != null) {
                moveToEdge(node, edge, army);
                node = army.getNode();
                edge = army.getEdge();
                if (edge != null) {
                    resolveBattleEdge(edge);
                    checkEventsEdge(edge, armyList);
                }
                graph.notifyListeners();
            }
        }
    }

    /**
     * Method that checks if there are any events in a node and takes a random one.
     *
     * @param node     which is checked for events
     * @param armyList that is positioned on the node
     */
    public void checkEventsNode(Node node, List<Army> armyList) {
        List<Event> eventList = node.getEventList();
        Event chosenEvent = getEvent(eventList);
        if (chosenEvent != null) {
            chosenEvent.handleEvent(graph, node);
        }
    }

    /**
     * Method that checks if there are any events in an edge and takes a random one.
     *
     * @param edge     which is checked for events
     * @param armyList that is positioned on the edge
     */
    public void checkEventsEdge(Edge edge, List<Army> armyList) {
        List<Event> eventList = edge.getEventList();
        Event chosenEvent = getEvent(eventList);
        if (chosenEvent != null) {
            chosenEvent.handleEvent(graph, edge);
        }
    }

    /**
     * Method that finalizes moving to a node, by looking for the other node and changing the location of the army.
     *
     * @param node that an army was positioned on before commuting to the edge
     * @param edge that an army is positioned on
     * @param army that is positioned on the edge
     */
    public void moveToNode(Node node, Edge edge, Army army) {
        Node otherNode = null;
        if (node == edge.getStart()) {
            otherNode = edge.getEnd();
        } else if (node == edge.getEnd()) {
            otherNode = edge.getStart();
        }
        edge.removeArmy(army);
        otherNode.addArmy(army);
        army.setLocation(otherNode, null);
    }

    /**
     * Method that finalizes moving to an edge, by looking for a random one and changing the location of the army.
     *
     * @param node that an army was positioned on
     * @param edge that an army will move on, will change in the method
     * @param army that is positioned on the node
     */
    public void moveToEdge(Node node, Edge edge, Army army) {
        List<Edge> edgeList = node.getEdgeList();
        Random random = new Random();
        int edgeSize = edgeList.size();
        if (edgeSize != 0) {
            edge = edgeList.get(random.nextInt(edgeSize));
            node.removeArmy(army);
            edge.addArmy(army);
            army.setLocation(node, edge);
        }
    }

    /**
     * Method that resolves a battle by checking how many teams ar on nodes; if there are more than 2, it calculates.
     * their strength and makes them fight until one is left. Loser team is removed.
     *
     * @return 1 if there are 2 or more teams on the nodes, 0 otherwise.
     */
    public int resolveBattleNodes() {
        int counter = 0;
        List<Node> nodeList = graph.getNodes();
        for (Node node : nodeList) {
            counter += resolveBattleNode(node);
        }
        return counter >= 1 ? 1 : 0;
    }

    /**
     * Method that resolves a battle by checking how many teams ar on edges; if there are more than 2, it calculates.
     * their strength and makes them fight until one is left. Loser team is removed.
     *
     * @return 1 if there are 2 or more teams on the edges, 0 otherwise.
     */
    public int resolveBattleEdges() {
        int counter = 0;
        List<Edge> edgeList = graph.getEdges();
        for (Edge edge : edgeList) {
            counter += resolveBattleEdge(edge);
        }
        return counter >= 1 ? 1 : 0;
    }

    /**
     * Method for resolving battle on one edge. Applies same principle.
     *
     * @param edge that is being checked
     * @return 1 if there are 2 teams or more, 0 otherwise
     */
    public int resolveBattleEdge(Edge edge) {
        int counter = 0;
        List<Army> armyList = edge.getArmyList();
        Map<Team, Integer> teamHealth = new HashMap<>();
        Map<Team, Integer> teamDamage = new HashMap<>();

        int teamCounter = calculateTeams(armyList, teamHealth, teamDamage);

        if (teamCounter >= 2) {
            List<Team> teamList = Team.getTeams();
            Team loser = fight(teamList, teamHealth, teamDamage);
            removeTeamEdge(loser, armyList, edge);
            counter = 1;
        }
        return counter;
    }

    /**
     * Method for resolving battle on one node. Applies same principle.
     *
     * @param node that is being checked
     * @return 1 if there are 2 teams or more, 0 otherwise
     */
    public int resolveBattleNode(Node node) {
        int counter = 0;
        List<Army> armyList = node.getArmyList();
        Map<Team, Integer> teamHealth = new HashMap<>();
        Map<Team, Integer> teamDamage = new HashMap<>();

        int teamCounter = calculateTeams(armyList, teamHealth, teamDamage);

        if (teamCounter >= 2) {
            List<Team> teamList = Team.getTeams();
            Team loser = fight(teamList, teamHealth, teamDamage);
            removeTeamNode(loser, armyList, node);
            counter = 1;
        }
        return counter;
    }

    /**
     * Method that goes through the armies and uses hash tables to calculate for each team the common health and damage.
     *
     * @param armies     that will compare their health and damage.
     * @param teamHealth the hash table which will be filled with health of teams.
     * @param teamDamage the hash table which will be filled with damage of teams.
     * @return how many teams there are.
     */
    public int calculateTeams(List<Army> armies, Map<Team, Integer> teamHealth, Map<Team, Integer> teamDamage) {
        int teamCounter = 0;
        for (Army army : armies) {
            Team team = army.getTeam();
            int health = army.getHealth();
            int damage = army.getDamage();
            int currentHealth = teamHealth.getOrDefault(team, 0);
            int currentDamage = teamDamage.getOrDefault(team, 0);
            if (currentDamage == 0 && currentHealth == 0) {
                teamCounter++;
            }
            teamHealth.put(team, currentHealth + health);
            teamDamage.put(team, currentDamage + damage);
        }
        return teamCounter;
    }

    /**
     * Method for fighting, it reduces the teams health until one of them remains superior and is selected victorious.
     *
     * @param teamList   list of teams
     * @param teamHealth hash table with health of teams
     * @param teamDamage hash table with damage of teams
     * @return the loser team
     */
    public Team fight(List<Team> teamList, Map<Team, Integer> teamHealth, Map<Team, Integer> teamDamage) {
        Team victory = null;
        Team team1 = teamList.get(0);
        Team team2 = teamList.get(1);

        while (teamHealth.get(team1) > 0 && teamHealth.get(team2) > 0) {
            int damage1 = teamDamage.get(team1);
            int damage2 = teamDamage.get(team2);
            teamHealth.put(team1, teamHealth.getOrDefault(team1, 0) - damage2);
            teamHealth.put(team2, teamHealth.getOrDefault(team2, 0) - damage1);
        }
        int health1 = teamHealth.get(team1);
        int health2 = teamHealth.get(team2);

        return (health1 > health2) ? team2 : team1;
    }

    /**
     * Removes a team from a node, goes through all armies and checks which is of the loser team.
     *
     * @param team   the loser team
     * @param armies list of armies at a node
     * @param node   node on which armies are positioned
     */
    public void removeTeamNode(Team team, List<Army> armies, Node node) {
        List<Army> removeArmy = new ArrayList<>();
        for (Army army : armies) {
            if (team.equals(army.getTeam())) {
                removeArmy.add(army);
            }
        }
        for (Army army : removeArmy) {
            node.removeArmy(army);
        }
        graph.notifyListeners();
    }

    /**
     * Removes a team from an edge, goes through all armies and checks which is of the loser team.
     *
     * @param team   the loser team
     * @param armies list of armies at an edge
     * @param edge   on which armies are positioned
     */
    public void removeTeamEdge(Team team, List<Army> armies, Edge edge) {
        List<Army> removeArmy = new ArrayList<>();
        for (Army army : armies) {
            if (team.equals(army.getTeam())) {
                removeArmy.add(army);
            }
        }
        for (Army army : removeArmy) {
            edge.removeArmy(army);
        }
        graph.notifyListeners();
    }

    /**
     * Chooses an event from the list, while keeping in account a probability.
     *
     * @param eventList with available events.
     * @return the chosen event
     */
    public Event getEvent(List<Event> eventList) {
        Random random = new Random();
        if (eventList.isEmpty() || random.nextInt(11) % 5 != 0) {
            return null;
        }
        int eventIndex = random.nextInt(eventList.size());
        Event chosenEvent = eventList.get(eventIndex);
        return chosenEvent;
    }
}
