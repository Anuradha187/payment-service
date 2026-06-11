package com.payment.factory;

import java.util.List; import java.util.Map;
import java.util.function.Function; 
import java.util.stream.Collectors;
import org.springframework.stereotype.Component; 
import com.payment.entity.enums.PaymentMethod;
import com.payment.exception.UnsupportedPaymentMethodException;
import com.payment.processor.PaymentProcessor;

@Component 
public class PaymentProcessorFactory {

private final Map<PaymentMethod, PaymentProcessor> processors;
public PaymentProcessorFactory(List<PaymentProcessor> processorList) {
this.processors = processorList.stream()
                                .collect(Collectors.toMap(
                                 PaymentProcessor::getPaymentMethod,
                                 Function.identity()));

}
public PaymentProcessor getProcessor(PaymentMethod method) {
PaymentProcessor processor = processors.get(method);
 if (processor == null) {
 throw new UnsupportedPaymentMethodException(
"Unsupported payment method" + method);
 }
 return processor;

}
}
