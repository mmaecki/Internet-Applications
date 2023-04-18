package Phase1

import Phase1.data.Message
import Phase1.data.MessageRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import Phase1.data.*
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.util.*
import org.springframework.security.crypto.password.PasswordEncoder as PasswordEncoder1


@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests{


    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var trucks: TruckRepository

    @MockBean
    lateinit var hubs: HubRepository

    @MockBean
    lateinit var messages : MessageRepository

    @MockBean
    lateinit var packages: PackageRepository

    @MockBean
    lateinit var clients: ClientRepository

    companion object {
        val r1 = Role(0, "ROLE_CLIENT", null)
        val r2 = Role(0, "ROLE_MANAGER", null)
        val r3 = Role(0, "ROLE_HUBWORKER", null)
        val r4 = Role(0, "ROLE_DRIVER", null)

        val c1 = Client(0, "Antos", "aaaaa@aaa.com","123",r1, mutableListOf())
        val c2 = Client(0, "Ziele", "aaaaaaa@aaa.com","123", r1, mutableListOf())
        val clientsList = listOf(c1, c2)

        val d1 = Driver(0, "Robert Kubica", "aaa@kubica.pl", "123", r4,null, mutableListOf())
        val d2 = Driver(0,"Max Verstappen","namaxa@gmail.com","123",r4, null,mutableListOf())
        val t1 = Truck(0, d2,mutableListOf())
        val t2 = Truck(0, d1,mutableListOf())
        val trucksList = listOf(t1, t2)

        init {
            d1.truck = t2
            d2.truck = t1
        }
        val driversList = listOf(d1, d2)

        val initialMessages = mutableListOf(
            Message(1, "a@a.com", "b@b.com", "Sam","Co", LocalDateTime.now(),null, 23),
            Message(2, "b@b.com", "c@c.com", "Już","Się", LocalDateTime.now(),null,222),
            Message(3, "c@c.com", "a@a.com", "Nie wiem","Dzieje", LocalDateTime.now(),null,2),
        )

        val hb1 = Hub(0,"Poznan", "Klonowa 5", "strasznie smutne miejsce", mutableListOf())
        val hb2 = Hub(0, "Lzibona", "oceanowa 5", "costa da caparica", mutableListOf())
        val hubsList = listOf(hb1,hb2)

        val initialPackages= listOf(
            Package(1, hb1, "pip", 10, 10, 10, 10, "Berlin", null,"poznan", mutableListOf(), c1, t1, Status.AwaitingEntry),
            Package(2, hb2, "desc", 10, 10, 10, 10, "Londyn", null,"poznand", mutableListOf(), c2, t1,Status.InTransit)
        )
    }




    //TESTS FOR TRUCKS
    @Test
    @WithMockUser(username="driver",roles= arrayOf("DRIVER"))
    fun `get all Trucks`(){
        Mockito.`when`(trucks.findAll()).thenReturn(trucksList)
        mvc.get("/api/trucks"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { MockMvcResultMatchers.jsonPath("$[0].truckId", equals(trucksList[0].truckId)) }
            content { jsonPath("$", Matchers.hasSize<Array<String>>(2)) }
        }
    }

    @Test
    @WithMockUser(username="client",roles= arrayOf("CLIENT"))
    fun `get all Trucks (forbidden)`(){
        Mockito.`when`(trucks.findAll()).thenReturn(trucksList)
        mvc.get("/api/trucks"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }



    //TESTS FOR PACKAGES
    @Test
    @WithMockUser(username="szef",roles= arrayOf("MANAGER"))
    fun `get all Packages`(){
        Mockito.`when`(packages.findAll()).thenReturn(initialPackages)
        mvc.get("/api/packages"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status{isOk()}
            content { contentType(MediaType.APPLICATION_JSON) }
            content { jsonPath("$", Matchers.hasSize<Array<String>>(2)) }

        }

    }
// TODO zdebugować to
//    @Test
//    @WithMockUser(username="client",roles= arrayOf("CLIENT"))
//    fun `get all Packages (forbidden)`(){
//        Mockito.`when`(packages.findAll()).thenReturn(initialPackages)
//        mvc.get("/api/packages"){
//            accept=MediaType.APPLICATION_JSON
//        }.andExpect {
//            status{isForbidden()}
//
//        }
//
//    }

    @Test
    @WithMockUser(username="Antos",roles= arrayOf("CLIENT"))
    fun `delete Package by Client`() {
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        Mockito.`when`(packages.deleteById(ArgumentMatchers.any())).then { println("Deleted") }

        mvc.delete("/api/packages/1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @WithMockUser(username="Ziele",roles= arrayOf("CLIENT"))
    fun `delete Package by Client (Status in not awaiting entry)`() {
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[1]))
        Mockito.`when`(packages.deleteById(ArgumentMatchers.any())).then { println("Deleted") }

        mvc.delete("/api/packages/1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username="Ziele",roles= arrayOf("CLIENT"))
    fun `delete Package by Client (deleting other's package)`() {
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        Mockito.`when`(packages.deleteById(ArgumentMatchers.any())).then { println("Deleted") }

        mvc.delete("/api/packages/1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username="Antos",roles= arrayOf("CLIENT"))
    fun `Client can get Package`() {
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))

        mvc.get("/api/packages/1") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { initialPackages[0]}
        }
    }

    @Test
    @WithMockUser(username = "Ziele", roles = arrayOf("CLIENT"))
    fun `Client can get package (is forbidden)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.get("/api/packages/1"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status{isForbidden()}
        }

    }

    @Test
    @WithMockUser(username = "Ziele", roles = arrayOf("CLIENT"))
    fun `Client can get packages status and history`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[1]))
        mvc.get("/api/packages/1/statusAndHistory"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status{isOk()}
            content{ contentType(MediaType.APPLICATION_JSON) }
        }

    }

    @Test
    @WithMockUser(username = "Antos", roles = arrayOf("CLIENT"))
    fun `Client can get packages status and history (is forbidden)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[1]))
        mvc.get("/api/packages/1/statusAndHistory"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status{isForbidden()}
        }
    }



    //TESTS FOR CLIENTS
    @Test
    @WithMockUser(username = "Antos", roles = arrayOf("CLIENT"))
    fun `Client can get his packages`(){
        Mockito.`when`(clients.findClientByUsername(anyString())).thenReturn(clientsList[0])

        mvc.get("/api/user/Antos/packages"){
            accept=MediaType.APPLICATION_JSON
        }.andExpect {
            status{isOk()}
        }

    }

//    @Test
//    @WithMockUser(username = "Ziele", roles = arrayOf("CLIENT"))
//    fun `Client can get his packages (is forbidden)`(){
//        Mockito.`when`(clients.findClientByUsername(anyString())).thenReturn(clientsList[0])
//
//        mvc.get("/api/user/Antos/packages"){
//            accept=MediaType.APPLICATION_JSON
//        }.andExpect {
//            status{isForbidden()}
//        }
//
//    }








    //TESTS FOR MESSAGES
    @Test
    fun `get all Messages (not authenticated)`() {
        Mockito.`when`(messages.findAll()).thenReturn(initialMessages)

        mvc.get("/api/messages") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isUnauthorized()}
        }
    }

    @Test
    @WithMockUser(username="manager",roles= arrayOf("MANAGER"))
    fun `get all Messages`() {
        Mockito.`when`(messages.findAll()).thenReturn(initialMessages)

        mvc.get("/api/messages") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { jsonPath("$", Matchers.hasSize<Array<String>>(3)) }
        }
    }

    @Test
    @WithMockUser(username="client",roles= arrayOf("CLIENT"))
    fun `get all Messages (forbidden)`() {
        Mockito.`when`(messages.findAll()).thenReturn(initialMessages)

        mvc.get("/api/messages") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username="a@a.com",roles= arrayOf("CLIENT"))
    fun `get one Message`() {
        Mockito.`when`(messages.findById(anyLong())).thenReturn(Optional.of(initialMessages[0]))

        mvc.get(("/api/messages/1")) {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { initialMessages[0] }
        }
    }


//    @Test
//    @WithMockUser(username="a@a.com",roles= arrayOf("CLIENT"))
//    fun `delete one Message`() {
//        Mockito.`when`(messages.deleteById(ArgumentMatchers.anyLong())).then { println("Deleted") }
//
//        mvc.delete("/api/messages/1") {
//            accept = MediaType.APPLICATION_JSON
//        }.andExpect {
//            status { isOk() }
//            content { "Deleted" }
//        }
//    }

    @Test
    @WithMockUser(username="a@a.com",roles= arrayOf("CLIENT"))
    fun `add one Message`() {

        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        var jsonData = mapper.writeValueAsString(Message(10,"a","b","c", "d", LocalDateTime.now(),null, 20))

        mvc.perform(MockMvcRequestBuilders.post("/api/messages/").contentType(MediaType.APPLICATION_JSON).content(jsonData))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

    }




    //TESTS FOR HUBS
    @Test
    @WithMockUser(username="hubworker",roles= arrayOf("HUBWORKER"))
    fun `get all Hubs`() {
        Mockito.`when`(hubs.findAll()).thenReturn(hubsList)

        mvc.get("/api/hubs") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { jsonPath("$", Matchers.hasSize<Array<String>>(2)) }
        }
    }

    @Test
    @WithMockUser(username="client",roles= arrayOf("CLIENT"))
    fun `get all Hubs (forbidden)`() {
        Mockito.`when`(hubs.findAll()).thenReturn(hubsList)

        mvc.get("/api/hubs") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isForbidden() }
        }
    }

    @Test
    fun `update package status (not authenticated)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1")
            .param("status", "InTransit")
        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }




    //TESTS FOR CAMUPDATEPACKAGE

    @Test
    @WithMockUser(username = "driver", roles = arrayOf("DRIVER"))
    fun `update package status (as driver - authorised)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/status")
            .param("status", "InTransit")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "driver", roles = arrayOf("DRIVER"))
    fun `update package status (as driver - non authorised)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/status")
            .param("status", "Lost")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "manager", roles = arrayOf("MANAGER"))
    fun `update package status (as manager)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/status")
            .param("status", "Lost")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "client", roles = arrayOf("CLIENT"))
    fun `update package status (as client)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/status")
            .param("status", "Lost")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "hubworker", roles = arrayOf("HUBWORKER"))
    fun `update package status (as hubworker authorised)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/status")
            .param("status", "InStorage")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "hubworker", roles = arrayOf("HUBWORKER"))
    fun `update package status (as hubworker forbidden)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/status")
            .param("status", "Lost")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "hubworker", roles = arrayOf("HUBWORKER"))
    fun `update package destination (as hubworker)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/destination")
            .param("destination", "Maroko")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "client", roles = arrayOf("CLIENT"))
    fun `update package destination (as client)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/destination")
            .param("destination", "Maroko")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "driver", roles = arrayOf("DRIVER"))
    fun `update package destination (as driver)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/destination")
            .param("destination", "Maroko")
        )
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

    @Test
    @WithMockUser(username = "manager", roles = arrayOf("MANAGER"))
    fun `update package destination (as manager)`(){
        Mockito.`when`(packages.findById(anyLong())).thenReturn(Optional.of(initialPackages[0]))
        mvc.perform(MockMvcRequestBuilders.put("/api/packages/1/destination")
            .param("destination", "Maroko")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()
    }

//    @Test
//    @WithMockUser(username="Antos",roles= arrayOf("CLIENT"))
//    fun `add one Package`() {
//        val mapper = jacksonObjectMapper()
//        mapper.findAndRegisterModules()
//        var jsonData = mapper.writeValueAsString(Package(10, hb2, "desc", 10, 10, 10, 10, "Londyn", null, "poznand", mutableListOf(), c1, null,Status.InTransit))
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/packages").contentType(MediaType.APPLICATION_JSON).content(jsonData))
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andDo(MockMvcResultHandlers.print())
//            .andReturn()
//
//    }

    @Test
    @WithMockUser(username="Antos",roles= arrayOf("HUBWORKER"))
    fun `add one Package (forbidden)`() {
        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        var jsonData = mapper.writeValueAsString(Package(10, hb2, "desc", 10, 10, 10, 10, "Londyn", null, "poznand", mutableListOf(), c1, null,Status.InTransit))

        mvc.perform(MockMvcRequestBuilders.post("/api/packages").contentType(MediaType.APPLICATION_JSON).content(jsonData))
            .andExpect(MockMvcResultMatchers.status().isForbidden)
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

    }

//    @Test
//    @WithMockUser(username="manager",roles= arrayOf("MANAGER"))
//    fun `add one Package (maanager on behalf)`() {
//        val mapper = jacksonObjectMapper()
//        mapper.findAndRegisterModules()
//        var jsonData = mapper.writeValueAsString(Package(10, hb2, "desc", 10, 10, 10, 10, "Londyn", null, "poznand", mutableListOf(), c1, null,Status.InTransit))
//
//        mvc.perform(MockMvcRequestBuilders.post("/api/packages").contentType(MediaType.APPLICATION_JSON).content(jsonData))
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andDo(MockMvcResultHandlers.print())
//            .andReturn()
//
//    }




}