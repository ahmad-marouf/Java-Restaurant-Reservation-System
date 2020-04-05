package Business;

public class MainCourseDish extends Dish {

    public double calculateTax(){
        double price = this.getPrice();
        price = price + (0.15 * price);
        return price;
    }

    public MainCourseDish(String name, double price, String type) {
        super(name, price, type);
    }
}


