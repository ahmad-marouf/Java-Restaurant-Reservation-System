package Users;

import Business.Order;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "role")
    private String role;

    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "password")
    private String password;



    public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {

        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;

    }

    public User() {}

    public User(String name, String role, String username, String password) {
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
