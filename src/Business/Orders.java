package Business;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders {

    @XmlElement(name = "order")
    private List<Order> orders;

    public Orders loadOrders() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Orders.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Orders orders = (Orders) unmarshaller.unmarshal(new File("reservations.xml"));
        return orders;
    }

    public void saveOrders(Orders orders) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Orders.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.marshal(orders, new File("reservations.xml"));
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
