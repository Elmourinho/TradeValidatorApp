package com.trade.restapp.controller;

import com.trade.restapp.validator.ValidationCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ShutDownController {

    @Autowired
    private ValidationCore validationCore;

    @RequestMapping(value = "shutdown", method = RequestMethod.GET)
    public boolean fetchStatus() {
        return validationCore.fetchShutdownStatus();
    }

    @RequestMapping(value = "shutdown", method = RequestMethod.POST)
    public boolean shutdown() {
        validationCore.shutdown();
        return validationCore.fetchShutdownStatus();
    }

    @RequestMapping(value = "shutdown", method = RequestMethod.DELETE)
    public boolean cancelShutdown() {
        validationCore.cancelShutdown();
        return validationCore.fetchShutdownStatus();
    }


}
