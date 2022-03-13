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
 * Controller gérant des fonctionnalités supplémentaires
 */
@RestController
@RequestMapping("/api/utils")
public class UtilController {

    @GetMapping("/checkIP")
    public ResponseEntity<?> checkIP(HttpServletRequest req) {
        //renvoie l'adresse IP sous la forme d'un String
        String adresse = req.getRemoteAddr();
        try {
            if(!estDansLaRangeIP(adresse, "172.19.32.1", "172.19.47.254")) {
                return new ResponseEntity<>("Vous n'êtes pas sur l'ip locale de l'IUT", HttpStatus.UNAUTHORIZED);
            }
        } catch (AddressStringException e) {
            return new ResponseEntity<>("Ip pas valide", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Vérifie que l'ip enovyé en paramètre est bien compris entre les 2 range d'IP
     * @param inputIP
     * @param rangeStartIP
     * @param rangeEndIP
     * @return Si l'IP est compris entre la range des deux IP
     * @throws AddressStringException si le String n'est pas une IP
     */
    private boolean estDansLaRangeIP (String inputIP, String rangeStartIP, String rangeEndIP)
            throws AddressStringException {
        IPAddress startIPAddress = new IPAddressString(rangeStartIP).getAddress();
        IPAddress endIPAddress = new IPAddressString(rangeEndIP).getAddress();
        IPAddressSeqRange ipRange = startIPAddress.toSequentialRange(endIPAddress);
        IPAddress inputIPAddress = new IPAddressString(inputIP).toAddress();

        return ipRange.contains(inputIPAddress);
    }
}
