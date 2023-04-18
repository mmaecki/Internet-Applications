import { Message } from "../../types/Message";
import './MessageCardView.css'

const MessageCardView = ({ mess }: { mess: Message }) =>
    <li>
            <div className="list-row">
                    <p>{"From: "}{mess.from}</p>
                    <p>{"To: "}{mess.to}</p>
                    <p>{"Subject: "}{mess.subject}</p>
                    <p>{"Body: "}{mess.body}</p>
                    <p>{"PackageID: "}{mess.packageId}</p>

            </div>

    </li>
export default MessageCardView
