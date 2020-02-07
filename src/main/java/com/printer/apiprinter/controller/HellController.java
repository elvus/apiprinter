package com.printer.apiprinter.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.printer.apiprinter.models.printers.getPrinterServiceNameList;
import static com.printer.apiprinter.models.printers.SetReport;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST }, maxAge = 3600)
@RestController
@RequestMapping("/")
public class HellController {
    @GetMapping("printer")
    List<String> hell() {
        return getPrinterServiceNameList(); // get the local printers.
    }

    @PostMapping("printer")
    int printReport(@RequestBody Map<String, Object> map) { // json with the connection, report ubication and params
                                                            // and local printer
        SetReport(map);
        return 200;
    }
}
