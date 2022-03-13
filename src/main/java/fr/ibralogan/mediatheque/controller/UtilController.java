package fr.ibralogan.mediatheque.controller;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressSeqRange;
import inet.ipaddr.IPAddressString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("/api/utils")
public class UtilController {

    @GetMapping("/checkIP")
    public ResponseEntity<?> checkIP(HttpServletRequest req) {
        String adresse = req.getRemoteAddr();
        try {
            if(!estDansLaRangeIP(adresse, "172.19.32.1", "172.19.47.254")) {
                return new ResponseEntity<>("Vous n'êtes pas sur l'ip locale de l'IUT", HttpStatus.UNAUTHORIZED);
            }
        } catch (AddressStringException e) {
            return new ResponseEntity<>("Ip pas super valide", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }


    private boolean estDansLaRangeIP (String inputIP, String rangeStartIP, String rangeEndIP)
            throws AddressStringException {
        IPAddress startIPAddress = new IPAddressString(rangeStartIP).getAddress();
        IPAddress endIPAddress = new IPAddressString(rangeEndIP).getAddress();
        IPAddressSeqRange ipRange = startIPAddress.toSequentialRange(endIPAddress);
        IPAddress inputIPAddress = new IPAddressString(inputIP).toAddress();

        return ipRange.contains(inputIPAddress);
    }
}
