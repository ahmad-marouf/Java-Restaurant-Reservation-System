package Business;

public class AppetizerDish extends Dish {

    public double calculateTax(){
        double price = this.getPrice();
        price = price + (0.1 * price);
        return price;
    }

    public AppetizerDish(String name, double price, String type) {
        super(name,price,type);
    }
}
