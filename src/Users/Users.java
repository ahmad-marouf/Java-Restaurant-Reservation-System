package Users;

import Business.Restaurant;
import Business.Table;

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

@XmlRootElement(name ="users")
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {

    @XmlElement(name = "user")
    private List<User> users;

    public User authenticate(String username, String password){

        for (User user : users){
            if (username.equals(user.getUsername()))
                if (password.equals(user.getPassword()))
                    return user;
        }
        return null;
    }

    public void createUser(User user) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Restaurant.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Restaurant restaurant = (Restaurant) unmarshaller.unmarshal(new File("input.xml"));
        restaurant.getUser().getUsers().add(user);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(restaurant,new File("input.xml"));
        restaurant.assignUsers();
    }

    public List<User> getUsers() { return users; }

    public void setUsers(List<User> users) { this.users = users; }
}
