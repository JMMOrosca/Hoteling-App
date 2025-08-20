package com.hotelapp.service.impl;

import com.hotelapp.model.Booking;
import com.hotelapp.model.Customer;
import com.hotelapp.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendBookingConfirmationEmail(Booking booking) {
        Customer customer = booking.getCustomer();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Hotel Booking Confirmation - Booking #" + booking.getId());
        message.setText(buildBookingConfirmationText(booking));

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    @Override
    public void sendBookingCancellationEmail(Booking booking) {
        Customer customer = booking.getCustomer();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Hotel Booking Cancellation - Booking #" + booking.getId());
        message.setText(buildBookingCancellationText(booking));

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    private String buildBookingConfirmationText(Booking booking) {
        return String.format(
                """
                        Dear %s %s,
                        
                        Your hotel booking has been confirmed!
                        
                        Booking Details:
                        Booking ID: %d
                        Room: %s (%s)
                        Check-in Date: %s
                        Check-out Date: %s
                        Number of Guests: %d
                        Total Amount: $%.2f
                        
                        Thank you for choosing our hotel!
                        
                        Best regards,
                        Hotel Management""",
                booking.getCustomer().getFirstName(),
                booking.getCustomer().getLastName(),
                booking.getId(),
                booking.getRoom().getRoomNumber(),
                booking.getRoom().getRoomType(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getNumberOfGuests(),
                booking.getTotalAmount()
        );
    }

    private String buildBookingCancellationText(Booking booking) {
        return String.format(
                """
                        Dear %s %s,
                        
                        Your hotel booking has been cancelled.
                        
                        Cancelled Booking Details:
                        Booking ID: %d
                        Room: %s (%s)
                        Check-in Date: %s
                        Check-out Date: %s
                        
                        If you have any questions, please contact us.
                        
                        Best regards,
                        Hotel Management""",
                booking.getCustomer().getFirstName(),
                booking.getCustomer().getLastName(),
                booking.getId(),
                booking.getRoom().getRoomNumber(),
                booking.getRoom().getRoomType(),
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );
    }
}
