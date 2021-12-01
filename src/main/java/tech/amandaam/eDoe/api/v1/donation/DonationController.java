package tech.amandaam.eDoe.api.v1.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/donation/")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PatchMapping(value = "/")
    @ResponseStatus(code = HttpStatus.OK)
    public DonationDTO donation (@RequestBody DonationCreateDTO donationCreateDTO, @RequestHeader("Authorization") String token ){
        return donationService.donation(donationCreateDTO, token);
    }

    @GetMapping(value = "/")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleDonationDTO> getAllDonations (){
        return donationService.getAll();
    }
}
