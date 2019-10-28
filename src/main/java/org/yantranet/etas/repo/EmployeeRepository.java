package org.yantranet.etas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.yantranet.etas.model.Employee;

@RepositoryRestResource(collectionResourceRel = "employee", path = "employee")
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
