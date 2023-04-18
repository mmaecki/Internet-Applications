import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { Package } from '../types/Package'
import { useLoginUserSelector } from './hooks'
// import { hubWorker } from '../types/userRoles'

export interface State {
    hubworkersPackages: Package[],
    filter: string,
}

// Define the initial state using that type
const initialState: State = {
    hubworkersPackages: [],
    filter: '',
}

export const slice = createSlice({
    name: 'hubWorkersPackages',
    initialState,
    reducers: {
        setHubworkersPackages: (state, action:PayloadAction<Package[]>) => {
            state.hubworkersPackages = action.payload
        },
        setFilter: (state, action:PayloadAction<string>) => {
            state.filter = action.payload
        }
    }
})

// export const { addBook, setFilter, setLoading, setBooks, setUploadingBook } = slice.actions
export const { setHubworkersPackages, setFilter } = slice.actions


export const loadHubWorkersPackages = () => (dispatch: any) => {
    
    let username = localStorage.getItem('username');
    let token = localStorage.getItem('token');


    fetch(`api/user/${username}/hub`, {
        method:'GET',
        headers: new Headers({'Authorization': `Bearer ${token}`})
    })
    .then(res => res.json())
    .then(hub => hub.hubId)
    .then(id =>{
        console.log(id)
        fetch(`api/hubs/${id}/packages`, {
            method:'GET',
            headers: new Headers({'Authorization': `Bearer ${token}`})
        })
        .then( (response) => {
                    if (response.ok) {
                        console.log(response)
                        return response.json()
                    }
                    throw new Error('Something went wrong');
                })
                .catch((error) => {
                    console.log(error)
                })
                .then( packages => dispatch(setHubworkersPackages(packages)))
    
    })

}

export default slice.reducer