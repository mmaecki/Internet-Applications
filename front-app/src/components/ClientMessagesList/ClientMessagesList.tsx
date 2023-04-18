import { useEffect, useState } from "react"
import { useAppDispatch, useClientMessagesSelector } from "../../store/hooks"
import { loadClientMessages } from "../../store/messages"
import MessagesListView from "../MessagesListView/MessagesListView"

const ClientMessagesList = () => {

    const messages = useClientMessagesSelector(state => state.clientMessages)
    const dispatch = useAppDispatch()
    useEffect(() => {
        dispatch(loadClientMessages())
    }, [])

    return <div>
        <MessagesListView messages={messages}/>
    </div>
}

export default ClientMessagesList