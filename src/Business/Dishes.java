package Business;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.util.List;

@XmlRootElement(name = "dishes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dishes {

    @XmlElement(name = "dish")
    private List<Dish> dishes;

    public void findDish(String menuValue, Label price, ImageView imageView) {
        for (Dish dish : dishes)
            try {
                if (menuValue.equals(dish.getName())) {
                    price.setText("Price: LE " + dish.getPrice());
                    try {
                        imageView.setImage(dish.getImage());
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (NullPointerException n1) {}
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
