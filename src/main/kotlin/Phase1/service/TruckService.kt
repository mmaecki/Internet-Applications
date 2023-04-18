package Phase1.service

import Phase1.data.TruckRepository
import org.springframework.stereotype.Service
import javax.annotation.security.RolesAllowed

@Service
class TruckService(val trucks:TruckRepository) {

    @RolesAllowed("DRIVER", "MANAGER")
    fun getAll() = trucks.findAll()

    @RolesAllowed("DRIVER", "MANAGER")
    fun getAllPackages(id:Long) = trucks.findPackagesByTruckId(id);

}