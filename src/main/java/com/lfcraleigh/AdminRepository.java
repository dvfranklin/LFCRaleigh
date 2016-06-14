package com.lfcraleigh;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Administrator, Integer> {

    Administrator findAdministratorByUsername(String username);
}
