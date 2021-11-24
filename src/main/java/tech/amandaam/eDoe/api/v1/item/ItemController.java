package tech.amandaam.eDoe.api.v1.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletException;

@RestController
@RequestMapping(value = "/api/v1/item")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemDTO createNewItem(@RequestBody ItemCreateDTO itemCreateDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.createItem(header, itemCreateDTO);
    }

    @PatchMapping (value = "/update")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateItem(@RequestHeader("Authorization") String header, @RequestBody ItemDTO itemDTO) throws ServletException {
        return null;
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteDisciplineById(@PathVariable("id") Long id, @RequestHeader("Authorization") String header) throws ServletException {
        itemService.deleteItem(id, header);
    }

    @PatchMapping (value = "/description/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateDescriptionDetailed(@PathVariable("id") Long id, @RequestBody UpdateDetailedDescriptionDTO updateDetailedDescriptionDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.updateDetailedDescription(id,updateDetailedDescriptionDTO, header);
    }

    @PatchMapping (value = "/quantity/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateQuantity(@PathVariable("id") Long id, @RequestBody UpdateQuantityDTO updateQuantityDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.updateQuantity(id,updateQuantityDTO, header);
    }

}
