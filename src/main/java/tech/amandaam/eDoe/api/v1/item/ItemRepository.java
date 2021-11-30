package tech.amandaam.eDoe.api.v1.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.amandaam.eDoe.api.v1.descriptor.Descriptor;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findById(Long id);
    public List<Item> findTop10ByItemTypeOrderByQuantityDesc(ItemTypeEnum itemType);
    public List<Item> findAll();
    public List<Item> findAllByItemType(ItemTypeEnum itemType);
    public List<Item> findAllByDescriptorsContainingAndItemType(Descriptor descriptor, ItemTypeEnum itemType);
}
