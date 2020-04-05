package Users;

import Business.Dish;
import Business.Order;
import Business.Restaurant;
import Business.Table;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager extends User implements Employee {

    public double calculateRevenue(List<Order> orderList){
        double revenue = 0;

        if (orderList != null)
        for (Order order : orderList)
            revenue+=order.getOrderPrice();

        return revenue;
    }

    public TreeView<String> checkReservations(List<Order> orderList) {
        TreeView<String> ordersTree;
        TreeItem<String> orders, orderBranch, dishesBranch;

        orders = new TreeItem<>();
        orders.setExpanded(true);

        if (orderList != null){
            int orderNum = 1;
            for (Order order : orderList) {
                orderBranch = makeBranch("Order " + orderNum, orders);
                makeBranch("Customer : " + order.getOrderCustomer(), orderBranch);
                makeBranch("Table Number :  " + order.getOrderTableNumber(), orderBranch);
                dishesBranch = makeBranch("Dishes : ", orderBranch);
                dishesBranch.setExpanded(false);
                for (int dishNum = 0; dishNum < order.getOrderedDishes().size(); dishNum++)
                    makeBranch("" + order.getOrderedDishes().get(dishNum).getName(), dishesBranch);
                makeBranch("Price : LE " + order.getOrderPrice(), orderBranch);
                orderNum++;
            }
        }
        ordersTree = new TreeView<>(orders);
        ordersTree.setShowRoot(false);
        return ordersTree;
    }


    public static List<String> checkStats(Restaurant restaurant, String comparator){

        List<String> list = new ArrayList<>();
        switch (comparator) {
            case "Customer":
                for (Order order : restaurant.getOrder().getOrders())
                    list.add(order.getOrderCustomer());
                break;
            case "Dish":
                for (Order order : restaurant.getOrder().getOrders())
                    for (Dish dish : order.getOrderedDishes())
                        list.add(dish.getName());
                break;
            case "Smoking":
                for (Order order : restaurant.getOrder().getOrders())
                    for (Table table : restaurant.getTable().getTables())
                        if (table.getNumber() == order.getOrderTableNumber())
                            if (table.isSmoking())
                                list.add("Smokers");
                            else
                                list.add("Non-Smokers");
                break;
        }

        List<String> modes = new ArrayList<>();
        int maxCount=0;
        for (int i = 0; i < list.size(); ++i){
            int count = 0;
            for (int j = 0; j < list.size(); ++j){
                if (list.get(j).equals(list.get(i))) ++count;
            }
            if (count > maxCount){
                maxCount = count;
                modes.clear();
                modes.add( list.get(i) );
            } else if ( count == maxCount && !modes.contains(list.get(i))){
                modes.add( list.get(i) );
            }
        }
        return modes;
    }

    public int newEmployee(Restaurant restaurant, User user) {

            for (User checkTaken : restaurant.getUser().getUsers()) {
                if (checkTaken.getUsername().equals(user.getUsername()))
                    return -1;
                else if (user.getName().equals(checkTaken.getName()))
                    return -2;
                }
        return 0;
    }

    public Manager(String name, String role, String username, String password) {
        super(name, role, username, password);
    }
}
