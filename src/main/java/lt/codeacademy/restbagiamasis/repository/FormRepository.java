package lt.codeacademy.restbagiamasis.repository;

import lt.codeacademy.restbagiamasis.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    List<Form> getAllByCreatorId(Long id);
}
