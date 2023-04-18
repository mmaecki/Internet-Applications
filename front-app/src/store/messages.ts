import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import {CreateMessage,Message} from "../types/Message";


export interface State{
    clientMessages:Message[],
}


const initialState: State = {
    clientMessages: [],
}
export const slice = createSlice({
    name: 'messages',
    initialState,
    reducers: {
        setClientMessages: (state, action:PayloadAction<Message[]>) => {
            state.clientMessages= action.payload
        },
        addClientMessage: (state, action:PayloadAction<CreateMessage>) => ({
            ...state, books: [...state.clientMessages, action.payload], uploading: false
        }),
    }
})
export const { setClientMessages,addClientMessage } = slice.actions


export const loadClientMessages = () => (dispatch: any) => {
    let token = localStorage.getItem('token');
    let username = localStorage.getItem('username');
    fetch(`/api/user/${username}/messages`,{
        method:'GET',
        headers: new Headers({'Authorization': `Bearer ${token}`}),
    })
        .then( (response) => {
            if (response.ok) {
                console.log(response);
                return response.json()
            }
            throw new Error('Something went wrong');
        })
        .catch((error) => {
            console.log(error)
        })
        .then( messages => dispatch(setClientMessages(messages)))
}
export const createClientMessage = (msg: CreateMessage) => (dispatch: any) => {
    fetch(`/api/messages`, {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }, body: JSON.stringify(msg)})
        .then(response => response.json())
        .then(message => dispatch(addClientMessage(message)))
}
export default slice.reducer