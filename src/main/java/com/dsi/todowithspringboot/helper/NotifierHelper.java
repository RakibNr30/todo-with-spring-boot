package com.dsi.todowithspringboot.helper;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public final class NotifierHelper {
    private RedirectAttributes attributes;

    private NotifierHelper() {}

    public NotifierHelper(RedirectAttributes attributes) {
        this.attributes = attributes;
    }

    public NotifierHelper message(String message) {
        this.attributes.addFlashAttribute("notifierMessage", message);
        return this;
    }

    public void success() {
        this.attributes.addFlashAttribute("notifierStatus", "success");
        this.attributes.addFlashAttribute("notifierBg", "bg-success");
        this.attributes.addFlashAttribute("notifierIcon", "fas fa-check-circle");
        this.attributes.addFlashAttribute("notifierTitle", "Success!");
    }

    public void warning() {
        this.attributes.addFlashAttribute("notifierStatus", "warning");
        this.attributes.addFlashAttribute("notifierBg", "bg-warning");
        this.attributes.addFlashAttribute("notifierIcon", "fas fa-exclamation-triangle");
        this.attributes.addFlashAttribute("notifierTitle", "Warning!");
    }

    public void error() {
        this.attributes.addFlashAttribute("notifierStatus", "error");
        this.attributes.addFlashAttribute("notifierBg", "bg-danger");
        this.attributes.addFlashAttribute("notifierIcon", "fas fa-times-circle");
        this.attributes.addFlashAttribute("notifierTitle", "Error!");
    }
}
