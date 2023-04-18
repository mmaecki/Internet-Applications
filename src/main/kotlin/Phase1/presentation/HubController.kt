package Phase1.presentation

import Phase1.service.HubService
import Phase1.data.Hub
import org.springframework.web.bind.annotation.RestController


@RestController
class HubController(val app: HubService):HubsAPI{

    override fun getAll(): Collection<HubDTO> = app.getAll().map { HubDTO(it) }

    override fun getAllPackages(id: Long): Collection<PackageDTO> = app.getAllPackages(id).map{ PackageDTO(it) }
}