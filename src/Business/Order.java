package Business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlElement(name = "orderCustomer")
    private String orderCustomer;

    @XmlElement(name = "orderTableNumber")
    private int orderTableNumber;

    @XmlElement(name = "orderedDishes")
    private List<Dish> orderedDishes;

    @XmlElement(name = "orderPrice")
    private double orderPrice;

    public String getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(String orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public int getOrderTableNumber() {
        return orderTableNumber;
    }

    public void setOrderTableNumber(int orderTableNumber) {
        this.orderTableNumber = orderTableNumber;
    }

    public List<Dish> getOrderedDishes() {
        return orderedDishes;
    }

    public void setOrderedDishes(List<Dish> orderedDishes) {
        this.orderedDishes = orderedDishes;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }
}
