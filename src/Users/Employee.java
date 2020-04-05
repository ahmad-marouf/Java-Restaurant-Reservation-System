package Users;

import Business.Order;
import Business.Orders;
import Business.Restaurant;
import javafx.scene.control.TreeView;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface Employee {

    TreeView<String> checkReservations(List<Order> orderList);
}
