package org.yantranet.etas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.yantranet.etas.model.Cabs;

@RepositoryRestResource(collectionResourceRel = "cabs", path = "cabs")
public interface CabRepository extends CrudRepository<Cabs, Long>{

}
