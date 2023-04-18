package Phase1.presentation

import Phase1.Application
import Phase1.data.Package
import Phase1.data.Truck
import Phase1.service.TruckService
import org.springframework.web.bind.annotation.RestController

@RestController
class TruckController(val app: TruckService):TrucksAPI{
    override fun getAll(): Collection<TruckDTO> = app.getAll().map { TruckDTO(it) }

    override fun getAllPackages(id: Long): Collection<PackageDTO> = app.getAllPackages(id).map{ PackageDTO(it) }
}