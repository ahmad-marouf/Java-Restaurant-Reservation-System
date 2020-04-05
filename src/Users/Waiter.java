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

public class Waiter extends User implements Employee {

    public TreeView<String> checkReservations(List<Order> orderList) {
        TreeView<String> ordersTree;
        TreeItem<String> orders, orderBranch, dishesBranch;

        orders = new TreeItem<>();
        orders.setExpanded(true);

        if (orderList != null) {
            int orderNum = 1;
            for (Order order : orderList) {
                orderBranch = makeBranch("Order " + orderNum, orders);
                makeBranch("Customer : " + order.getOrderCustomer(), orderBranch);
                makeBranch("Table Number :  " + order.getOrderTableNumber(), orderBranch);
                orderNum++;
            }
        }

            ordersTree = new TreeView<>(orders);
            ordersTree.setShowRoot(false);

            return ordersTree;
    }


    public Waiter(String name, String role, String username, String password) {
        super(name, role, username, password);
    }
}
