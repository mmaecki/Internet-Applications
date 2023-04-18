import { createSlice, PayloadAction } from '@reduxjs/toolkit'
import { LoginUser } from '../types/LoginUser'

export interface State {
    loginUser: LoginUser,
}

const initialState: State = {
    loginUser: {username: '', password: ''}, 
}

export const slice = createSlice({
    name: 'loginUser',
    initialState,
    reducers: {
        setLoginUser: (state, action:PayloadAction<LoginUser>) => {
            state.loginUser = action.payload
        },
    }
})

export const { setLoginUser } = slice.actions

export default slice.reducer