package com.example.test_driven_development.dashBoard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashBoardService dashBoardService;

    public DashboardController(DashBoardService service){
        this.dashBoardService = service;
    }

    @GetMapping
    public String getDashBoardView(Model model){
        model.addAttribute("user", "Duke");
        model.addAttribute("analyticsGraph",dashBoardService.getAnalyticsGraphData());
        model.addAttribute("quickNote", new QuickNote(""));

        return "myDashboard";


    }
    
}
