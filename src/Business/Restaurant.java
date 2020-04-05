package Business;

import Users.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "restaurant")
@XmlAccessorType(XmlAccessType.FIELD)
public class Restaurant {

    @XmlElement(name = "users")
    private Users user = null;

    @XmlElement(name = "tables")
    private Tables table = null;

    @XmlElement(name = "dishes")
    private Dishes dish = null;

    @XmlElement(name = "orders")
    private Orders order = null;

    public void loadRestaurant() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Restaurant.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Restaurant restaurant = (Restaurant) unmarshaller.unmarshal(new File("input.xml"));
        Orders orders = (Orders) unmarshaller.unmarshal(new File("reservations.xml"));

        setUser(restaurant.getUser());
        setTable(restaurant.getTable());
        setDish(restaurant.getDish());
        setOrder(orders);
    }

    public void assignUsers() {
        List<User> userList = new ArrayList<>();
        for (User user : user.getUsers()){
            switch(user.getRole()){
                case "Client":
                    Customer customer = new Customer(user.getName(), user.getRole(), user.getUsername(), user.getPassword());
                    userList.add(customer);
                    break;
                case "Manager":
                    Manager manager = new Manager(user.getName(), user.getRole(), user.getUsername(), user.getPassword());
                    userList.add(manager);
                    break;
                case "Waiter":
                    Waiter waiter = new Waiter(user.getName(), user.getRole(), user.getUsername(), user.getPassword());
                    userList.add(waiter);
                    break;
                case "Cooker":
                    Cooker cooker = new Cooker(user.getName(), user.getRole(), user.getUsername(), user.getPassword());
                    userList.add(cooker);
                    break;
            }
        }
        Users assignedUsers = new Users();
        assignedUsers.setUsers(userList);
        setUser(assignedUsers);
    }

    public void assignDishes(){
        List<Dish> dishList = new ArrayList<>();
        for (Dish dish : dish.getDishes()) {
            switch (dish.getType()) {
                case "appetizer":
                    AppetizerDish appetizerDish = new AppetizerDish(dish.getName(), dish.getPrice(), dish.getType());
                    dishList.add(appetizerDish);
                    break;
                case "main_course":
                    MainCourseDish mainCourseDish = new MainCourseDish(dish.getName(), dish.getPrice(), dish.getType());
                    dishList.add(mainCourseDish);
                    break;
                case "desert":
                    DessertDish dessertDish = new DessertDish(dish.getName(), dish.getPrice(), dish.getType());
                    dishList.add(dessertDish);
                    break;
            }
            Dishes assignedDishes = new Dishes();
            assignedDishes.setDishes(dishList);
            setDish(assignedDishes);
        }
    }

    public Users getUser() { return user; }

    public void setUser(Users user) { this.user = user; }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Dishes getDish() { return dish; }

    public void setDish(Dishes dish) {
        this.dish = dish;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
