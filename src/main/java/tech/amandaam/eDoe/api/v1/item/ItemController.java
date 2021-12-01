package tech.amandaam.eDoe.api.v1.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ItemDTO createNewItemDonation(@RequestBody ItemCreateDTO itemCreateDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.createItem(header, itemCreateDTO);
    }



    //========================



    @DeleteMapping(value = "/donation/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteDonationDisciplineById(@PathVariable("id") Long id, @RequestHeader("Authorization") String header) throws ServletException {
        itemService.deleteItem(id, header);
    }

    @PatchMapping (value = "/donation/description/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateDescriptionDetailed(@PathVariable("id") Long id, @RequestBody UpdateDescriptionOrJustificationDTO updateDetailedDescriptionDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.updateDetailedDescriptionOrMotivation(id,updateDetailedDescriptionDTO, header);
    }

    @PatchMapping (value = "/donation/quantity/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateQuantityItemDonation(@PathVariable("id") Long id, @RequestBody UpdateQuantityDTO updateQuantityDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.updateQuantity(id,updateQuantityDTO, header);
    }

    @GetMapping(value = "/donation/top10")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleItemDTO> getTop10DonationItems(){
        return itemService.listTop10(ItemTypeEnum.DONATION);
    }

    @GetMapping(value = "/donation/list/descriptor/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleItemDTO> getDonationItemsPerDescriptor(@PathVariable("id") Long id){
        return itemService.getItemsPerDescriptor(id, ItemTypeEnum.DONATION);
    }


    @GetMapping(value = "/donation/list/descriptor/name")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleItemDTO> getDonationItemsByDescriptorName(@RequestBody DonationItemsByDescriptorNameDTO donationItemsByDescriptorName){
        return itemService.getItemsByDescriptorName(donationItemsByDescriptorName, ItemTypeEnum.DONATION);
    }


    //============================

    @DeleteMapping(value = "/grantee/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteGranteeDisciplineById(@PathVariable("id") Long id, @RequestHeader("Authorization") String header) throws ServletException {
        itemService.deleteItem(id, header);
    }

    @PatchMapping (value = "/grantee/justification/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateJustification(@PathVariable("id") Long id, @RequestBody UpdateDescriptionOrJustificationDTO updateDetailedDescriptionDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.updateDetailedDescriptionOrMotivation(id,updateDetailedDescriptionDTO, header);
    }

    @PatchMapping (value = "/grantee/quantity/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ItemDTO updateQuantityItemGrantee(@PathVariable("id") Long id, @RequestBody UpdateQuantityDTO updateQuantityDTO, @RequestHeader("Authorization") String header) throws ServletException {
        return itemService.updateQuantity(id,updateQuantityDTO, header);
    }

    @GetMapping(value = "/grantee/top10")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleItemDTO> getTop10GranteeItems(){
        return itemService.listTop10(ItemTypeEnum.GRANTEE);
    }

    @GetMapping(value = "/grantee/list/descriptor/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleItemDTO> getGranteeItemsPerDescriptor(@PathVariable("id") Long id){
        return itemService.getItemsPerDescriptor(id, ItemTypeEnum.GRANTEE);
    }


    @GetMapping(value = "/grantee/list/descriptor/name")
    @ResponseStatus(code = HttpStatus.OK)
    public List<SimpleItemDTO> getGranteeItemsByDescriptorName(@RequestBody DonationItemsByDescriptorNameDTO donationItemsByDescriptorName){
        return itemService.getItemsByDescriptorName(donationItemsByDescriptorName, ItemTypeEnum.GRANTEE);
    }

}
