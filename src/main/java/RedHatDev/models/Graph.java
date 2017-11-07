package RedHatDev.models;

import RedHatDev.abstraction.ShortestPathTools;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Graph implements ShortestPathTools {

    private final List<String[]> nodesData;
    private final Set<Node> nodes = new HashSet<>();

    public Graph(List<String[]> nodesData) {
        this.nodesData = nodesData;
        createGraph();
    }

    private void createGraph() {
        Set<String> nodeNames = getNodesNames();
        createNodes(nodeNames);
        connectNodesInBothDirections();
    }

    private Set<String> getNodesNames( ) {
        Set<String> nodeNames = new HashSet<>();

        for (String[] record : this.nodesData) {
            nodeNames.add(getFromName.apply(record));
            nodeNames.add(getToName.apply(record));
        }

        return nodeNames;
    }


    private void createNodes(Set<String> nodeNames) {
        for (String name : nodeNames) {
            this.nodes.add(new Node(name));
        }
    }

    private void connectNodesInBothDirections( ) {


        for (String[] record : this.nodesData) {
            String recordFromName = getFromName.apply(record);
            String recordToName = getToName.apply(record);

            Node fromNode = findNodeByName(recordFromName);
            Node toNode = findNodeByName(recordToName);

            Integer price = getPriceAsInt.apply(record);
            fromNode.addDestination(toNode, price);
            toNode.addDestination(fromNode, price);
        }
    }

    public Node findNodeByName(String givenName) {
        return this.nodes.stream()
                         .filter(node -> areEqual.test(node.getName(), givenName))
                         .findAny()
                         .get();
    }


}
