package Phase1.presentation

import Phase1.Application
import Phase1.data.Message
import Phase1.service.MessageService
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(val app: MessageService) : MessagesAPI {
    
    override fun getAll(): Collection<MessageDTO> = app.getAll().map { MessageDTO(it) }

    override fun getOne(id: Long): MessageDTO = MessageDTO(app.getOne(id).get())

//    override fun delete(id: Long) {
//        app.delete(id)
//    }

    override fun addOne(message: MessageCreateDTO) {
        app.addOne(message.from,message.to,message.subject,message.body,message.timestamp, message.packageId, message.previous_messageId)

    }
}