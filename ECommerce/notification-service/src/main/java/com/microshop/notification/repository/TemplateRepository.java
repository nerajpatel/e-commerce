package com.microshop.notification.repository;

import com.microshop.notification.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    // You can later add custom query methods, for example:
    // Optional<Template> findByTemplateName(String templateName);
}
