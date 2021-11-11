package tech.amandaam.eDoe.api.v1.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    public List<Request> findAllByRequestingUserEmail(String email);
}
