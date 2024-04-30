package com.xyz.service.impl;

import com.xyz.controller.TheatreController;
import com.xyz.exception.BookingException;
import com.xyz.model.BookingRequest;
import com.xyz.model.BookingResponse;
import com.xyz.model.ShowSlot;
import com.xyz.model.Util;
import com.xyz.repo.BookingRepo;
import com.xyz.service.BookingService;
import com.xyz.service.MovieService;
import com.xyz.service.RunningSlotService;
import com.xyz.service.TheatreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;


@Service
public class BookingServiceImpl implements BookingService {

    Logger logger = LogManager.getLogger(TheatreController.class);

    @Autowired
    TheatreService theatreService;
    @Autowired
    MovieService movieService;

    @Autowired
    RunningSlotService slotService;

    @Autowired
    BookingRepo bookingRepo;

    @Override
    public Optional<BookingResponse> bookTicket(BookingRequest bookingRequest) throws BookingException {

        String slotId = bookingRequest.getRequestSlotId();

        Optional<ShowSlot> dbShowSlotOP = slotService.findById(slotId);
        if (dbShowSlotOP.isPresent()) {
            ShowSlot dbSlot = dbShowSlotOP.get();

            if (dbSlot.getBookedSeats() != dbSlot.getTotalNumberOfSeats()) {

                int bookedSeats = dbSlot.getBookedSeats();
                logger.info("Booking Seats:  total Seats:" + dbSlot.getTotalNumberOfSeats() + " bookedSeats:" + bookedSeats + " available:" + (dbSlot.getTotalNumberOfSeats() - bookedSeats));
                if (bookingRequest.getNumberOfTickets() <= (dbSlot.getTotalNumberOfSeats() - bookedSeats)) {
                    dbSlot.setBookedSeats(bookedSeats + bookingRequest.getNumberOfTickets());
                    slotService.save(dbSlot);
                    logger.info("Booking " + bookingRequest);

                    BookingResponse response = getBookingResponse(bookingRequest, dbSlot);
                    return Optional.of(bookingRepo.save(response));

                } else {
                    logger.error("All requested Seats not available");
                    throw new BookingException("All requested Seats not available");
                }
            } else {

                logger.error("Booking full. booked: " + dbSlot.getBookedSeats() + " total seats " + dbSlot.getTotalNumberOfSeats());
                throw new BookingException("seats not available");
            }

        } else {
            logger.error("Requested Show Slot not found");
            throw new BookingException("Show not found");
        }

//        return Optional.empty();
    }

    private BookingResponse getBookingResponse(BookingRequest bookingRequest, ShowSlot dbSlot) {
        int ticketCount = bookingRequest.getNumberOfTickets();
        int disTickCount = ticketCount / 3;
        double discount = disTickCount * Util.TICKET_PRICE - disTickCount * Util.TICKET_PRICE * .50;
        logger.info("Booking disTickCount " + disTickCount + "  discount " + discount);
        double totalPrice = (ticketCount - disTickCount) * Util.TICKET_PRICE + disTickCount * Util.TICKET_PRICE * .50;

        LocalTime noon = LocalTime.NOON;
        int h = dbSlot.getShowStartTime().getHour();
        if (dbSlot.getShowStartTime().isAfter(LocalTime.NOON) && dbSlot.getShowStartTime().isBefore(noon.plusHours(5))) {

            totalPrice = totalPrice + -totalPrice * .50;
            discount = disTickCount + totalPrice * .50;
        }


        logger.info("Booking total price " + totalPrice);
        BookingResponse response = new BookingResponse();
        response.setRequestSlotId(dbSlot.getId());
        response.setNumberOfTickets(bookingRequest.getNumberOfTickets());
        response.setTotalPrice(totalPrice);
        response.setDiscount(discount);
        response.setTicketPrice(Util.TICKET_PRICE);
        return response;
    }
}
