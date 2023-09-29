package interfeces;

import dto.Client;

import java.util.List;
import java.util.Optional;

public interface Iclient extends Ipersonne {
    Optional<List<Client>> SearchByCode(String code);
    boolean Delete(String code);
    Optional<List<Client>> Showlist() ;
    Optional<List<Client>> SearchByPrenom(String prenom);
    Optional<Client> Update(Client client);
}
