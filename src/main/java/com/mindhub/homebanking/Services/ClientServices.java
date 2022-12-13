package com.mindhub.homebanking.Services;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;
public interface ClientServices {
    public List<ClientDTO> getClientDTO();
    public ClientDTO getClientbyId(long id);
    public Client findByEmail(String email);
    public void saveClient(Client client);
}
