package com.microshop.payment.service;

import com.microshop.payment.dto.PaymentRequestDTO;
import com.microshop.payment.dto.PaymentResponseDTO;
import com.microshop.payment.dto.RefundRequestDTO;
import com.microshop.payment.model.*;
import com.microshop.payment.repository.PaymentMethodRepository;
import com.microshop.payment.repository.PaymentRepository;
import com.microshop.payment.repository.RefundRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final PaymentMethodRepository methodRepository;

    public PaymentService(PaymentRepository paymentRepository, RefundRepository refundRepository, PaymentMethodRepository methodRepository) {
        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
        this.methodRepository = methodRepository;
    }

    public PaymentResponseDTO initiatePayment(PaymentRequestDTO request) {
        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .method(PaymentMethodType.valueOf(request.getMethod()))
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        return toResponse(saved);
    }

    public PaymentResponseDTO confirmPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        return toResponse(payment);
    }

    public PaymentResponseDTO failPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
        return toResponse(payment);
    }

    public PaymentResponseDTO cancelPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.CANCELLED);
        paymentRepository.save(payment);
        return toResponse(payment);
    }

    public List<PaymentResponseDTO> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponseDTO getPaymentByOrder(Long orderId) {
        return toResponse(paymentRepository.findByOrderId(orderId));
    }

    public Refund createRefund(Long paymentId, RefundRequestDTO request) {
        Refund refund = Refund.builder()
                .paymentId(paymentId)
                .refundAmount(request.getRefundAmount())
                .status(RefundStatus.INITIATED)
                .createdAt(LocalDateTime.now())
                .build();

        return refundRepository.save(refund);
    }

    public List<Refund> getRefunds(Long paymentId) {
        return refundRepository.findByPaymentId(paymentId);
    }

    private PaymentResponseDTO toResponse(Payment payment) {
        return PaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .method(payment.getMethod().name())
                .status(payment.getStatus().name())
                .build();
    }
}
