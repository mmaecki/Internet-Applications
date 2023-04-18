import { useState } from "react"
import { addClientMessage, createClientMessage } from "../../store/messages"
import { useAppDispatch, useClientMessagesSelector } from "../../store/hooks"
import {useNavigate, useParams} from "react-router-dom";

import MessagesListView from "../MessagesListView/MessagesListView"
import useInput from "../UseInput/UseInput"
import { Package} from "../../types/Package"
export type Myparams={
    packageId:string;
}
const CreateMessage = () => {
    // packageId}:{packageId:number}
    //
    const {packageId}=useParams<keyof Myparams>() as Myparams

    const dispatch = useAppDispatch()
    const username=localStorage.getItem('username')!
    const [newMessage, setNewMessage] = useState<typeof CreateMessage | undefined>(undefined)
    const [inputFrom, from] = useInput("", "from")
    const [inputTo, to] = useInput("", "to")
    const [inputSubject, subject] = useInput("", "subject")
    const [inputBody, body] = useInput("", "body")
    // const [inputPackageId, packageId] = useInput("", "packageId")
    const [inputPreviousMessageId, previousMessageId] = useInput("", "previousMessageId")
    const addMessage = () => {
        dispatch(createClientMessage({from: username, to: to, subject: subject,
            body: body, timestamp: new Date(), packageId: parseInt(packageId), previousMessageId: parseInt(previousMessageId)}))
            navigate('/clientPackages')
    }
    const navigate = useNavigate()

    return <div>
        <h1>Create Message for client</h1>
        {inputTo}
        <br/>
        {inputSubject}
        <br/>
        {inputBody}
        {/*<br/>*/}
        {/*{inputPackageId}*/}
        <br/>
        {inputPreviousMessageId}
        <br/>
        <button onClick={addMessage}>
            Add message
        </button>
    </div>
}

export default CreateMessage