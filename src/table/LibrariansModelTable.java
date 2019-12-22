package table;

public class LibrariansModelTable {

    String id,name,surname,contact_num,email;

    public LibrariansModelTable(String id, String name, String surname, String contact_num, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.contact_num = contact_num;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getContact_num() {
        return contact_num;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
