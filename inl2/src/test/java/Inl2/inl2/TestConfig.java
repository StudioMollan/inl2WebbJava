package Inl2.inl2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.util.Locale;

@Configuration
public class TestConfig {

    @Bean
    public NumberFormat defaultCurrencyFormat() {
        return NumberFormat.getCurrencyInstance();
    }

    @Bean
    public NumberFormat germanCurrencyFormat() {
        return NumberFormat.getCurrencyInstance(Locale.GERMAN);
    }
}

