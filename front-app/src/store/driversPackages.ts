import { createSlice, PayloadAction } from '@reduxjs/toolkit'

import { Package } from '../types/Package'
import { useLoginUserSelector } from './hooks'
// import { hubWorker } from '../types/userRoles'

export interface State {
    driversPackages: Package[],
    filter: string,


}

// Define the initial state using that type
const initialState: State = {
    driversPackages: [],
    filter: '',
}

export const slice = createSlice({
    name: 'driversPackages',
    initialState,
    reducers: {

        setDriversPackages: (state, action:PayloadAction<Package[]>) => {
            state.driversPackages = action.payload
        },
        setFilter: (state, action:PayloadAction<string>) => {
            state.filter = action.payload
        }

    }
})


export const { setDriversPackages , setFilter} = slice.actions

export const loadDriversPackages = () => (dispatch: any) => {

    let username = localStorage.getItem('username');
    let token = localStorage.getItem('token');

    fetch(`api/user/${username}/truck`,{
        method: 'GET',
        headers: new Headers({'Authorization': `Bearer ${token}`})
    })
    .then(res => res.json())
    .then(truck => truck.truckId)
    .then(id => {
        console.log(id)
        fetch(`/api/trucks/${id}/packages`, {
            method:'GET',
            headers: new Headers({'Authorization': `Bearer ${token}`})
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
            .then( packages => dispatch(setDriversPackages(packages)))
    })
    

}


export default slice.reducer