import { Message } from "../../types/Message";
import MessageCardView from "../MessageCardView/MessageCardView"

const MessagesListView = ({ messages }:
                              {
                                  messages: Message[],
                              }) => {

    console.log(messages)

    return <div>
        <ul>
            {messages.map((b, i) =>
                <MessageCardView
                    key={i}
                    mess={b}
                />)}
        </ul>
    </div>
}

export default MessagesListView