package Users;

import Business.*;
import GUI.AlertBox;
import GUI.Login;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    public void createReservation(Restaurant restaurant, Order order) {

        if (restaurant.getOrder().getOrders() != null)
            restaurant.getOrder().getOrders().add(order);
        else {
            List<Order> orderList = new ArrayList<>();
            orderList.add(order);
            restaurant.getOrder().setOrders(orderList);
        }
        try {
            restaurant.getOrder().saveOrders(restaurant.getOrder());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(Restaurant restaurant, Customer customer, TreeView<String> orderList, VBox viewLayout, Text cancelError) {
        AlertBox alertBox = new AlertBox();
        int offset = 0;
        List<Order> orders = restaurant.getOrder().getOrders();
            try {
                if (orderList.getSelectionModel().getSelectedItem().getParent().getValue().equals("orders"))
                    if(alertBox.display("Are you sure you want to cancel this reservation\nThis cannot be undone")) {
                        for (int i=0; i < orders.size(); i++)
                            if (orders.get(i).getOrderCustomer().equals(customer.getName())){
                                if (offset == orderList.getSelectionModel().getSelectedItem().getParent().getChildren().indexOf(orderList.getSelectionModel().getSelectedItem())){
                                    orders.remove(i);
                                }

                                offset++;
                            }
                    }
                    else return;
            } catch (IndexOutOfBoundsException i1) {}
            restaurant.getOrder().setOrders(orders);
            try {
                restaurant.getOrder().saveOrders(restaurant.getOrder());
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
            try {
                viewLayout.getChildren().remove(cancelError);
                if (orderList.getSelectionModel().getSelectedItem().getParent().getValue().equals("orders"))
                    orderList.getSelectionModel().getSelectedItem().getParent().getChildren().remove(orderList.getSelectionModel().getSelectedItem().getParent().getChildren().indexOf(orderList.getSelectionModel().getSelectedItem()));
                else {
                    viewLayout.getChildren().add(cancelError);
                    cancelError.setText("Please select a reservation header to cancel");
                }
            } catch (IndexOutOfBoundsException i1) {
                viewLayout.getChildren().add(cancelError);
                cancelError.setText("Please select a reservation header to cancel");
            }
    }

    public void selectDish(Dishes allDishes,List<Dish> selectedDishes, Order order ,String name, ListView orderList, Label totalPrice){
        for (Dish dish : allDishes.getDishes())
                if (name.equals(dish.getName())){
                    selectedDishes.add(dish);
                    orderList.getItems().add(dish.getName() + "     LE " + dish.calculateTax());
                    order.setOrderPrice(order.getOrderPrice() + dish.calculateTax());
                    totalPrice.setText("Total Price (Including taxes) : LE " + order.getOrderPrice());
                    break;
                }
    }


    public TreeView<String> checkReservations(List<Order> orderList, String name){
        TreeView<String> ordersTree;
        TreeItem<String> orders, orderBranch, dishesBranch;

        orders = new TreeItem<>("orders");
        orders.setExpanded(true);

        List<Order> customerOrders = new ArrayList<>();
        if (orderList != null) {
            for (Order order : orderList)
                if (name.equals(order.getOrderCustomer()))
                    customerOrders.add(order);

            int orderNum = 1;
            for (Order order : customerOrders) {
                orderBranch = makeBranch("Reservation " + orderNum, orders);
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

    public int newCustomer(Restaurant restaurant){
        for (User checkTaken : restaurant.getUser().getUsers()) {
            if (checkTaken.getUsername().equals(getUsername()))
                return -1;
            else if (getName().equals(checkTaken.getName()))
                return -2;
        }
        return 0;
    }

    public Customer(String name, String role, String username, String password) {
        super(name, role, username, password);
    }
}
