import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import { CreatePackage, Package } from '../types/Package'
import { useLoginUserSelector } from './hooks'
import {skipToken} from "@reduxjs/toolkit/query";

export interface State {
    clientPackages: Package[],
}

// Define the initial state using that type
const initialState: State = {
    clientPackages: [], 
}

export const slice = createSlice({
    name: 'packages',
    initialState,
    reducers: {
        addClientPackage: (state, action:PayloadAction<CreatePackage>) => ({
            ...state, books: [...state.clientPackages, action.payload], uploading: false
        }),
        setClientPackages: (state, action:PayloadAction<Package[]>) => {
            console.log(action.payload)
            state.clientPackages = action.payload
        },
    }
})

// export const { addBook, setFilter, setLoading, setBooks, setUploadingBook } = slice.actions
export const { setClientPackages, addClientPackage } = slice.actions


export const loadClientPackages = () => (dispatch: any) => {
    let token = localStorage.getItem('token');
    let username = localStorage.getItem('username');

    fetch(`/api/user/${username}/packages`, {
        method:'GET',
        headers: new Headers({'Authorization': `Bearer ${token}`}),
   })
    .then( (response) => {
        if (response.ok) {
            return response.json()
        }
        throw new Error('Something went wrong');
    })
    .catch((error) => {
        console.log(error)
      })
    .then( books => dispatch(setClientPackages(books)))
    }

export const createClientPackage = (pack: CreatePackage) => (dispatch: any) => {
    fetch('/api/packages', { method: "POST",
    headers: {
        "Accept":"application/json",
        "Content-Type":"application/json"}
        ,body: JSON.stringify(pack)})
    .then( response => response.json())
    .then( pack => dispatch(addClientPackage(pack)))
}

export default slice.reducer