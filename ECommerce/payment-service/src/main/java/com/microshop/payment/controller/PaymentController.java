package com.microshop.payment.controller;

import com.microshop.payment.dto.PaymentRequestDTO;
import com.microshop.payment.dto.PaymentResponseDTO;
import com.microshop.payment.dto.RefundRequestDTO;
import com.microshop.payment.model.Refund;
import com.microshop.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@Valid @RequestBody PaymentRequestDTO request) {
        return ResponseEntity.ok(paymentService.initiatePayment(request));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<PaymentResponseDTO> confirmPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.confirmPayment(id));
    }

    @PatchMapping("/{id}/fail")
    public ResponseEntity<PaymentResponseDTO> failPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.failPayment(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PaymentResponseDTO> cancelPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.cancelPayment(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<Refund> refund(@PathVariable Long paymentId,
                                         @Valid @RequestBody RefundRequestDTO request) {
        return ResponseEntity.ok(paymentService.createRefund(paymentId, request));
    }

    @GetMapping("/{paymentId}/refunds")
    public ResponseEntity<List<Refund>> getRefunds(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getRefunds(paymentId));
    }
}
