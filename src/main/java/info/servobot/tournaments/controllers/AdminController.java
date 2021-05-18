package info.servobot.tournaments.controllers;

import info.servobot.tournaments.data.serializers.OrganizationSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final OrganizationSerializer organizationSerializer;

    @GetMapping
    public String admin(final Model model) {
        model.addAttribute("organizations", organizationSerializer.loadOrganizations());
        return "admin";
    }

    @ModelAttribute
    private void addUsers(final Model model) {
        model.addAttribute("users", null);
    }

    @ModelAttribute
    private void addMemory(final Model model) {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        model.addAttribute("total_memory", formatMemory(totalMemory));
        model.addAttribute("free_memory", formatMemory(freeMemory));
        model.addAttribute("used_memory", formatMemory(totalMemory-freeMemory));
    }

    private String formatMemory(final long amount) {
        if (amount > 1024 * 1024) {
            return String.format("%.2f MB", amount/1024./1024);
        }

        if (amount > 1024) {
            return String.format("%.2f KB", amount/1024.);
        }

        return amount + " B";
    }
}