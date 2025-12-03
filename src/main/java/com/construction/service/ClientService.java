package com.construction.service;

import com.construction.dto.AdminUserDTO;
import com.construction.dto.ClientDTO;
import com.construction.dto.FullClientDTO;
import com.construction.mapper.BookingMapper;
import com.construction.mapper.ClientMapper;
import com.construction.models.Client;
import com.construction.models.User;
import com.construction.repository.ClientRepository;
import com.construction.repository.UserRepository;
import io.undertow.util.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.construction.security.SecurityUtils.getCurrentUserLogin;
import static java.util.stream.Collectors.toSet;

/**
 * Service Implementation for managing {@link com.construction.models.Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final BookingMapper bookingMapper;


    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, BookingMapper bookingMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.bookingMapper = bookingMapper;
    }

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    // ToDo: возращать корректные данные по авторизации (убрать параметр id)
    public Optional<FullClientDTO> get(Long id) throws BadRequestException {
        log.debug("Request to get Client with all Bookings");
        String currentLogin = getCurrentUserLogin()
                .orElseThrow(() -> new UsernameNotFoundException("Current user by login not found"));


        Client client = this
                .find(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Client with id=%d not found!"
                                .formatted(id)));

        if (client.getUser() == null || !client.getUser().getLogin().equals(currentLogin)) {
            throw new BadRequestException("You are not allowed to get bookings for another client!");
        }

        return Optional.of(this.toDto(client));
    }

    public void createForUser(User user){
        clientRepository.save(new Client(user));
    }

    /**
     * Update a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    public ClientDTO update(ClientDTO clientDTO) {
        log.debug("Request to update Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    /**
     * Partially update a client.
     *
     * @param clientDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClientDTO> partialUpdate(ClientDTO clientDTO) {
        log.debug("Request to partially update Client : {}", clientDTO);

        return clientRepository
            .findById(clientDTO.getId())
            .map(existingClient -> {
                clientMapper.partialUpdate(existingClient, clientDTO);

                return existingClient;
            })
            .map(clientRepository::save)
            .map(clientMapper::toDto);
    }

    /**
     * Get all the clients with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ClientDTO> findAllWithEagerRelationships(Pageable pageable) {
        return clientRepository.findAllWithEagerRelationships(pageable).map(clientMapper::toDto);
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findOneWithEagerRelationships(id).map(clientMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<Client> find(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findOneWithEagerRelationships(id);
    }


    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }

    private FullClientDTO toDto(Client c) {
        return new FullClientDTO(
                c.getId(),
                new AdminUserDTO(c.getUser()),
                c.getBookings().stream().map(bookingMapper::toDto).collect(toSet())
        );
    }
}
