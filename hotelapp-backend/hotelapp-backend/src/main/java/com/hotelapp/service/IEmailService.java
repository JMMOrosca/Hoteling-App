package com.hotelapp.service;

import com.hotelapp.model.Booking;

public interface IEmailService {
    void sendBookingConfirmationEmail(Booking booking);
    void sendBookingCancellationEmail(Booking booking);
}
