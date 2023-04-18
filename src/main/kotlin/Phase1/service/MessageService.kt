package Phase1.service

import Phase1.data.Message
import Phase1.data.MessageRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.annotation.security.RolesAllowed

@Service
class MessageService(val messages:MessageRepository){

    @RolesAllowed("MANAGER")
    fun getAll()=messages.findAll()

    fun getOne(id:Long)=messages.findById(id)

//    fun delete(id:Long){
//        messages.deleteById(id)
//    }

    fun addOne(from:String, to:String, subject:String, body:String, timestamp: LocalDateTime, packageId:Long, previous_messageId:Long?){
        messages.save(Message(0, from,to,subject,body,timestamp,packageId, previous_messageId))
    }
}