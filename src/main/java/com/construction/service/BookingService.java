package com.construction.service;

import com.construction.dto.BookingDTO;
import com.construction.dto.SimpleBookingDTO;
import com.construction.mapper.BookingMapper;
import com.construction.mapper.UnitMapper;
import com.construction.models.Booking;
import com.construction.models.Client;
import com.construction.models.Unit;
import com.construction.repository.BookingRepository;
import io.undertow.util.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.construction.models.enumeration.UnitStatus.AVAILABLE;
import static com.construction.models.enumeration.UnitStatus.RESERVED;
import static com.construction.security.SecurityUtils.getCurrentUserLogin;

/**
 * Service Implementation for managing {@link com.construction.models.Booking}.
 */
@Service
@Transactional
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final ClientService clientService;
    private final UnitService unitService;
    private final UnitMapper unitMapper;


    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, ClientService clientService, UnitService unitService, UnitMapper unitMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.clientService = clientService;
        this.unitService = unitService;
        this.unitMapper = unitMapper;
    }

    /**
     * Save a booking.
     *
     * @param bookingDTO the entity to save.
     * @return the persisted entity.
     */
    public BookingDTO save(BookingDTO bookingDTO) {
        log.debug("Request to save Booking : {}", bookingDTO);
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    public BookingDTO create(SimpleBookingDTO dto) throws BadRequestException {
        log.debug("Request to create Booking : {}", dto);
        String currentLogin = getCurrentUserLogin()
                .orElseThrow(() -> new UsernameNotFoundException("Current user by login not found"));


        Client client = clientService
                .find(dto.getClientId())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Client with id=%d not found!"
                                .formatted(dto.getClientId())));

        if (client.getUser() == null || !client.getUser().getLogin().equals(currentLogin)) {
            throw new BadRequestException("You are not allowed to create bookings for another client!");
        }

        Unit unit = unitService
                .find(dto.getUnitId())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Unit with id=%d not found!"
                                .formatted(dto.getUnitId())));

        if (!unit.getStatus().equals(AVAILABLE)){
            throw new BadRequestException("Unit is already reserved!");
        }

        unit.setStatus(RESERVED);
        unitService.save(unitMapper.toDto(unit));

        Booking booking = new Booking(
                dto.getNote(),
                client,
                unit
        );
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    /**
     * Update a booking.
     *
     * @param bookingDTO the entity to save.
     * @return the persisted entity.
     */
    public BookingDTO update(BookingDTO bookingDTO) {
        log.debug("Request to update Booking : {}", bookingDTO);
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking = bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    /**
     * Partially update a booking.
     *
     * @param bookingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BookingDTO> partialUpdate(BookingDTO bookingDTO) {
        log.debug("Request to partially update Booking : {}", bookingDTO);

        return bookingRepository
            .findById(bookingDTO.getId())
            .map(existingBooking -> {
                bookingMapper.partialUpdate(existingBooking, bookingDTO);

                return existingBooking;
            })
            .map(bookingRepository::save)
            .map(bookingMapper::toDto);
    }

    /**
     * Get one booking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookingDTO> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id).map(bookingMapper::toDto);
    }

    /**
     * Delete the booking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
    }
}
