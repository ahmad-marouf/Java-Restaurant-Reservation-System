package Business;

public class DessertDish extends Dish {

    public double calculateTax(){
        double price = this.getPrice();
        price = price + (0.2 * price);
        return price;
    }

    public DessertDish(String name, double price, String type) {
        super(name,price,type);
    }
}
