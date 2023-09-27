package interfeces;

import dto.Client;

import java.util.List;

public interface Iclient extends Ipersonne {
    List<Client> SearchByCode(String code);
    boolean Delete(String code);
    List<Client> Showlist();
    List<Client> SearchByPrenom(String prenom);
    Client Update(Client client);

}
