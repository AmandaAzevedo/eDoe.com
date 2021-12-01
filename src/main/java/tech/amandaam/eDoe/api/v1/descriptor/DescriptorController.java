package tech.amandaam.eDoe.api.v1.descriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/descriptors")
public class DescriptorController {
    @Autowired
    private DescriptorService descriptorService;

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DescriptorDTO createDescriptor(@RequestBody DescriptorCreateDTO descriptorCreateDTO,@RequestHeader("Authorization") String header) throws ServletException {
        return descriptorService.createDescriptor(descriptorCreateDTO, header);
    }

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DescriptorDTO> getAllDescriptor(@RequestHeader("Authorization") String header, @RequestBody OptionsToGetTheDescriptorDTO optionsToGetTheDescriptorDTO) throws ServletException{
        return descriptorService.getAll(header, optionsToGetTheDescriptorDTO);
    }
}
