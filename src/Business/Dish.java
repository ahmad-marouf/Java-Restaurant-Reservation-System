package Business;

import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@XmlRootElement(name = "dish")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dish {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "price")
    private double price;

    @XmlElement(name = "type")
    private String type;


    public double calculateTax() {
        return price + (price * 0.15);
    }

    public Image getImage() throws FileNotFoundException {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream("Dish Images\\"+this.name+".jpg");
        } catch (FileNotFoundException f1) {
            inputStream = new FileInputStream("Dish Images\\No image.jpg");
        }
        Image image = new Image(inputStream);
        return image;
    }

    public Dish() {}

    public Dish(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
