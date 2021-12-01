package tech.amandaam.eDoe.api.v1.descriptor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {
    public boolean existsByName(String name);

    public Optional<Descriptor> findByName(String name);

    public List<Descriptor> findAllByOrderByNameDesc();

    public List<Descriptor> findAllByOrderByNameAsc();

    public Optional<Descriptor> findById(Long id);
}
