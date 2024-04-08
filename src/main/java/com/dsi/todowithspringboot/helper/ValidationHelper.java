package com.dsi.todowithspringboot.helper;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class ValidationHelper {
    private RedirectAttributes attributes;
    private String modelString;

    private ValidationHelper() {}

    public ValidationHelper(RedirectAttributes attributes) {
        this.attributes = attributes;
    }

    public ValidationHelper model(String modelString, Object model) {
        this.modelString = modelString;
        this.attributes.addFlashAttribute(modelString, model);
        return this;
    }

    public void bind(BindingResult result) {
        this.attributes.addFlashAttribute("org.springframework.validation.BindingResult." + this.modelString, result);
    }
}
