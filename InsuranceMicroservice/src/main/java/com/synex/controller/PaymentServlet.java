package com.synex.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.synex.domain.PaymentIntentRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentServlet {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        Stripe.apiKey = stripeSecretKey;
        
      
        System.out.println(request.getAmount());
        try {
         
            long amount = request.getAmount() * 100;
            PaymentMethod paymentMethod = PaymentMethod.retrieve(request.getPaymentMethodId());
            System.out.println("paymentMethod" + paymentMethod.getType());

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setCurrency("usd")
                    .setAmount(amount)
                    .setPaymentMethod(request.getPaymentMethodId())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            System.out.println(paymentIntent);
            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment intent");
        }
    }
}

