package Users;

import Business.Order;
import Business.Restaurant;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class Cooker extends User implements Employee {

    public TreeView<String> checkReservations(List<Order> orderList){
        TreeView<String> ordersTree;
        TreeItem<String> orders, orderBranch, dishesBranch;

        orders = new TreeItem<>();
        orders.setExpanded(true);

        if (orderList != null) {
            int orderNum = 1;
            for (Order order : orderList) {
                orderBranch = makeBranch("Order " + orderNum, orders);
                makeBranch("Table Number :  " + order.getOrderTableNumber(), orderBranch);
                dishesBranch = makeBranch("Dishes : ", orderBranch);
                for (int dishNum = 0; dishNum < order.getOrderedDishes().size(); dishNum++)
                    makeBranch("" + order.getOrderedDishes().get(dishNum).getName(), dishesBranch);
                orderNum++;
            }
        }

        ordersTree = new TreeView<>(orders);
        ordersTree.setShowRoot(false);

        return ordersTree;
    }

    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {

        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;

    }

    public Cooker(String name, String role, String username, String password) {
        super(name, role, username, password);
    }
}
